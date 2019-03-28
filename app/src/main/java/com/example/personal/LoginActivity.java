package com.example.personal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    String default_pin="1";
    EditText etOldPin,etNewPin,etRePin;
    Button btnCPin;
    SharedPreferences preferences;
    SharedPreferences preferences1;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText etLogin=(EditText)findViewById(R.id.etUsername);
        final EditText password=(EditText)findViewById(R.id.etPassword);
        Button btnLogin=(Button)findViewById(R.id.btnLogin);
        Button btnReset=(Button)findViewById(R.id.btnReset);
        preferences=getSharedPreferences("username",MODE_PRIVATE);
        preferences1=getSharedPreferences("password",MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storedUsername=preferences.getString("username","admin");
                String storePassword = preferences1.getString("password", "1");
                if(etLogin.getText().toString().equals(storedUsername) && password.getText().toString().equals(storePassword) ){
                    Intent i =new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    etLogin.setError("Sai thông tin đăng nhập");
                }
            }

        });

        password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String storedUsername=preferences.getString("username","admin");
                    String storePassword = preferences1.getString("password", "1");
                    if(etLogin.getText().toString().equals(storedUsername) && password.getText().toString().equals(storePassword) ){
                        Intent i =new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        etLogin.setError("Sai thông tin đăng nhập");
                    }
                    return true;
                }
                return false;
            }
        });
    }
}

