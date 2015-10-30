package com.syl.toolbox.upload;


import com.syl.toolbox.services.UploadService;

/**
 * Upload Task Abstract Base Class
 * 上传任务抽象基类
 *
 * Created by syl on 15/10/26.
 */
public abstract class UploadTask {

    protected UploadRequest mUploadRequest;
    protected UploadService mUploadService;

    public UploadTask(UploadRequest request, UploadService service) {
        this.mUploadRequest = request;
        this.mUploadService = service;
    }

    public abstract void upload();

}
