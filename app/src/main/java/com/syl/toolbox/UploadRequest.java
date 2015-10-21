package com.syl.toolbox;

import java.util.ArrayList;
import java.util.List;

/**
 * Upload Request
 *
 * Created by syl on 15/10/21.
 */
public class UploadRequest {

    public static final String TAG = UploadRequest.class.getSimpleName();

    private String mUrl;
    private List<UploadFile> mUploadFiles;

    public UploadRequest(String url, List<UploadFile> files) {
        this.mUrl = url;
        this.mUploadFiles = files;
    }

    public String getUploadUrl() {
        return mUrl;
    }

    public List<UploadFile> getUploadFiles() {
        return mUploadFiles;
    }
}
