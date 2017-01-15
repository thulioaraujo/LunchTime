package com.codechallenge.dbserver.lunchtime.views;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.codechallenge.dbserver.lunchtime.R;
import com.codechallenge.dbserver.lunchtime.service.LunchTimeService;
import com.codechallenge.dbserver.lunchtime.service.NotificationServiceStarterReceiver;
import com.codechallenge.dbserver.lunchtime.utils.MainAplicationConstants;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by thulioaraujo on 1/12/2017.
 *
 * This activity was created with the purpose to check and adjust some pre-configuration parameters
 * before go to the MainActivity.
 */
public class LaunchActivity extends AppCompatActivity implements Runnable {

    // This PendingIntent will start the Broadcast service
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        if (!isMyServiceRunning()){
            Intent intent = new Intent(this, LunchTimeService.class);
            startService(intent);
        }
        // Setup an alarm manager to display messages on an specific time.
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.AM_PM, Calendar.PM);
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

        Intent myIntent = new Intent(LaunchActivity.this, NotificationServiceStarterReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(LaunchActivity.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);

        Thread thread = new Thread(this);
        thread.start();
    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

        // Check if the user has been logged before
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(MainAplicationConstants.CATEGORY, 0);
        boolean isAlreadyLogged = sharedPref.getBoolean(getString(R.string.preferences_login), Boolean.FALSE);

        if (isAlreadyLogged) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
        finish();
        }
    };

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            this.handler.sendEmptyMessage(0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * This function check whether the service is still running or not.
     *
     * @return
     */
    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (LunchTimeService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
