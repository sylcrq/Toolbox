package com.syl.toolbox.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.syl.toolbox.receivers.UploadServiceReceiver;
import com.syl.toolbox.upload.MultipartUploadTask;
import com.syl.toolbox.upload.UploadNotificationConfig;


/**
 * Upload Service
 *
 */
public class UploadService extends IntentService {

    public static final String TAG = UploadService.class.getSimpleName();

    public static final String ACTION_UPLOAD = "com.syl.toolbox.services.action.UPLOAD";
//    public static final String UPLOAD_NOTIFICATION_TITLE = "My Upload Service Notification";
    public static final int UPLOAD_NOTIFICATION_ID = 1024;

    public static final String UPLOAD_REQUEST_TYPE = "UPLOAD_REQUEST_TYPE";
    public static final String UPLOAD_REQUEST_ID = "UPLOAD_REQUEST_ID";
    public static final String UPLOAD_REQUEST_URL = "UPLOAD_REQUEST_URL";
    public static final String UPLOAD_REQUEST_METHOD = "UPLOAD_REQUEST_METHOD";
    public static final String UPLOAD_REQUEST_HEADERS = "UPLOAD_REQUEST_HEADERS";
    public static final String UPLOAD_REQUEST_FILES = "UPLOAD_REQUEST_FILES";
    public static final String UPLOAD_REQUEST_NOTIFICATION_CONFIG = "UPLOAD_REQUEST_NOTIFICATION_CONFIG";

    public static final int UPLOAD_REQUEST_MULTIPART = 1;

    private UploadNotificationConfig mNotificationConfig;

    public UploadService() {
        super("MyIntentService");
    }

//    public static void startActionUpload(Context context) {
//        Intent intent = new Intent(context, UploadService.class);
//        intent.setAction(ACTION_UPLOAD);
//        context.startService(intent);
//    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if(ACTION_UPLOAD.equals(action)) {
                if(UPLOAD_REQUEST_MULTIPART == intent.getIntExtra(UPLOAD_REQUEST_TYPE, 0)) {
                    mNotificationConfig = intent.getParcelableExtra(UPLOAD_REQUEST_NOTIFICATION_CONFIG);

                    MultipartUploadTask task = new MultipartUploadTask(this, intent);
                    task.upload();
                }
            }
        }
    }

//    private void handleActionUpload() {
//        Log.d(TAG, "Thread "+Thread.currentThread().getId()+" # handleActionUpload");
//
//        final String path = "/sdcard/DCIM/IQIYI/IQIYI_2015_1027_140229.mp4";
//        final String name = "IQIYI_2015_1027_140229";
//        final String filename = "IQIYI_2015_1027_140229.mp4";
//        final String contentType = "video/mpeg4";
//        MultipartUploadFile file = new MultipartUploadFile(path, name, filename, contentType);
//
//        String url = "http://10.1.36.99:3000/upload/multipart";
//        String url = "http://posttestserver.com/post.php";
//        MultipartUploadRequest request = new MultipartUploadRequest(url, MultipartUploadTask.METHOD_POST);
//        request.addRequestFile(file);
//
//        MultipartUploadTask task = new MultipartUploadTask(request, this);
//        task.upload();
//    }

    public void sendUploadStart() {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_START);
        sendBroadcast(intent);

        if(mNotificationConfig != null) {
            showNotification(mNotificationConfig.getStartTip());
        }
    }

    public void sendUploadStop() {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_STOP);
        sendBroadcast(intent);

        if(mNotificationConfig != null) {
            showNotification(mNotificationConfig.getStopTip());
        }
    }

    public void sendUploadComplete() {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_COMPLETE);
        sendBroadcast(intent);

        if(mNotificationConfig != null) {
            showNotification(mNotificationConfig.getCompleteTip());
        }
    }

    public void sendUploadError() {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_ERROR);
        sendBroadcast(intent);

        if(mNotificationConfig != null) {
            showNotification(mNotificationConfig.getErrorTip());
        }
    }

    public void sendUploadProgress(int percent) {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_PROGRESS);
        intent.putExtra(UploadServiceReceiver.UPLOAD_PROGRESS, percent);
        sendBroadcast(intent);

        if(mNotificationConfig != null) {
            showNotification("Uploading...");
        }
    }

    /**
     * 显示通知栏
     *
     * @param content
     */
    public void showNotification(String content) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(mNotificationConfig.getIcon());
        builder.setContentTitle(mNotificationConfig.getContentTitle());
        builder.setContentText(content);

        // setContentIntent
//        Intent intent = new Intent(this, ListViewActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(ListViewActivity.class);
        stackBuilder.addNextIntent(mNotificationConfig.getClickIntent());

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // setStyle
//        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
//        String[] events = new String[6];
//        style.setBigContentTitle("Event tracker details:");
//
//        for(int i=0; i<events.length; i++) {
//            style.addLine("event " + i);
//        }
//        builder.setStyle(style);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(UPLOAD_NOTIFICATION_ID, builder.build());
    }

//    public void send2StaticReceiver() {
//        Intent intent = new Intent();
//        intent.setAction("com.syl.toolbox.receivers.StaticPublishReceiver");
//        sendBroadcast(intent);
//    }
}
