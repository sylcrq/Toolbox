package com.syl.toolbox.upload;

import java.io.File;

/**
 * Upload File Abstract Base Class
 * 上传文件抽象基类
 *
 * Created by syl on 15/10/26.
 */
public abstract class UploadFile {

    public static final String NEW_LINE = "\r\n";

    protected File mFile;

    public UploadFile(String path) {
        this.mFile = new File(path);
    }

    public File getUploadFile() {
        return mFile;
    }
}
