package com.syl.toolbox.upload;


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

}
