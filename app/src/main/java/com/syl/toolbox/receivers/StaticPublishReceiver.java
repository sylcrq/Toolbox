package com.syl.toolbox.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StaticPublishReceiver extends BroadcastReceiver {

    public static final String TAG = StaticPublishReceiver.class.getSimpleName();

    public StaticPublishReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
    }
}
