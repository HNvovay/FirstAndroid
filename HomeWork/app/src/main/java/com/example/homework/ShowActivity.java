package com.example.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//首页
public class ShowActivity extends AppCompatActivity {
    RecyclerView recyclerView_news;
    Button btn_back,btn_news,btn_data,btn_test;
    ImageView img_address,img_home,img_my,img_show;
    int img[]={R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4};
    int count=0;
    MyThread myThread;
    NewsAdapter newsadapter;
    ListView listView;
    //初始化
    public void init(){
        btn_back=findViewById(R.id.button);
        img_address=findViewById(R.id.imageView3);
        img_home=findViewById(R.id.imageView4);
        img_my=findViewById(R.id.imageView5);
        img_show=findViewById(R.id.imageView12);
        btn_news=findViewById(R.id.button2);
        btn_data=findViewById(R.id.button3);
        btn_test=findViewById(R.id.button6);
        recyclerView_news=findViewById(R.id.news);
        listView=findViewById(R.id.study_data);
        img_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowActivity.this,AddressActivity.class));
            }
        });
        img_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowActivity.this,MyActivity.class));
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
        setContentView(R.layout.activity_show);
        init();
        //开始轮播
        startImage();
        btn_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNews(2);
                btn_data.setEnabled(true);
                btn_news.setEnabled(false);
            }
        });
        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_data.setEnabled(false);
                btn_news.setEnabled(true);
                startStudyDate();
            }
        });
        //添加数据，无任何功能
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNews(1);
            }
        });

    }
    //开始初始化学习资源列表
    public void startStudyDate(){
        //设置新闻列表不可见
        recyclerView_news.setVisibility(View.INVISIBLE);
        //设置学历资源列表课件
        listView.setVisibility(View.VISIBLE);
        List list=new ArrayList();
        for(int i=0;i<3;i++){
            HashMap map1=new HashMap();
            map1.put("img",R.drawable.video);
            map1.put("text","学习资源"+(i+1));
            list.add(map1);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,list,R.layout.layout_sd,new String[]{"img","text"},new int[]{R.id.pic,R.id.text});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ShowActivity.this,StudyDataActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
    //开始初始化RecycleView成为新闻列表
    public void startNews(int test){
        //设置新闻列表可见
        recyclerView_news.setVisibility(View.VISIBLE);
        //设置学习资源列表不可见
        listView.setVisibility(View.INVISIBLE);
        SQLHelper helper=new SQLHelper(ShowActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        final List<News> news_list=new ArrayList<News>();
        //插入数据测试
        if(test==1) {
            ContentValues values = new ContentValues();
            values.put("news_title", "这是一个测试标题");
            values.put("news_content", "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试");
            values.put("news_author", "HYH");
            values.put("news_great", 22);
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.xmlglg);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            values.put("news_image",baos.toByteArray());
            database.insert("news", null, values);
        }
        //查询
        Cursor cursor = database.query("news", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String news_title = cursor.getString(cursor.getColumnIndex("news_title"));
                String news_content = cursor.getString(cursor.getColumnIndex("news_content"));
                String news_author = cursor.getString(cursor.getColumnIndex("news_author"));
                int news_great=cursor.getInt(cursor.getColumnIndex("news_great"));
                byte[] imageByte=cursor.getBlob(cursor.getColumnIndex("news_image"));
                News show=new News(id,news_title,news_content,news_author,news_great,imageByte);
                news_list.add(show);
            } while (cursor.moveToNext());
        }
        //关闭流
        cursor.close();
        database.close();
        newsadapter=new NewsAdapter(news_list);
        //设置RecycleView的监听事件
        newsadapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(ShowActivity.this,NewsActivity.class);
                News news_item=news_list.get(position);
                intent.putExtra("news_item",news_item);
                startActivity(intent);
            }
        });
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView_news.setLayoutManager(manager);
        recyclerView_news.addItemDecoration(new DividerItemDecoration(ShowActivity.this,DividerItemDecoration.VERTICAL));
        recyclerView_news.setAdapter(newsadapter);
    }
    //轮播
    public void startImage(){
        myThread=new MyThread();
        myThread.start();
    }
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (true){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                if(count==img.length){
                    count=0;
                }
                //textView.setText(""+count);
                Message msg=new Message();
                msg.arg1=1;
                myHandler.sendMessage(msg);
            }

        }
    }
    Handler myHandler=new Handler(){
        //处理消息

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.arg1==1){
                img_show.setImageResource(img[count]);
            }
        }
    };
}
