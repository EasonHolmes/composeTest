package com.example.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bossy.component.DaemonBaseService;

public class CFNService extends DaemonBaseService {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    class StopForegroundRunnable implements Runnable {
        @Override
        public void run() {
            Log.e("ethan","StopForegroundRunnable");
//            CFNService.this.stopForeground(true);
        }
    }

    private String defineChannelName() {
        return "Default Channel";
    }

    public static void startStayForegroundService(Context context) {
//        ContextCompat.startForegroundService(context, new Intent(context, StayService.class));
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(26)
    private void createNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(getPackageName(), defineChannelName(), NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription("android");
        notificationChannel.enableLights(false);
        notificationChannel.enableVibration(false);
        notificationChannel.setSound(null, null);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(notificationChannel);
    }

    private void startCNFForeground() {
        if (Build.VERSION.SDK_INT >= 26) {
//            createNotificationChannel();
            Notification.Builder builder = new Notification.Builder(this, getPackageName());
            builder.setContentTitle(getResources().getString(R.string.app_name));
            builder.setContentText(getResources().getString(R.string.app_name));
            startForeground(4130, builder.build());
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ethan", "CFN Service onCreate sProcessName : " );
        startCNFForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i2) {
        Log.e("ethan", "onStartCommand");
//        startCNFForeground();
//        startStayForegroundService(this);
        this.mHandler.postDelayed(new StopForegroundRunnable(), 500);
        return super.onStartCommand(intent, i, i2);
    }
}