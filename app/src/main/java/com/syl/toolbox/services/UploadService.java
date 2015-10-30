package com.syl.toolbox.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.syl.toolbox.receivers.UploadServiceReceiver;
import com.syl.toolbox.upload.MultipartUploadFile;
import com.syl.toolbox.upload.MultipartUploadRequest;
import com.syl.toolbox.upload.MultipartUploadTask;


/**
 * Upload Service
 *
 */
public class UploadService extends IntentService {

    public static final String TAG = UploadService.class.getSimpleName();

    public static final String ACTION_UPLOAD = "com.syl.toolbox.services.action.UPLOAD";

    public UploadService() {
        super("MyIntentService");
    }

    /**
     * start upload
     *
     * @param context
     */
    public static void startActionUpload(Context context) {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(ACTION_UPLOAD);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if(ACTION_UPLOAD.equals(action)) {
                handleActionUpload();
            }
        }
    }

    /**
     * handle upload
     *
     */
    private void handleActionUpload() {
        Log.d(TAG, "Thread "+Thread.currentThread().getId()+" # handleActionUpload");

        final String path = "/sdcard/DCIM/IQIYI/IQIYI_2015_1027_140229.mp4";
        final String name = "IQIYI_2015_1027_140229";
        final String filename = "IQIYI_2015_1027_140229.mp4";
        final String contentType = "video/mpeg4";
        MultipartUploadFile file = new MultipartUploadFile(path, name, filename, contentType);

        String url = "http://10.1.36.99:3000/upload/multipart";
//        String url = "http://posttestserver.com/post.php";
        MultipartUploadRequest request = new MultipartUploadRequest(url, MultipartUploadTask.METHOD_POST);
        request.addRequestFile(file);

        MultipartUploadTask task = new MultipartUploadTask(request, this);
        task.upload();
    }

    public void sendUploadStart() {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_START);
        sendBroadcast(intent);
    }

    public void sendUploadStop() {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_STOP);
        sendBroadcast(intent);
    }

    public void sendUploadComplete() {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_COMPLETE);
        sendBroadcast(intent);
    }

    public void sendUploadError() {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_ERROR);
        sendBroadcast(intent);
    }

    public void sendUploadProgress(int percent) {
        Intent intent = new Intent();
        intent.setAction(UploadServiceReceiver.UPLOAD_BROADCAST_ACTION);
        intent.putExtra(UploadServiceReceiver.UPLOAD_STATUS, UploadServiceReceiver.UPLOAD_STATUS_PROGRESS);
        intent.putExtra(UploadServiceReceiver.UPLOAD_PROGRESS, percent);
        sendBroadcast(intent);
    }

//    public void send2StaticReceiver() {
//        Intent intent = new Intent();
//        intent.setAction("com.syl.toolbox.receivers.StaticPublishReceiver");
//        sendBroadcast(intent);
//    }
}
