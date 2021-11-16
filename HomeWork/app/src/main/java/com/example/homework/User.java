package com.example.homework;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;
    private String studentnumber;
    private String tel;
    private String sex;
    private byte[] img;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(String studentnumber) {
        this.studentnumber = studentnumber;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public User(String name, String password, String studentnumber, String tel, String sex, byte[] img) {
        this.name = name;
        this.password = password;
        this.studentnumber = studentnumber;
        this.tel = tel;
        this.sex = sex;
        this.img = img;
    }
}
