package com.example.homework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class News_addActivity extends AppCompatActivity {
    EditText edt_title,edt_content;
    ImageView imageView;
    Button button,btn_back;
    String author;
    Bitmap bitmap;
    public void init(){
        btn_back=findViewById(R.id.btn_back1);
        edt_content=findViewById(R.id.add_content);
        edt_title=findViewById(R.id.edt_addtitle);
        imageView=findViewById(R.id.img_addNewsImg);
        button=findViewById(R.id.button9);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("user_login",MODE_PRIVATE);
        author=sharedPreferences.getString("name",null);
        //设置打开图库
        imageView.setOnClickListener(new View.OnClickListener() {
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

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_add);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNews();
            }
        });
    }
    public void addNews(){
        String title=edt_title.getText().toString();
        String content=edt_content.getText().toString();
        SQLHelper helper=new SQLHelper(News_addActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("news_title", title);
        values.put("news_content",content);
        values.put("news_author", author);
        values.put("news_great", 0);
        //将图片添加到表中
        if(bitmap==null){
            bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.xmlglg);
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        values.put("news_image",baos.toByteArray());
        long i=database.insert("news",null,values);
        database.close();
        if(i>0){
            Toast.makeText(News_addActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            Uri uri=data.getData();
            ContentResolver cr=this.getContentResolver();
            try {
                bitmap= BitmapFactory.decodeStream(cr.openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
