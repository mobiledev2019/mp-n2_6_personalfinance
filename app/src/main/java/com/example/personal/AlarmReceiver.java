package com.example.personal;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

// class thực hiện chức năng tính toán thông báo cho người dùng nếu hôm nay người dùng chưa thêm bất cứ 1 phiếu thu phiếu chi nào
public class AlarmReceiver extends BroadcastReceiver {
    DatabaseHandler database;
    private static final int NOTIFICATION_ID = 0;
    private static final String CHANNEL_ID = "channel_id_personal";
    private NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        database = new DatabaseHandler(context);
        database.open();
//        int caCash = Integer.parseInt(database.getAmountByAccount("Tiền mặt"));
//        Toast.makeText(context, "Hôm nay bạn chưa thêm khoản thu chi nào",  Toast.LENGTH_LONG).show();
        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(calendar.getTime());
        boolean check = database.checkCAbyDate(date);
        if(!check)
            deliverNotification(context);
    }

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Personal Finance")
                .setContentText("Hôm nay bạn chưa thêm khoản thu chi nào. Bạn dừng quên nhé!")
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // gửi thông baos
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }




}
