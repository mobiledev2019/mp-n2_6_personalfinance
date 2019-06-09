package com.example.personal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        preferences = getSharedPreferences("preMoneyLimit", MODE_PRIVATE);
        int limit = preferences.getInt("MoneyLimit", 0);
        tvMoneyLimit.setText(limit+"");
        btnSaveLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  moneyLimit = Integer.parseInt(edMoneyLimit.getText().toString());
                preferences = getSharedPreferences("preMoneyLimit", MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("MoneyLimit", moneyLimit);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Thêm hạn mức chi thành công", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LimitActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
