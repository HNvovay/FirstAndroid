package com.example.homework;

import java.io.Serializable;

public class News implements Serializable {
    private int id;
    private String news_title;
    private String news_content;
    private String news_author;
    private int news_great;
    private byte[] img;

    public News(int id, String news_title, String news_content, String news_author, int news_great, byte[] img) {
        this.id = id;
        this.news_title = news_title;
        this.news_content = news_content;
        this.news_author = news_author;
        this.news_great = news_great;
        this.img = img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getNews_great() {
        return news_great;
    }

    public void setNews_great(int news_great) {
        this.news_great = news_great;
    }

    public News() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getNews_author() {
        return news_author;
    }

    public void setNews_author(String news_author) {
        this.news_author = news_author;
    }
}
