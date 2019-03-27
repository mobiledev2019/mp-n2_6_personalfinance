package com.example.personal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemgiaodichActivity extends AppCompatActivity implements
        OnItemSelectedListener {
    Spinner sptk,sploaigd,spinphannhom,spintrangthai;
    Button luu;
    EditText ngaythang , sotien, lydo;
    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    DatabaseHandler db;
    String spinkhoanthu = "Khoản Thu";
    String spinkhoanchi = "Khoản Chi";
    String ngay;
    String thang;
    Toast mToast;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themgiaodich);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        db= new DatabaseHandler(this);
        db.open();
        sptk=(Spinner)findViewById(R.id.spintk);
        luu=(Button)findViewById(R.id.savegd);
        ngaythang = (EditText) findViewById(R.id.etngaygiaodich);
        sotien = (EditText) findViewById(R.id.edsotien);
        lydo = (EditText) findViewById(R.id.edlydo);
        imageView = (ImageView)findViewById(R.id.imageViewxx);

        ngaythang.setEnabled(false);
        ngaythang.setFocusable(false);
        Calendar c= Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DATE);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ngaythang.setText( sdf.format(c.getTime()));

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        luu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(ngaythang.getText().length()<1||sotien.getText().length()<1||lydo.getText().length()<1){
                    Toast.makeText(getApplicationContext(), "Bạn cần nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                    sotien.requestFocus();

                }else{
                    if(mDay<10){
                        ngay="0"+mDay;

                    }else{
                        ngay=mDay+"";

                    }
                    if((mMonth+1)<10){
                        thang="0"+(mMonth+1);

                    }else{
                        thang=(mMonth+1)+"";

                    }
                    try {
                        if (sploaigd.getSelectedItem().equals("Khoản Chi")) {

                            db.themgiaodich(sptk.getSelectedItem().toString(), sploaigd.getSelectedItem().toString(), "-" + sotien.getText().toString(), lydo.getText().toString(), spinphannhom.getSelectedItem().toString(), ngaythang.getText().toString(), ngay + "", "" + thang, mYear + "");
                            db.close();
                            LayoutInflater inflater = getLayoutInflater();
                            View mToastView = inflater.inflate(R.layout.luuthanhcong,
                                    null);
                            mToast = new Toast(ThemgiaodichActivity.this);
                            mToast.setView(mToastView);
                            mToast.show();
                            Intent i =new Intent(ThemgiaodichActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            db.themgiaodich(sptk.getSelectedItem().toString(), sploaigd.getSelectedItem().toString(), sotien.getText().toString(), lydo.getText().toString(), spinphannhom.getSelectedItem().toString(), ngaythang.getText().toString(), ngay + "", "" + thang, mYear + "");
                            db.close();
                            LayoutInflater inflater = getLayoutInflater();
                            View mToastView = inflater.inflate(R.layout.luuthanhcong,
                                    null);
                            mToast = new Toast(ThemgiaodichActivity.this);
                            mToast.setView(mToastView);
                            mToast.show();

                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Chưa tạo khoản thu - khoản chi", Toast.LENGTH_SHORT).show();
                    }


                    Intent intent = new Intent(getApplicationContext(), ThemgiaodichActivity.class);
                    startActivityForResult(intent, 8);
                    finish();
                }

            }
        });
        final List<String> taikhoan=new ArrayList<String>();
        taikhoan.add("Tiền Mặt");
        taikhoan.add("Tiết Kiệm");
        taikhoan.add("Tín Dụng");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, taikhoan);
        sptk.setAdapter(adapter);

        sploaigd=(Spinner)findViewById(R.id.spinloaigd);
        final List<String> loaigd=new ArrayList<String>();

        loaigd.add("Khoản Thu");

        loaigd.add("Khoản Chi");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, loaigd);
        sploaigd.setAdapter(adapter1);

        spinphannhom=(Spinner)findViewById(R.id.spinphannhom);

        sploaigd.setOnItemSelectedListener(this);
        spintrangthai = (Spinner)findViewById(R.id.spintrangthai);
        final List<String> trangthai=new ArrayList<String>();

        trangthai.add("Hoàn tất");

        trangthai.add("Chưa hoàn tất");

        ArrayAdapter<String> adap = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, trangthai);
        spintrangthai.setAdapter(adap);

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
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
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            ngaythang.setText(new StringBuilder().append(mDay).append("/").append(mMonth+1).append("/").append(mYear));

        }

    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sp1= sploaigd.getSelectedItem().toString();
        if(sp1.contentEquals("Khoản Chi")) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < db.getAllNames(spinkhoanchi).size(); i++) {
                list.add(db.getAllNames(spinkhoanchi).get(i));

            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, list);
            dataAdapter.notifyDataSetChanged();
            spinphannhom.setAdapter(dataAdapter);
        }
        if(sp1.contentEquals("Khoản Thu")) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < db.getAllNames(spinkhoanthu).size(); i++) {
                list.add(db.getAllNames(spinkhoanthu).get(i));

            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, list);
            dataAdapter.notifyDataSetChanged();
            spinphannhom.setAdapter(dataAdapter);


        }
        // TODO Auto-generated method stub
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

}