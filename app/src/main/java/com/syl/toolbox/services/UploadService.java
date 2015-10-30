package com.syl.toolbox.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.syl.toolbox.upload.MultipartUploadFile;
import com.syl.toolbox.upload.MultipartUploadRequest;
import com.syl.toolbox.upload.MultipartUploadTask;

import java.util.ArrayList;
import java.util.List;

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

        final String path = "/sdcard/file1024.txt";
        final String name = "file1024";
        final String filename = "file1024.txt";
        final String contentType = "text/plain";
        MultipartUploadFile file = new MultipartUploadFile(path, name, filename, contentType);

        String url = "http://10.1.36.99:3000/upload/multipart";
//        String url = "http://posttestserver.com/post.php";
        MultipartUploadRequest request = new MultipartUploadRequest(url, MultipartUploadTask.METHOD_POST);
        request.addRequestFile(file);

        MultipartUploadTask task = new MultipartUploadTask(request);
        task.upload();
    }
}
