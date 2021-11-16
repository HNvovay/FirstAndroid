package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {
    Button btn_back,btn_test,btn_add;
    ImageView img_address,img_home,img_my,img_check;
    EditText editText;
    RecyclerView recyclerView_address;
    AddressAdapter addressAdapter;
    List<Address> addresses;
    //初始化
    public void init(){
        btn_back=findViewById(R.id.btn_back2);
        btn_add=findViewById(R.id.btn_add);
        img_address=findViewById(R.id.imageView6);
        img_home=findViewById(R.id.imageView7);
        img_my=findViewById(R.id.imageView8);
        img_check=findViewById(R.id.imageView15);
        editText=findViewById(R.id.editText);
        recyclerView_address=findViewById(R.id.recycleView_address);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this,ShowActivity.class));
            }
        });
        img_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this,MyActivity.class));
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
        setContentView(R.layout.activity_address);
        init();
        //加载列表
        startAddress(null);

        //查询按钮
        img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> list=checkAddress();
                startAddress(list);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddressActivity.this,Address_addActivity.class);
                startActivity(intent);
            }
        });

    }
    //查询的方法，返回List类型提供给RecyclerView
    public List<Address> checkAddress(){
        String checkContent=editText.getText().toString();
        SQLHelper helper=new SQLHelper(AddressActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        Cursor cursor=database.rawQuery("select * from address where address_name like ?",new String[]{"%"+checkContent+"%"});
        List<Address> addresses_check=new ArrayList<Address>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String address_name = cursor.getString(cursor.getColumnIndex("address_name"));
                String address_tel = cursor.getString(cursor.getColumnIndex("address_tel"));
                byte[] imageByte=cursor.getBlob(cursor.getColumnIndex("image"));
                Address address=new Address(id,address_name,address_tel,imageByte);
                addresses_check.add(address);
            } while (cursor.moveToNext());
        }
        return addresses_check;
    }
    //开始加载初始列表方法
    public void startAddress(List<Address> list){
        SQLHelper helper=new SQLHelper(AddressActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        List<Address> address_list=new ArrayList<Address>();
        /*if(test==1){
            //插入数据测试
            ContentValues values = new ContentValues();
            values.put("address_name", "蓝志成");
            values.put("address_tel", "13163706938");
            database.insert("address", null, values);
            //
        }*/
        Cursor cursor = database.query("address", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String address_name = cursor.getString(cursor.getColumnIndex("address_name"));
                String address_tel = cursor.getString(cursor.getColumnIndex("address_tel"));
                byte[] imageByte=cursor.getBlob(cursor.getColumnIndex("image"));
                Address address=new Address(id,address_name,address_tel,imageByte);
                address_list.add(address);
            } while (cursor.moveToNext());
        }
        //关闭流
        cursor.close();
        database.close();
        //传入要查询的值
        if(list!=null) {
            address_list=list;
        }
        addresses=address_list;
            addressAdapter = new AddressAdapter(address_list);
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView_address.setLayoutManager(manager);
            recyclerView_address.addItemDecoration(new DividerItemDecoration(AddressActivity.this, DividerItemDecoration.VERTICAL));
            recyclerView_address.setAdapter(addressAdapter);
    }



}
