package com.syl.toolbox.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.syl.toolbox.R;
import com.syl.toolbox.views.activities.MainActivity;

import java.util.Random;

public class LocalService extends Service {

    public static final String TAG = LocalService.class.getSimpleName();

    private NotificationManager mNotificationManager;
    private int NOTIFICATION = R.string.local_service_started;
    private final IBinder mBinder = new LocalBinder();
    private final Random mRandom = new Random();

    public LocalService() {
    }

    /**
     * Class used for the client Binder.
     */
    public class LocalBinder extends Binder {
        public LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");

        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");

        mNotificationManager.cancel(NOTIFICATION);
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: " + "received start id=" + startId + " : " + intent);

        return START_NOT_STICKY;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);

        Log.d(TAG, "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");

        return super.onUnbind(intent);
    }

    /**
     * method for clients
     *
     * @return
     */
    public int getRandomNumber() {
        return mRandom.nextInt(100);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification() {

        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_stub)
                .setTicker(getText(R.string.local_service_started))
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Local Service Title")
                .setContentText(getText(R.string.local_service_started))
                .setContentIntent(intent)
                .build();

        mNotificationManager.notify(NOTIFICATION, notification);
    }
}
