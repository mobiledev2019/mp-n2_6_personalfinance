package com.example.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CAActivity extends AppCompatActivity {
    Button btnAddCA, btnHistory, btnExit;
    TextView tvRemain, tvCash, tvSaveMoney, tvCredit;
    DatabaseHandler databaseHandler;
    int caCash,caSaveMoney, caCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca);
        btnAddCA = (Button) findViewById(R.id.btnAddCA);
        btnHistory =(Button) findViewById(R.id.btnHistory);
        btnExit = (Button) findViewById(R.id.btnExit);

        tvRemain = (TextView) findViewById(R.id.tvRemain);
        tvCash = (TextView) findViewById(R.id.tvCash);
        tvSaveMoney = (TextView) findViewById(R.id.tvSaveMoney);
        tvCredit = (TextView) findViewById(R.id.tvCredit);

        databaseHandler =new DatabaseHandler(this);
        databaseHandler.open();
        caCash = 0;
        caSaveMoney = 0;
        caCredit = 0;
        caCash = Integer.parseInt(databaseHandler.getAmountByAccount("Tiền mặt"));
        caSaveMoney = Integer.parseInt(databaseHandler.getAmountByAccount("Tiền tiết kiệm"));
        caCredit = Integer.parseInt(databaseHandler.getAmountByAccount("Thẻ tín dụng"));

        tvCash.setText(caCash+"");
        tvSaveMoney.setText(caSaveMoney + "");
        tvCredit.setText(caCredit + "");
        tvRemain.setText(caCash + caSaveMoney + caCredit + "");

        // su kien nhi nhan nut them giao dich
        btnAddCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CAActivity.this, AddCAActivity.class);
                startActivity(i);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CAActivity.this, ListCAActtivity.class);
                startActivity(i);
            }
        });

        // khi nhan nut thoat thi thoat khoi ung dung
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}
