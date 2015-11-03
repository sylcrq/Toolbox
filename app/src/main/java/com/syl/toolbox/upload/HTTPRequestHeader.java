package com.syl.toolbox.upload;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * HTTP请求头（HTTP Request Header）
 *
 * Created by shenyunlong on 11/2/15.
 */
public class HTTPRequestHeader implements Parcelable {

    private String key;
    private String value;

    public HTTPRequestHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    protected HTTPRequestHeader(Parcel in) {
        key = in.readString();
        value = in.readString();
    }

    public static final Creator<HTTPRequestHeader> CREATOR = new Creator<HTTPRequestHeader>() {
        @Override
        public HTTPRequestHeader createFromParcel(Parcel in) {
            return new HTTPRequestHeader(in);
        }

        @Override
        public HTTPRequestHeader[] newArray(int size) {
            return new HTTPRequestHeader[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
