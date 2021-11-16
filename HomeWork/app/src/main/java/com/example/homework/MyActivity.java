package com.example.homework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyActivity extends AppCompatActivity {
    Button btn_back,btn_safe;
    ImageView img_address,img_home,img_my,img_head;
    EditText edt_name,edt_password,edt_tel,edt_stuNum;
    RadioButton boyrb,girlrb;
    User user;
    Bitmap bitmap;
    public void init(){
        btn_back=findViewById(R.id.btn_back1);
        img_address=findViewById(R.id.imageView9);
        img_home=findViewById(R.id.imageView10);
        img_my=findViewById(R.id.imageView11);
        img_head=findViewById(R.id.imageView18);
        edt_name=findViewById(R.id.edt_myname);
        edt_password=findViewById(R.id.edt_mypassword);
        edt_tel=findViewById(R.id.edt_mytel);
        edt_stuNum=findViewById(R.id.edt_mystudentnumber);
        boyrb=findViewById(R.id.radioButton3);
        girlrb=findViewById(R.id.radioButton4);
        btn_safe=findViewById(R.id.button8);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyActivity.this,ShowActivity.class));
            }
        });
        img_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyActivity.this,AddressActivity.class));
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        init();


        getMessage();
        //打开图库
        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                if(Build.VERSION.SDK_INT<19){
                    intent.setAction(Intent.ACTION_GET_CONTENT); }else{
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT); }
                startActivityForResult(intent,2);
            }
        });
        btn_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safe();
            }
        });
    }
    //保存个人信息
    public void safe(){
        SQLHelper helper=new SQLHelper(MyActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("password",edt_password.getText().toString());
        values.put("studentnumber",edt_stuNum.getText().toString());
        values.put("tel",edt_tel.getText().toString());
        values.put("name",edt_name.getText().toString());
        if(bitmap==null){
            bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.head);
        }
        //将图片添加到表中
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        values.put("user_image",baos.toByteArray());
        if(boyrb.isChecked()){
            values.put("sex",boyrb.getText().toString());
        }else{
            values.put("sex",girlrb.getText().toString());
        }
        long i=database.update("user",values,"name=?",new String[]{edt_name.getText().toString()});
        database.close();
        if(i>0){
            Toast.makeText(MyActivity.this, "safe OK!", Toast.LENGTH_SHORT).show();
        }

    }
    //从sharedPreference中拿取登录的用户的名字进行查询
    public void getMessage(){
        SharedPreferences sharedPreferences=getSharedPreferences("user_login",MODE_PRIVATE);
         String session_name=sharedPreferences.getString("name",null);
        SQLHelper helper=new SQLHelper(MyActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        Cursor cursor= database.rawQuery("select * from user where name=?",new String[]{session_name});
        if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String tel = cursor.getString(cursor.getColumnIndex("tel"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String studentnumber = cursor.getString(cursor.getColumnIndex("studentnumber"));
                byte[] imageByte = cursor.getBlob(cursor.getColumnIndex("user_image"));
                user = new User(name, password, studentnumber, tel, sex, imageByte);
        }
       Bitmap bm;
        //开启默认头像
       if(user.getImg()==null){
           bm=BitmapFactory.decodeResource(getResources(),R.drawable.head);
       }else {
           bm=BitmapFactory.decodeByteArray(user.getImg(), 0, user.getImg().length);
           bitmap=bm;
       }
       img_head.setImageBitmap(bm);
       edt_name.setText(user.getName());
       edt_stuNum.setText(user.getStudentnumber());
       edt_password.setText(user.getPassword());
       if(user.getTel()!=null){
           edt_tel.setText(user.getTel());
       }
       if(boyrb.getText().toString().equals(user.getSex())){
           boyrb.setChecked(true);
       }else {
           girlrb.setChecked(true);
       }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            Uri uri=data.getData();
            ContentResolver cr=this.getContentResolver();
            try {
                bitmap= BitmapFactory.decodeStream(cr.openInputStream(uri));
                img_head.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
