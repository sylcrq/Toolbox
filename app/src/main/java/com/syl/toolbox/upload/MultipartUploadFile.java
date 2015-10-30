package com.syl.toolbox.upload;

/**
 * Upload Multipart File
 * Multipart上传文件
 *
 * Created by syl on 15/10/22.
 */
public class MultipartUploadFile extends UploadFile {

    public static final String TAG = MultipartUploadFile.class.getSimpleName();

    private String mName;
    private String mFilename;
    private String mContentType;

    public MultipartUploadFile(String path, String name, String filename, String contentType) {
        super(path);

        this.mName = name;
        this.mFilename = filename;
        this.mContentType = contentType;
    }

    public String getMultipartUploadHeader() {
        final StringBuilder builder = new StringBuilder();

        builder.append("Content-Disposition: form-data;")
                .append(" name=\"").append(mName).append("\";")
                .append(" filename=\"").append(mFilename).append("\"")
                .append(NEW_LINE)
                .append("Content-Type: ").append(mContentType)
                .append(NEW_LINE)
                .append(NEW_LINE);

        return builder.toString();
    }
}
