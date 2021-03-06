package com.example.user.coffeemaker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by user on 12/02/2016.
 */
public class RingTonePlayService extends Service {
    MediaPlayer ringtone;
    ConnectionDetector connection;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final NotificationManager   mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        MainActivity mainActivity = new MainActivity();
       // mainActivity.principalActivity.getApplicationContext();
        //connection = new ConnectionDetector(mainActivity.principalActivity);
        Notification mNotify=null;
        if(mainActivity.connection.isConnectingToInternet()){
            mNotify  = new Notification.Builder(this)
                    .setContentTitle("Time to Live. Time to COFFEE!")
                    .setContentText("Your CoffeMaker is ready!")
                    .setSmallIcon(R.drawable.coffe_icon)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();
            //new MainActivity.JSonTask().execute("1");


            mainActivity.mWorker = (MainActivity.JSonTask) new MainActivity.JSonTask(mainActivity.principalActivity).execute("1");
            ringtone = MediaPlayer.create(this,R.raw.jazz_ringtone);
            ringtone.setLooping(false);
            ringtone.start();
            mNM.notify(0,mNotify);
        }else{
            mNotify  = new Notification.Builder(this)
                    .setContentTitle("Error")
                    .setContentText("Check your internet connection")
                    .setSmallIcon(R.drawable.coffe_icon)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();
            mNM.notify(0,mNotify);
            //System.exit(0);
        }
        return START_NOT_STICKY;
    }
}
