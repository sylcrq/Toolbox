package com.syl.toolbox.upload;


/**
 * Upload Task Abstract Base Class
 * 上传任务抽象基类
 *
 * Created by syl on 15/10/26.
 */
public abstract class UploadTask {

    protected UploadRequest mUploadRequest;

    public UploadTask(UploadRequest request) {
        this.mUploadRequest = request;
    }

    public abstract void upload();

}
