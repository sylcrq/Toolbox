package com.syl.toolbox.models;

/**
 * Created by shenyunlong on 2015/9/2.
 */
public class IdentityInfoResp {

    private int errNum;
    private String retMsg;
    private IdentityInfo retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public IdentityInfo getRetData() {
        return retData;
    }

    public void setRetData(IdentityInfo retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "IdentityInfoResp{" +
                "errNum=" + errNum +
                ", retMsg='" + retMsg + '\'' +
                ", retData=" + retData +
                '}';
    }
}
