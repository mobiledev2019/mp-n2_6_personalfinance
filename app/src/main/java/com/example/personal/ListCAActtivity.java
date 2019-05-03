package com.example.personal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListCAActtivity extends AppCompatActivity {
    private ListView listView;
    DatabaseHandler database;
    ReceiptPayment receiptPayment;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_listca);
        database = new DatabaseHandler(this);
        database.open();

        List<ReceiptPayment> receiptPayments = database.getReceiptPayment();

        listView = (ListView) findViewById(R.id.listCA);

        ArrayAdapter<ReceiptPayment> adapter = new ArrayAdapter<ReceiptPayment>(this, android.R.layout.simple_list_item_1, receiptPayments);
        listView.setAdapter(adapter);
    }
}
