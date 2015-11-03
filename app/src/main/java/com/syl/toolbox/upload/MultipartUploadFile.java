package com.syl.toolbox.upload;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Upload Multipart File
 * Multipart上传文件
 *
 * Created by syl on 15/10/22.
 */
public class MultipartUploadFile extends UploadFile implements Parcelable {

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

    protected MultipartUploadFile(Parcel in) {
        super(in);

        mName = in.readString();
        mFilename = in.readString();
        mContentType = in.readString();
    }

    public static final Creator<MultipartUploadFile> CREATOR = new Creator<MultipartUploadFile>() {
        @Override
        public MultipartUploadFile createFromParcel(Parcel in) {
            return new MultipartUploadFile(in);
        }

        @Override
        public MultipartUploadFile[] newArray(int size) {
            return new MultipartUploadFile[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeString(mName);
        dest.writeString(mFilename);
        dest.writeString(mContentType);
    }
}
