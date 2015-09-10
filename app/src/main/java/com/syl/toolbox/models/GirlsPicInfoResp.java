package com.syl.toolbox.models;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by shenyunlong on 2015/9/10.
 */
public class GirlsPicInfoResp {

    private int code;
    private String msg;
    // TODO: 云端接口设计不合理，是否有更好的处理方式？
    @JSONField(name="0")
    private GirlsPicInfo pic0;
    @JSONField(name="1")
    private GirlsPicInfo pic1;
    @JSONField(name="2")
    private GirlsPicInfo pic2;
    @JSONField(name="3")
    private GirlsPicInfo pic3;
    @JSONField(name="4")
    private GirlsPicInfo pic4;
    @JSONField(name="5")
    private GirlsPicInfo pic5;
    @JSONField(name="6")
    private GirlsPicInfo pic6;
    @JSONField(name="7")
    private GirlsPicInfo pic7;
    @JSONField(name="8")
    private GirlsPicInfo pic8;
    @JSONField(name="9")
    private GirlsPicInfo pic9;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GirlsPicInfo getPic0() {
        return pic0;
    }

    public void setPic0(GirlsPicInfo pic0) {
        this.pic0 = pic0;
    }

    public GirlsPicInfo getPic1() {
        return pic1;
    }

    public void setPic1(GirlsPicInfo pic1) {
        this.pic1 = pic1;
    }

    public GirlsPicInfo getPic2() {
        return pic2;
    }

    public void setPic2(GirlsPicInfo pic2) {
        this.pic2 = pic2;
    }

    public GirlsPicInfo getPic3() {
        return pic3;
    }

    public void setPic3(GirlsPicInfo pic3) {
        this.pic3 = pic3;
    }

    public GirlsPicInfo getPic4() {
        return pic4;
    }

    public void setPic4(GirlsPicInfo pic4) {
        this.pic4 = pic4;
    }

    public GirlsPicInfo getPic5() {
        return pic5;
    }

    public void setPic5(GirlsPicInfo pic5) {
        this.pic5 = pic5;
    }

    public GirlsPicInfo getPic6() {
        return pic6;
    }

    public void setPic6(GirlsPicInfo pic6) {
        this.pic6 = pic6;
    }

    public GirlsPicInfo getPic7() {
        return pic7;
    }

    public void setPic7(GirlsPicInfo pic7) {
        this.pic7 = pic7;
    }

    public GirlsPicInfo getPic8() {
        return pic8;
    }

    public void setPic8(GirlsPicInfo pic8) {
        this.pic8 = pic8;
    }

    public GirlsPicInfo getPic9() {
        return pic9;
    }

    public void setPic9(GirlsPicInfo pic9) {
        this.pic9 = pic9;
    }

    @Override
    public String toString() {
        return "GirlsPicInfoResp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", pic0=" + pic0 +
                ", pic1=" + pic1 +
                ", pic2=" + pic2 +
                ", pic3=" + pic3 +
                ", pic4=" + pic4 +
                ", pic5=" + pic5 +
                ", pic6=" + pic6 +
                ", pic7=" + pic7 +
                ", pic8=" + pic8 +
                ", pic9=" + pic9 +
                '}';
    }
}
