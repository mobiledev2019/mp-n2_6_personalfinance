package com.example.personal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListCAActtivity extends AppCompatActivity {
    private ListView listView;
    DatabaseHandler database;
    ReceiptPayment receiptPayment;
    CAAdapter caAdapter;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_listca);

        database = new DatabaseHandler(this);
        database.open();

        List<ReceiptPayment> receiptPayments = database.getReceiptPayment();
        // sắp xếp danh sách giảm dần theo thời gian để lúc hiện thị ra hợp lý
        Collections.sort(receiptPayments, new Comparator<ReceiptPayment>() {
            @Override
            public int compare(ReceiptPayment o1, ReceiptPayment o2) {
                return getYYMYMDD(o2.getDateCA()).compareTo(getYYMYMDD(o1.getDateCA()));
            }
        });
        listView = (ListView) findViewById(R.id.listCA);
        caAdapter = new CAAdapter(this, R.layout.ca_item, receiptPayments);
        listView.setAdapter(caAdapter);
    }

    // hàm chuyển từ dang dd/MM/yyyy -> yyyyMMdd để phục vụ cho việc so sánh ngày tháng năm
    private String getYYMYMDD(String date) {
        String str[] = date.split("/");
        String result = "";
        for(int i = str.length-1; i >= 0; i--) {
            result += str[i];
        }
        return result;
    }
}
