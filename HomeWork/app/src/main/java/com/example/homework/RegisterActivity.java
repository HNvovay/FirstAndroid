package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button btn_register1;
    EditText edt_rgname,edt_rgpassword,edt_rgstuNum;
    RadioButton boyrb,girlrb;
    public void init(){
        btn_register1=findViewById(R.id.btn_register1);
        edt_rgname=findViewById(R.id.edt_rgname);
        edt_rgpassword=findViewById(R.id.edt_rgpassword);
        edt_rgstuNum=findViewById(R.id.edt_rgstuNum);
        boyrb=findViewById(R.id.radioButton2);
        girlrb=findViewById(R.id.radioButton);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        btn_register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLHelper helper=new SQLHelper(RegisterActivity.this);
                SQLiteDatabase database=helper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name",edt_rgname.getText().toString());

                values.put("password",edt_rgpassword.getText().toString());

                values.put("studentnumber",edt_rgstuNum.getText().toString());

                if(boyrb.isChecked()){
                    values.put("sex",boyrb.getText().toString());

                }else{
                    values.put("sex",girlrb.getText().toString());

                }
                long i=database.insert("user",null,values);
                database.close();
                if(i>0){
                    Toast.makeText(RegisterActivity.this, "Register OK!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.putExtra("name",edt_rgname.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
