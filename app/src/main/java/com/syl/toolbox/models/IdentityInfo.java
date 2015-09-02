package com.syl.toolbox.models;

/**
 * Created by shenyunlong on 2015/9/2.
 */
public class IdentityInfo {

    private String sex;  //M-男，F-女，N-未知
    private String birthday;
    private String address;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "IdentityInfo{" +
                "sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
