package com.example.personal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class LimitActivity extends AppCompatActivity {
    TextView tvMoneyLimit;
    EditText edMoneyLimit;
    Button btnSaveLimit;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savingInstanceState) {
        super.onCreate(savingInstanceState);
        setContentView(R.layout.activity_limit);
        edMoneyLimit = (EditText) findViewById(R.id.edMoneylimit);
        btnSaveLimit = (Button) findViewById(R.id.btnSaveLimit);
        tvMoneyLimit = (TextView) findViewById(R.id.tvMoneyLimit);
        // chức năn tự động format số tiền khi người dùng nhập vào ô số tiền
        edMoneyLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edMoneyLimit.removeTextChangedListener(this);
                try {
                    String strAmount = s.toString();
                    long amount;
                    if(strAmount.contains(".")) {
                        strAmount = strAmount.replaceAll("\\.", "");
                    }
                    amount = Long.parseLong(strAmount);
                    String pattern = "#,###,###,###";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    String formatAmount = decimalFormat.format(amount);
                    edMoneyLimit.setText(formatAmount);
                    edMoneyLimit.setSelection(edMoneyLimit.getText().length());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                edMoneyLimit.addTextChangedListener(this);
            }
        });

        preferences = getSharedPreferences("MoneyLimit", MODE_PRIVATE);
        int limit = preferences.getInt("MoneyLimit", 0);


        tvMoneyLimit.setText(formatCurrency(limit));
        btnSaveLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = edMoneyLimit.getText().toString().replaceAll("\\.", "");
                int  moneyLimit = Integer.parseInt(money);
                preferences = getSharedPreferences("MoneyLimit", MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("MoneyLimit", moneyLimit);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Thêm hạn mức chi thành công", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LimitActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    // ham format tien
    public String formatCurrency(int  limit) {
        String result = "";
        String pattern = "#,###,###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        result = decimalFormat.format(limit);
        return result;
    }
}
