package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText edt_name,edt_password;
    Button btn_login,btn_register;
    ImageView imageView;
    //初始化
    public void init(){
        edt_name=findViewById(R.id.edt_name);
        edt_password=findViewById(R.id.edt_password);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        String name=getIntent().getStringExtra("name");
        edt_name.setText(name);
        imageView=findViewById(R.id.imageView);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //登录按钮
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLHelper helper=new SQLHelper(LoginActivity.this);
                SQLiteDatabase database=helper.getWritableDatabase();
                String sql="select * from user where name=? and password=?";
                Cursor cursor = database.rawQuery(sql, new String[]{edt_name.getText().toString(),edt_password.getText().toString()});
                if(cursor.moveToFirst()){
                    cursor.close();
                    database.close();
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,ShowActivity.class);
                    //保存已登录用户数据
                    SharedPreferences.Editor editor=getSharedPreferences("user_login",MODE_PRIVATE).edit();
                    editor.putString("name",edt_name.getText().toString());
                    if(editor.commit()){startActivity(intent);}
                }else{
                    Toast.makeText(LoginActivity.this, "Login defeat", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //注册按钮跳转
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Register", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
