package com.syl.toolbox.models;

/**
 * Created by shenyunlong on 2015/9/16.
 */
public class PersonInfo {

    private String name;
    private int age;
    private String avatar;

    public PersonInfo(String name, int age, String avatar) {
        this.name = name;
        this.age = age;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
