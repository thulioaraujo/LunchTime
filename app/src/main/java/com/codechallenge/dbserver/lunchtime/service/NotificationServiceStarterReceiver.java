package com.codechallenge.dbserver.lunchtime.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationServiceStarterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, LunchTimeService.class);
        service.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(service);
    }
}
