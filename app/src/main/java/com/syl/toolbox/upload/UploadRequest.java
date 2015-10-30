package com.syl.toolbox.upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Upload Request Abstract Base Class
 * 上传请求抽象基类
 *
 * Created by syl on 15/10/26.
 */
public abstract class UploadRequest {

    // 唯一标识id
    protected long mUploadId;
    protected String mUrl;
    protected String mMethod;
    protected Map<String, String> mHeaders;
    protected List<UploadFile> mFiles;

    public UploadRequest(String url, String method) {
        // TODO: unique Id
        this.mUploadId = System.currentTimeMillis();
        this.mUrl = url;
        this.mMethod = method;
        this.mHeaders = new HashMap<>();
        this.mFiles = new ArrayList<>();
    }

    public void addRequestHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public void addRequestFile(UploadFile file) {
        mFiles.add(file);
    }

    public long getUploadId() {
        return mUploadId;
    }

    public String getRequestUrl() {
        return mUrl;
    }

    public Map<String, String> getRequestHeaders() {
        return mHeaders;
    }

    public List<UploadFile> getRequestFiles() {
        return mFiles;
    }
}
