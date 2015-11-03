package com.syl.toolbox.upload;

import android.content.Context;
import com.syl.toolbox.HTTPRequestHeader;
import com.syl.toolbox.NotificationConfig;
import java.util.ArrayList;

/**
 * Upload Request Abstract Base Class
 * 上传请求抽象基类
 *
 * Created by syl on 15/10/26.
 */
public abstract class UploadRequest {

    // 唯一标识id
    private long mUploadId;
    private String mUrl;
    private String mMethod;
    private ArrayList<HTTPRequestHeader> mHeaders;
    private ArrayList<UploadFile> mFiles;
    private NotificationConfig mNotificationConfig;

    public UploadRequest(String url, String method) {
        // TODO: unique Id
        this.mUploadId = System.currentTimeMillis();
        this.mUrl = url;
        this.mMethod = method;
        this.mHeaders = new ArrayList<>();
        this.mFiles = new ArrayList<>();
    }

    public void addRequestHeader(String key, String value) {
        mHeaders.add(new HTTPRequestHeader(key,value));
    }

    public void addRequestFile(UploadFile file) {
        mFiles.add(file);
    }

    public void setNotificationConfig(NotificationConfig config) {
        this.mNotificationConfig = config;
    }

    public long getUploadId() {
        return mUploadId;
    }

    public String getRequestUrl() {
        return mUrl;
    }

    public String getRequestMethod() {
        return mMethod;
    }

    public ArrayList<HTTPRequestHeader> getRequestHeaders() {
        return mHeaders;
    }

    public ArrayList<UploadFile> getRequestFiles() {
        return mFiles;
    }

    public NotificationConfig getNotificationConfig() {
        return mNotificationConfig;
    }

    public abstract void startUpload(Context context);
}
