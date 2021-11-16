package com.example.homework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {


    public SQLHelper(@Nullable Context context) {
        super(context, "School.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table user(name varchar(11) primary key,password varchar(11),studentnumber varchar(11),sex varchar(11),tel varchar(11),user_image BLOB)";
        String sql2="create table address(id integer primary key autoincrement,address_name varchar(11),address_tel varchar(11),image BLOB)";
        String sql1="create table news(id integer primary key autoincrement,news_title varchar(11),news_content varchar(100),news_author varchar(11),news_great integer,news_image BLOB)";
        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
