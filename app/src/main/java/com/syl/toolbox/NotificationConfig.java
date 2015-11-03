package com.syl.toolbox;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 自定义通知栏显示内容
 *
 * Created by shenyunlong on 11/2/15.
 */
public class NotificationConfig implements Parcelable {

    // TODO:
    private int mNum;

    public NotificationConfig(int num) {
        this.mNum = num;
    }

    protected NotificationConfig(Parcel in) {
        mNum = in.readInt();
    }

    public static final Creator<NotificationConfig> CREATOR = new Creator<NotificationConfig>() {
        @Override
        public NotificationConfig createFromParcel(Parcel in) {
            return new NotificationConfig(in);
        }

        @Override
        public NotificationConfig[] newArray(int size) {
            return new NotificationConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mNum);
    }
}
