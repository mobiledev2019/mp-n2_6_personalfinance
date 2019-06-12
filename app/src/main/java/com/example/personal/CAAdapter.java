package com.example.personal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CAAdapter extends ArrayAdapter<ReceiptPayment> {
    private Context context;
    private int layout;
    private List<ReceiptPayment> receiptPayments = new ArrayList<>();

    public CAAdapter(Context context, int layout, List<ReceiptPayment> receiptPayments) {
        super(context, layout, receiptPayments);
        this.context = context;
        this.layout = layout;
        this.receiptPayments = receiptPayments;
    }

    @Override
    public int getCount() {
        return receiptPayments.size();
    }

    private class ViewHolder {
        ImageView imgBill;
        TextView tvGroup, tvDate, tvAmount, tvAccount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(layout, null);
            convertView = LayoutInflater.from(context).inflate(R.layout.ca_item, parent, false);
            viewHolder.imgBill = (ImageView) convertView.findViewById(R.id.imgBill);
            viewHolder.tvGroup = (TextView) convertView.findViewById(R.id.tvGroup);
            viewHolder.tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDateCa);
            viewHolder.tvAccount = (TextView) convertView.findViewById(R.id.tvAccount);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ReceiptPayment receiptPayment = receiptPayments.get(position);

        viewHolder.tvGroup.setText(receiptPayment.getGroupCA());
        viewHolder.tvDate.setText(receiptPayment.getDateCA());
        viewHolder.tvAmount.setText(receiptPayment.getAmountCA());
        viewHolder.tvAccount.setText(receiptPayment.getAccountCA());
        // chuyển byte[] -> bitmap
        byte[] bill = receiptPayment.getImageBill();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bill, 0, bill.length);
        viewHolder.imgBill.setImageBitmap(bitmap);

        return convertView;
    }
}
