package com.example.homework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
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

public class Address_addActivity extends AppCompatActivity {
    Button btn_back,btn_add;
    EditText address_name,address_tel;
    ImageView imageView;
    Bitmap bitmap;
    //初始化
    public void init(){
        btn_back=findViewById(R.id.btn_back1);
        btn_add=findViewById(R.id.button7);
        imageView=findViewById(R.id.imageView17);
        address_name=findViewById(R.id.editText2);
        address_tel=findViewById(R.id.editText3);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //从图库获取图片
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
        setContentView(R.layout.activity_address_add);
        init();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddress();
            }
        });
    }
    public void addAddress(){
        String name=address_name.getText().toString();
        String tel=address_tel.getText().toString();
        SQLHelper helper=new SQLHelper(Address_addActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("address_name",name);
        values.put("address_tel",tel);
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
            values.put("image",baos.toByteArray());
            //结束
        long i=database.insert("address",null,values);
        database.close();
        if(i>0){
            Toast.makeText(Address_addActivity.this, "insert OK!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Address_addActivity.this,AddressActivity.class);
            startActivity(intent);
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
