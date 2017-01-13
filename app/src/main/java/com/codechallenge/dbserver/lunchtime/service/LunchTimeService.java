package com.codechallenge.dbserver.lunchtime.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.codechallenge.dbserver.lunchtime.R;
import com.codechallenge.dbserver.lunchtime.controller.VotingController;
import com.codechallenge.dbserver.lunchtime.views.LaunchActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class LunchTimeService extends IntentService {
    private static final int NOTIFICATION_ID = 1;

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public LunchTimeService() {
        super(LunchTimeService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, LunchTimeService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, LunchTimeService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                processStartNotification(VotingController.getInstance().getmMostVotedRestaurant());
            }
            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification(intent);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, finish handling a notification event");
    }

    private void processStartNotification(JSONObject obj) {

        String restaurantName = null;
        String restaurantVotes = null;

        try {
            restaurantName = obj.getString("restaurant_name");
            restaurantVotes = obj.getString("votes_qtd");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = new Notification();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        builder.setContentTitle("Lunch Time")
                .setAutoCancel(true)
                .setDefaults(notification.defaults)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentText("The chosen restaurant was: " + restaurantName + "with " + restaurantVotes + "votes!")
                .setSmallIcon(R.mipmap.ic_launcher);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                new Intent(this, LaunchActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
