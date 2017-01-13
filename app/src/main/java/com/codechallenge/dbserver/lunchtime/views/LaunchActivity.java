package com.codechallenge.dbserver.lunchtime.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.codechallenge.dbserver.lunchtime.R;
import com.codechallenge.dbserver.lunchtime.service.NotificationEventReceiver;
import com.codechallenge.dbserver.lunchtime.utils.MainAplicationConstants;

public class LaunchActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

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
            NotificationEventReceiver.setupAlarm(getApplicationContext());
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
}
