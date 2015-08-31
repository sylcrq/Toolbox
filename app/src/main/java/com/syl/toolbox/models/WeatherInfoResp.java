package com.syl.toolbox.models;

/**
 * Created by shenyunlong on 2015/8/31.
 */
public class WeatherInfoResp {

    private int errNum;
    private String errMsg;
    private WeatherInfo retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public WeatherInfo getRetData() {
        return retData;
    }

    public void setRetData(WeatherInfo retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "WeatherInfoResp{" +
                "errNum=" + errNum +
                ", errMsg='" + errMsg + '\'' +
                ", retData=" + retData +
                '}';
    }
}
