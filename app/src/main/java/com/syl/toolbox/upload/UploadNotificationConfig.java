package com.syl.toolbox.upload;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 自定义通知栏显示内容
 *
 * Created by shenyunlong on 11/2/15.
 */
public class UploadNotificationConfig implements Parcelable {

    private int mIcon;
    private String mContentTitle;
    private String mStartTip;
    private String mStopTip;
    private String mCompleteTip;
    private String mErrorTip;
    private Intent mClickIntent;

    public UploadNotificationConfig(int icon, String title, Intent intent) {
        this.mIcon = icon;
        this.mContentTitle = title;
        this.mStartTip = "Start";
        this.mStopTip = "Stop";
        this.mCompleteTip = "Complete";
        this.mErrorTip = "Error";
        this.mClickIntent = intent;
    }

    protected UploadNotificationConfig(Parcel in) {
        mIcon = in.readInt();
        mContentTitle = in.readString();
        mStartTip = in.readString();
        mStopTip = in.readString();
        mCompleteTip = in.readString();
        mErrorTip = in.readString();
        mClickIntent = in.readParcelable(Intent.class.getClassLoader());
    }

    public static final Creator<UploadNotificationConfig> CREATOR = new Creator<UploadNotificationConfig>() {
        @Override
        public UploadNotificationConfig createFromParcel(Parcel in) {
            return new UploadNotificationConfig(in);
        }

        @Override
        public UploadNotificationConfig[] newArray(int size) {
            return new UploadNotificationConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIcon);
        dest.writeString(mContentTitle);
        dest.writeString(mStartTip);
        dest.writeString(mStopTip);
        dest.writeString(mCompleteTip);
        dest.writeString(mErrorTip);
        dest.writeParcelable(mClickIntent, 0);
    }

    public Intent getClickIntent() {
        return mClickIntent;
    }

    public String getCompleteTip() {
        return mCompleteTip;
    }

    public void setCompleteTip(String mCompleteTip) {
        this.mCompleteTip = mCompleteTip;
    }

    public String getContentTitle() {
        return mContentTitle;
    }

    public String getErrorTip() {
        return mErrorTip;
    }

    public void setErrorTip(String mErrorTip) {
        this.mErrorTip = mErrorTip;
    }

    public int getIcon() {
        return mIcon;
    }

    public String getStartTip() {
        return mStartTip;
    }

    public void setStartTip(String mStartTip) {
        this.mStartTip = mStartTip;
    }

    public String getStopTip() {
        return mStopTip;
    }

    public void setStopTip(String mStopTip) {
        this.mStopTip = mStopTip;
    }
}
