package com.example.homework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MenuView extends LinearLayout {
    Button btn,btn_back;
    public MenuView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.layout_titlebar,this,true);
        btn=view.findViewById(R.id.btn_test);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),v);
                ((Activity)getContext()) .getMenuInflater().inflate(R.menu.menu1,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_1:
                                context.startActivity(new Intent(getContext(),News_addActivity.class));
                                break;
                            case R.id.item_2:
                                context.startActivity(new Intent(getContext(),Address_addActivity.class));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
