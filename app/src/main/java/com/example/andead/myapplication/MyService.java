package com.example.andead.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.lang.annotation.Documented;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("Service", "Service started");
        Thread thread = new Thread(new ServiceThread());
        thread.start();
    }

    @Override
    public void onDestroy() {
        Log.d("Service", "Service destroyed");
        super.onDestroy();
    }

    public void sendNotify(String textNotify, int iId) {
        Notification.Builder notifBuilder = new Notification.Builder(getApplicationContext());
        notifBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notifBuilder.setContentTitle("Test");
        notifBuilder.setContentText(textNotify);

        Notification notification = notifBuilder.build();
        NotificationManager notifManger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifManger.notify(iId, notification);
    }

    class ServiceThread implements Runnable {

        private boolean bFinish;

        public void finish() {
            bFinish = true;
        }

        @Override
        public void run() {
            bFinish = false;

            for (int i = 0; i < 5; i++) {
                if (bFinish)
                    break;

                try {
                    TimeUnit.SECONDS.sleep(3);
                    sendNotify("i = " + i, i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            MyService.this.stopSelf();
        }
    }
}
