package com.syl.toolbox.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * 更新上传服务进度抽象基类
 *
 * Created by shenyunlong on 10/30/15.
 */
public abstract class UploadServiceReceiver extends BroadcastReceiver {

    public static final String TAG = UploadServiceReceiver.class.getSimpleName();

    public static final String UPLOAD_BROADCAST_ACTION = "com.syl.toolbox.receivers.uploadservicereceiver";
    public static final String UPLOAD_STATUS = "UPLOAD_STATUS";
    public static final String UPLOAD_PROGRESS = "UPLOAD_PROGRESS";

    public static final int UPLOAD_STATUS_START = 1;
    public static final int UPLOAD_STATUS_STOP = 2;
    public static final int UPLOAD_STATUS_COMPLETE = 3;
    public static final int UPLOAD_STATUS_ERROR = 4;
    public static final int UPLOAD_STATUS_PROGRESS = 5;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        if(intent.getAction().equals(UPLOAD_BROADCAST_ACTION)) {
            int status = intent.getIntExtra(UPLOAD_STATUS, 0);

            switch (status) {
                case UPLOAD_STATUS_START:
                    onUploadStart();
                    break;
                case UPLOAD_STATUS_STOP:
                    onUploadStop();
                    break;
                case UPLOAD_STATUS_COMPLETE:
                    onUploadComplete();
                    break;
                case UPLOAD_STATUS_ERROR:
                    onUploadError();
                    break;
                case UPLOAD_STATUS_PROGRESS:
                    int percent = intent.getIntExtra(UPLOAD_PROGRESS, 0);
                    updateUploadProgress(percent);
                    break;
            }
        }
    }

    /**
     * 注册UploadServiceReceiver
     *
     * @param context
     */
    public void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPLOAD_BROADCAST_ACTION);

        context.registerReceiver(this, filter);
    }

    /**
     * 注销UploadServiceReceiver
     *
     * @param context
     */
    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public abstract void onUploadStart();

    public abstract void onUploadStop();

    public abstract void onUploadComplete();

    public abstract void onUploadError();

    public abstract void updateUploadProgress(int percent);
}
