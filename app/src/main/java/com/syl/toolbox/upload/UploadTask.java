package com.syl.toolbox.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * Upload Task Abstract Base Class
 * 上传任务抽象基类
 *
 * Created by syl on 15/10/26.
 */
public abstract class UploadTask {

    protected UploadRequest uploadRequest;
    protected HttpURLConnection connection;
    protected OutputStream outputStream;
    protected InputStream inputStream;

    public UploadTask(UploadRequest request) {
        this.uploadRequest = request;
    }

    public abstract void upload();
    public abstract HttpURLConnection getHTTPConnection() throws IOException;
    public abstract void writeBody();
    public abstract void readResponse();

}
