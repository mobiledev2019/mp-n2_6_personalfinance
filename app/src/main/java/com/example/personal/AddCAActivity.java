package com.example.personal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddCAActivity extends AppCompatActivity  {
    Spinner spAccount, spTypeCA, spGroupCA, spStatusCA;
    Button btnSave;
    EditText edDateCA, edAmount, edReasonCA;
    ImageView imgDatePicker;
    ImageView imgBill;
    ImageButton ibtnCamera, ibtnForder;
    DatabaseHandler database;
    int mYear,mMonth,mDay;
    NotificationManager notificationManager;
    static List<String> accounts = new ArrayList<String>(Arrays.asList("Tiền mặt", "Tiền tiết kiệm","Thẻ tín dụng"));
    static List<String> types = new ArrayList<String>(Arrays.asList("Khoản thu", "Khoản chi"));
    static List<String> groups = new ArrayList<String>(Arrays.asList("Ăn uống", "Trang phục","Đi lại","Học tập", "Sức khỏe", "Giải trí", "Nhà cửa", "Nhận lương", "Khác"));
    static List<String> status = new ArrayList<String>(Arrays.asList("Hoàn tất", "Chưa hoàn tất"));

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_addca);

        database = new DatabaseHandler(this);
        database.open();


        mapField();
        ArrayAdapter<String> adapter = null;
        // thiết lập chọn select taif khoan
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accounts);
        spAccount.setAdapter(adapter);

        // thiet lap chon loai giao dich
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types);
        spTypeCA.setAdapter(adapter);

        // thiet lap chon phan nhom
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groups);
        spGroupCA.setAdapter(adapter);

        // thiet lap chon trang thai
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, status);
        spStatusCA.setAdapter(adapter);

        // thiết lập việc chọn ngày giao dịch là ngày hiện tại
        edDateCA.setEnabled(false);
        edDateCA.setFocusable(false);
        Calendar c = Calendar.getInstance();
        mDay = c.get(Calendar.DATE);
        mMonth = c.get(Calendar.MONTH);
        mYear = c.get(Calendar.YEAR);

        // sử lý nghiệp vụ lấy chụp ảnh và lấy ảnh từ camera và thư viện
        getImageForCameraForder();

        // định dạng theo dd/MM/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        edDateCA.setText(sdf.format(c.getTime()));

        // su kien khi nhan vao img thi hien ra datepicker (co dung 2 ham onCreateDialog, onDateSet o duoi de set gia tri cho no
        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        // su kien nhan nut luu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edAmount.getText().length() < 1 ) {
                    Toast.makeText(getApplicationContext(), "Bạn cần điền số tiền chi tiêu!", Toast.LENGTH_SHORT).show();
                    edAmount.requestFocus();
                } else if(edReasonCA.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), "Bạn cần điền lý do chi tiêu!", Toast.LENGTH_SHORT).show();
                    edReasonCA.requestFocus();
                } else {
                    String amount = edAmount.getText().toString();
                    if(spTypeCA.getSelectedItem().equals("Khoản chi")) {
                        amount = "-" + edAmount.getText().toString();
                    }

                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imgBill.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] arr = byteArrayOutputStream.toByteArray();
                    boolean check = database.addCA(spAccount.getSelectedItem().toString(), spTypeCA.getSelectedItem().toString(),
                            amount, edReasonCA.getText().toString(), spGroupCA.getSelectedItem().toString(),
                            edDateCA.getText().toString(), arr);

                    if(check == true) {
                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        int paymentAmount = Integer.parseInt(database.getAmountByTypeAndTime("Khoản chi", "2019"));
                        SharedPreferences preferences = getSharedPreferences("MoneyLimit", MODE_PRIVATE);
                        int limit = preferences.getInt("MoneyLimit", -1);
                        if(true) {
                            addNotification();
//                            Toast.makeText(getApplicationContext(), "Tháng này bạn đã tiêu nhiều tiền", Toast.LENGTH_SHORT).show();
                        }
//
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    database.close();
                    Intent i = new Intent(AddCAActivity.this, MainActivity.class);
                    startActivity(i);

                }

            }
        });
    }

    // Hàm ánh xạ các trường với các id trong file xml
    private void mapField() {
        btnSave = (Button) findViewById(R.id.btnSave);
        edDateCA = (EditText) findViewById(R.id.edDateCA);
        edAmount = (EditText) findViewById(R.id.edAmount);
        edReasonCA = (EditText) findViewById(R.id.edReason);
        imgDatePicker = (ImageView) findViewById(R.id.imgDatePicker);
        spAccount = (Spinner) findViewById(R.id.spinAccount);
        spTypeCA = (Spinner)  findViewById(R.id.spinTypeCA);
        spGroupCA = (Spinner)  findViewById(R.id.spinGroup);
        spStatusCA = (Spinner)  findViewById(R.id.spinStatus);
        imgBill = (ImageView) findViewById(R.id.imgBill);
        ibtnCamera = (ImageButton) findViewById(R.id.ibtnCamera);
        ibtnForder = (ImageButton) findViewById(R.id.ibtnFolder);
    }

    // hàm xử lý việc lấy chụp ảnh và lấy ảnh ra từ camera và folder
    private void getImageForCameraForder() {
        ibtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA );
            }
        });

        ibtnForder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            imgBill.setImageBitmap(bitmap);
        } else if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = Bitmap.createScaledBitmap(bitmap, 100,100, true);
                imgBill.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);

        }
        return null;

    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear+1;
            mDay = dayOfMonth;
            String day = mDay + "", month = mMonth+"";
            if(mDay < 10) day = "0"+mDay;
            if(mMonth < 10) month = "0"+mMonth;
            edDateCA.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(mYear));

        }

    };

    private void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "chanelID")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Personal Finance")
                .setContentText("Hạn mức, Bạn đã bội chi. Bạn hãy điều hiển thói quen thu chi nhé")
                .setPriority(Notification.PRIORITY_MAX);
        Intent intent = new Intent(getApplicationContext(), AddCAActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("chanelID","MyChannel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, builder.build());
    }

}
