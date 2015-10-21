package com.syl.toolbox;

import java.io.File;

/**
 * Upload File
 *
 * Created by syl on 15/10/22.
 */
public class UploadFile {

    public static final String TAG = UploadFile.class.getSimpleName();

    private String mName;
    private String mFilename;
    private String mFilePath;

    public UploadFile(String name, String filename, String path) {
        this.mName = name;
        this.mFilename = filename;
        this.mFilePath = path;
    }

    public String getRequestHeaders() {
        StringBuilder builder = new StringBuilder();
        builder.append("Content-Disposition: form-data;").
                append(" name=\"").append(mName).append("\";").
                append(" filename=\"").append(mFilename).append("\"").
                append(UploadTask.NEW_LINE);
        builder.append("Content-Type: text/plain").
                append(UploadTask.NEW_LINE).
                append(UploadTask.NEW_LINE);

        return builder.toString();
    }

    public File getRequestFile() {
        File file = new File(mFilePath);

        return file;
    }
}
