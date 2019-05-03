package com.example.personal;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec ca = tabHost.newTabSpec("Thu Chi");
        ca.setIndicator("Thu Chi", getResources().getDrawable(R.mipmap.ic_launcher));
        Intent i = new Intent(MainActivity.this, CAActivity.class);
        ca.setContent(i);
        tabHost.addTab(ca);
        tabHost.setCurrentTab(0);

        TabHost.TabSpec statistics = tabHost.newTabSpec("Thống kê");
        statistics.setIndicator("Thống kê", getResources().getDrawable(R.mipmap.ic_launcher));
        Intent i1 = new Intent(MainActivity.this, StatisticsActivity.class);
        statistics.setContent(i1);
        tabHost.addTab(statistics);
//        tabHost.setCurrentTab(1);

        TabHost.TabSpec limit = tabHost.newTabSpec("Hạn mức");
        limit.setIndicator("Hạn mức", getResources().getDrawable(R.mipmap.ic_launcher));
        Intent i2 = new Intent(MainActivity.this, LimitActivity.class);
        limit.setContent(i2);
        tabHost.addTab(limit);
//        tabHost.setCurrentTab(2);
    }
}
