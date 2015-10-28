package com.syl.toolbox.upload;

import java.util.ArrayList;
import java.util.List;

/**
 * Multipart Upload Request
 * Multipart上传请求
 *
 * Created by syl on 15/10/21.
 */
public class MultipartUploadRequest extends UploadRequest {

    public static final String TAG = MultipartUploadRequest.class.getSimpleName();

    private List<MultipartUploadFile> mFiles;

    public MultipartUploadRequest(String url, String method) {
        super(url, method);
        mFiles = new ArrayList<>();
    }

    public void addFile(MultipartUploadFile file) {
        mFiles.add(file);
    }

    public List<MultipartUploadFile> getUploadFiles() {
        return mFiles;
    }
}
