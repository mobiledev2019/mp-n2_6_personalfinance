package com.example.personal;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import java.util.Calendar;

public class MainActivity extends TabActivity {
    private Calendar calendar;
    private static final int NOTIFICATION_ID = 0;
    private static final String CHANNEL_ID = "channel_id_personal";
    private NotificationManager notificationManager;

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

        TabHost.TabSpec limit = tabHost.newTabSpec("Hạn mức");
        limit.setIndicator("Hạn mức", getResources().getDrawable(R.mipmap.ic_launcher));
        Intent i2 = new Intent(MainActivity.this, LimitActivity.class);
        limit.setContent(i2);
        tabHost.addTab(limit);

        // thêm chức năng tự động thông báo cho người dùng nếu người dùng hôm nay chưa thêm khoản thu khoản chi nào, 21h tối sẽ thông b


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // thiết lập thời gian thông báo
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
        // sẽ lặp lại vào bao lâu - mặc định đây là lặp lại 1 ngày 1 lần vào lúc 21h
        long repeatInterval = AlarmManager.INTERVAL_DAY;

        long triggerTime = calendar.getTimeInMillis();
        if(alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerTime, repeatInterval, pendingIntent);
        }

        createNotificationChannel();
    }

    // ham tao 1 channel cho app
    public void createNotificationChannel() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // notification channel chỉ có thể dùng cho API 26(phiên banr OREO or trở lên)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Personal Finance notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Thông báo của ứng dụng personal finance");
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }
}
