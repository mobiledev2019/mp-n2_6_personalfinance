package com.example.personal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHandler {
    private static final String DATABASE_NAME = "Personal2";
    private Context context;
    static OpenHelper openHelper;
    private SQLiteDatabase database;
    static final String tblReceiptPayment = "tblReceiptPayment";
    static final String caID = "caID";
    static final String caAccount = "caAccount";
    static final String caType = "caType";
    static final String caAmount = "caAmount";
    static final String caReason = "caReason";
    static final String caGroup = "caGroup";
    static final String caDate = "caDate";
    static final String caImgBill = "imgBill";
    static final String createdDate = "createDate";
    static final String modifiedDate = "modifiedDate";

  public DatabaseHandler(Context context) {
        this.context = context;
    }

    public DatabaseHandler open() {
        try {
            this.openHelper = new OpenHelper(context);
            this.database = openHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void close() {
        openHelper.close();
    }

    public boolean addCA(String account, String type, String amount,
                         String reason, String group, String date, byte[] imageBill) {
        ContentValues cv = new ContentValues();
        cv.put(caAccount,account);
        cv.put(caType, type);
        cv.put(caAmount, amount);
        cv.put(caGroup, group);
        cv.put(caReason, reason);
        cv.put(caDate, date);
        cv.put(caImgBill, imageBill);
        // lấy ngày hiện tại ra format theo định dạng dd/mm/yyyy để gắn vào trường createdDate vs ModifiedDate
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String calenderFormat = sdf.format(calendar.getTime());
        cv.put(createdDate, calenderFormat);
        cv.put(modifiedDate, calenderFormat);


        long result = -1;
        try {
            result = database.insert(tblReceiptPayment, null, cv);
        } catch(Exception e) {
            e.printStackTrace();

            return false;
        }
        return result == -1 ? false : true;
    }

    public List<ReceiptPayment> getReceiptPayment() {
        ArrayList<ReceiptPayment> receiptPayments = new ArrayList<ReceiptPayment>();
        String sql = "select " + caAccount + ", " + caGroup + ", "
                + caAmount + ", " + caDate + ", " + caImgBill + ", " + caReason +" from " + tblReceiptPayment;
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            ReceiptPayment receiptPayment = null;
            do {
                receiptPayment = new ReceiptPayment();
                receiptPayment.setAccountCA(cursor.getString(0));
                receiptPayment.setGroupCA(cursor.getString(1));
                receiptPayment.setAmountCA(cursor.getString(2));
                receiptPayment.setDateCA(cursor.getString(3));
                receiptPayment.setImageBill(cursor.getBlob(4));
                receiptPayment.setReasonCA(cursor.getString(5));
                receiptPayments.add(receiptPayment);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return receiptPayments;
    }

    // ham lay so tien theo tai khoan truyen vao nhu tien mat, tien tiet kiem, the tin dung
    public String getAmountByAccount(String account) {
        String amount = null;
        String sql = "select sum(" + caAmount + ") from " + tblReceiptPayment + " where " + caAccount + "='" + account+"'";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            amount = cursor.getString(0);
        }
        return amount == null ? "0" : amount;
    }

    /**
     * Hàm lấy số tiền theo từng loại khoản thu, khoanr chi và lấy dữ liệu ngày này, tháng này, năm này
     * @param typeca là Khoản thu or Khoản chi
     * @param time có thể truyền vào theo dạng dd/MM/yyyy or /MM/yyyy or /yyyy để lấy dữ liệu hôm nay, tháng này, or năm này
     * @return lấy ra số tiền theo khoản thu, or khoản chi theo thời gian truyền vào
     */
    public String getAmountByTypeAndTime(String typeca, String time) {
        String amount = null;
        String sql = "select sum(" + caAmount + ") from " + tblReceiptPayment + " where " + caType + "='" + typeca + "' and "
                    + caDate + " like '%" + time + "'" ;
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            amount = cursor.getString(0);
        }
        return amount == null ? "0" : amount;
    }

    /**
     * Hàm này kiểm tra xem người dùng hôm nay đã nhập phiếu thi or phiếu chi nào chưa
     * @param time thời gian hôm nay : định dạng dd/MM/yyyy
     * @return true nếu mà người dùng đã thêm, false nếu ngược lại
     */
    public boolean checkCAbyDate(String time) {
        String count = null;
        String sql = "select count(*) from " + tblReceiptPayment + " where "  + createdDate + " like '%" + time + "'" ;
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            count = cursor.getString(0);
        }
        if(count.equals("0")) {
            return false;
        }
        return true;
    }

    static class OpenHelper extends SQLiteOpenHelper{
        public OpenHelper(Context context) {
            super(context,DATABASE_NAME,null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase database) {
            String sql = "create table "+tblReceiptPayment +"("
                    + caID + " integer primary key autoincrement not null,"
                    + caAccount + " text,"
                    + caType + " text,"
                    + caAmount +" text,"
                    + caReason + " text,"
                    + caGroup + " text,"
                    + caDate + " text,"
                    + createdDate + " text,"
                    + modifiedDate + " text,"
                    + caImgBill + " blob);";
            database.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int olVersion, int newVersion) {
            String sql = "drop table if exists tblReceiptPayment";
            onCreate(database);
        }

    }

}
