package com.syl.toolbox.upload;


import android.content.Context;
import android.content.Intent;
import com.syl.toolbox.services.UploadService;

/**
 * Multipart Upload Request
 * Multipart上传请求
 *
 * Created by syl on 15/10/21.
 */
public class MultipartUploadRequest extends UploadRequest {

    public static final String TAG = MultipartUploadRequest.class.getSimpleName();

    public MultipartUploadRequest(String url, String method) {
        super(url, method);
    }

    @Override
    public void startUpload(Context context) {
        Intent intent = new Intent(context, UploadService.class);

        intent.setAction(UploadService.ACTION_UPLOAD);

        intent.putExtra(UploadService.UPLOAD_REQUEST_TYPE, UploadService.UPLOAD_REQUEST_MULTIPART);
        intent.putExtra(UploadService.UPLOAD_REQUEST_ID, getUploadId());
        intent.putExtra(UploadService.UPLOAD_REQUEST_URL, getRequestUrl());
        intent.putExtra(UploadService.UPLOAD_REQUEST_METHOD, getRequestMethod());
        intent.putParcelableArrayListExtra(UploadService.UPLOAD_REQUEST_HEADERS, getRequestHeaders());
        intent.putParcelableArrayListExtra(UploadService.UPLOAD_REQUEST_FILES, getRequestFiles());
        intent.putExtra(UploadService.UPLOAD_REQUEST_NOTIFICATION_CONFIG, getNotificationConfig());

        context.startService(intent);
    }

}
