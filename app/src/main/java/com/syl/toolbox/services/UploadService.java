package com.syl.toolbox.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.syl.toolbox.UploadFile;
import com.syl.toolbox.UploadRequest;
import com.syl.toolbox.UploadTask;

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

        String url = "http://posttestserver.com/post.php";
        List<UploadFile> files = new ArrayList<>();
        files.add(new UploadFile("file1024", "file1024.txt", "/sdcard/file1024.txt"));

        UploadTask task = new UploadTask(new UploadRequest(url, files));
        task.upload();
    }
}
