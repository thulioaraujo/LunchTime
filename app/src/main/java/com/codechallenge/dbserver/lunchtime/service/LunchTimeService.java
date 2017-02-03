package com.codechallenge.dbserver.lunchtime.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.codechallenge.dbserver.lunchtime.R;
import com.codechallenge.dbserver.lunchtime.presenter.VotingController;
import com.codechallenge.dbserver.lunchtime.views.LaunchActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class LunchTimeService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private ProcessRestaurantOfTheDayTask processRestaurantOfTheDayTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.AM_PM, Calendar.PM);
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

        if (cur_cal.get(Calendar.HOUR_OF_DAY) == cal.get(Calendar.HOUR_OF_DAY) &&
            cur_cal.get(Calendar.MINUTE) == cal.get(Calendar.MINUTE) &&
            cur_cal.get(Calendar.SECOND) == cal.get(Calendar.SECOND)) {
            processRestaurantOfTheDayTask = new ProcessRestaurantOfTheDayTask();
            processRestaurantOfTheDayTask.execute();
        }
        return Service.START_STICKY;
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
                new Intent(this.getApplicationContext(), LaunchActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class ProcessRestaurantOfTheDayTask extends AsyncTask<Void, Void, Boolean> {

        private JSONObject obj;

        @Override
        protected Boolean doInBackground(Void... voids) {
            obj = VotingController.getInstance().getmMostVotedRestaurant();
            if (obj != null) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if (aBoolean) {
                processStartNotification(obj);
            }
        }
    }
}
