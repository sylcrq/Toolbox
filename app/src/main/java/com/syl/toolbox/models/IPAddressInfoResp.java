package com.syl.toolbox.models;

/**
 * Created by shenyunlong on 2015/9/2.
 */
public class IPAddressInfoResp {

    private int errNum;
    private String errMsg;
    private IPAddressInfo retData;

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

    public IPAddressInfo getRetData() {
        return retData;
    }

    public void setRetData(IPAddressInfo retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "IPAddressInfoResp{" +
                "errNum=" + errNum +
                ", errMsg='" + errMsg + '\'' +
                ", retData=" + retData +
                '}';
    }
}
