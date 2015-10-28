package com.syl.toolbox.upload;

import java.util.HashMap;
import java.util.Map;

/**
 * Upload Request Abstract Base Class
 * 上传请求抽象基类
 *
 * Created by syl on 15/10/26.
 */
public abstract class UploadRequest {

    // 唯一标识id
    protected long mId;
    protected String mUrl;
    protected String mMethod;
    protected Map<String, String> mHeaders;

    public UploadRequest(String url, String method) {
        this.mId = System.currentTimeMillis();
        this.mUrl = url;
        this.mMethod = method;
        this.mHeaders = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public String getRequestUrl() {
        return mUrl;
    }

    public Map<String, String> getRequestHeaders() {
        return mHeaders;
    }
}
