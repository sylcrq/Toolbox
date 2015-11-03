package com.syl.toolbox.upload;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.File;

/**
 * Upload File Base Class
 * 上传文件基类
 *
 * Created by syl on 15/10/26.
 */
public class UploadFile implements Parcelable {

    public static final String NEW_LINE = "\r\n";

//    private String mPath;
    private File mFile;

    public UploadFile(String path) {
        this.mFile = new File(path);
    }

    protected UploadFile(Parcel in) {
        mFile = new File(in.readString());
    }

    public static final Creator<UploadFile> CREATOR = new Creator<UploadFile>() {
        @Override
        public UploadFile createFromParcel(Parcel in) {
            return new UploadFile(in);
        }

        @Override
        public UploadFile[] newArray(int size) {
            return new UploadFile[size];
        }
    };

    public File getUploadFile() {
        return mFile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFile.getAbsolutePath());
    }
}
