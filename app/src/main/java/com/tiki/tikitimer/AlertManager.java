package com.tiki.tikitimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlertManager {
    Context context;
    public AlertManager(Context con){
        context = con;
    }


    public void startAlert(){
        // Starting milliseconds
        //int timeInMillis = 5000; // Test
        int timeInMillis = HelperSharedPreferences.getSharedPreferencesInt(context.getApplicationContext(),HelperSharedPreferences.SharedPreferencesKeys.tikiTimeInMillis, 1200000);
        System.out.println(timeInMillis);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeInMillis, pendingIntent); // +100 is to set nearly to the current time
        Toast.makeText(context, "New Tiki Timer set in " + timeInMillis/1000/60 + " minutes",Toast.LENGTH_LONG).show();
    }
    public void stopAlert(){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }
}
