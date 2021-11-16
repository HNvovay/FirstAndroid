package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsActivity extends AppCompatActivity {
    Button btn_back,btn_like;
    News news;
    TextView textView_title,textView_content, textView_like,textView_author;
    ImageView imageView;
    public void init(){
        btn_back=findViewById(R.id.btn_back1);
        textView_title=findViewById(R.id.textView15);
        textView_content=findViewById(R.id.textView16);
        btn_like=findViewById(R.id.button4);
        textView_author=findViewById(R.id.textView28);
        imageView=findViewById(R.id.imageView20);
        //初始化数值
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        news= (News) getIntent().getSerializableExtra("news_item");
        textView_title.setText(news.getNews_title());
        textView_content.setText("       "+news.getNews_content());
        textView_author.setText("  作者：     "+news.getNews_author());
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeByteArray(news.getImg(), 0, news.getImg().length);
        imageView.setImageBitmap(bitmap);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLike();
            }
        });
    }
    public void getLike(){
        int count=news.getNews_great();
        SQLHelper helper=new SQLHelper(NewsActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        count=count+1;
        values.put("news_great",count);
        int i=database.update("news",values,"id=?",new String[]{String.valueOf(news.getId())});
        database.close();
        if(i>0){
            Toast.makeText(NewsActivity.this, "like success", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(NewsActivity.this, "defeat", Toast.LENGTH_SHORT).show();
        }

    }

}
