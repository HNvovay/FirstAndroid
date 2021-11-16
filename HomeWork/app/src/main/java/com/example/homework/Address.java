package com.example.homework;

import java.io.Serializable;

public class Address implements Serializable {
    private int id;
    private String address_name;
    private String address_tel;
    private byte[] img;

    public Address(int id, String address_name, String address_tel, byte[] img) {
        this.id = id;
        this.address_name = address_name;
        this.address_tel = address_tel;
        this.img = img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getAddress_tel() {
        return address_tel;
    }

    public void setAddress_tel(String address_tel) {
        this.address_tel = address_tel;
    }
}
