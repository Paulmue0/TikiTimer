package com.tiki.tikitimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.example.tikitimer.R;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    private static final String TAG = "Receiver: ";


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean shouldStop=HelperSharedPreferences.getSharedPreferencesBoolean(context.getApplicationContext(),HelperSharedPreferences.SharedPreferencesKeys.shouldStop, false);

        if (shouldStop){
            Toast.makeText(context, "Stopped Tiki Timer",Toast.LENGTH_LONG).show();
        }
        else {
            mp=MediaPlayer.create(context, R.raw.tiki);
            mp.start();
            Log.v(TAG, "ringing");
            Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
            int timeInMillis = HelperSharedPreferences.getSharedPreferencesInt(context.getApplicationContext(),HelperSharedPreferences.SharedPreferencesKeys.tikiTimeInMillis, 1200000);// Production
            //int timeInMillis = 5000; // Test

            System.out.println(HelperSharedPreferences.getSharedPreferencesBoolean(context.getApplicationContext(),HelperSharedPreferences.SharedPreferencesKeys.shuffledTime, true));

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            Intent notificationIntent = new Intent(context, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context.getApplicationContext(), 234324243, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            alarmManager.cancel(pendingIntent);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeInMillis, pendingIntent); // +100 is to set nearly to the current time
            Toast.makeText(context, "New alarm set in " + timeInMillis/60/1000 + " minutes",Toast.LENGTH_LONG).show();
        }



    }
}