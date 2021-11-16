package com.example.homework;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressViewHolder> {
    List<Address> list;

    public AddressAdapter(List<Address> list) {
        this.list = list;
    }
    //自定义监听器；
    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }
    private AddressAdapter.OnItemClickListener listener;
    //第二步， 写一个公共的方法
    public void setOnItemClickListener(AddressAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemLongClickListener {
        void onClick(int position);
    }
    private AddressAdapter.OnItemLongClickListener longClickListener;
    public void setOnItemLongClickListener(AddressAdapter.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
    //结束定义
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adviewholder,null);
        final View view1= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_address,null);
        final AddressViewHolder holder=new AddressViewHolder(view);

        //设置删除按钮
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view1.getContext());
                builder.setMessage("确认删除");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLHelper helper = new SQLHelper(view1.getContext());
                        SQLiteDatabase db = helper.getReadableDatabase();
                        int id=list.get(holder.getAdapterPosition()).getId();
                        int i = db.delete("address", "id=?", new String[]{String.valueOf(id)});
                        db.close();
                        if(i>0){
                            list.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            Toast.makeText(view1.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(view1.getContext(), "取消删除", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, final int position) {
        Address address=list.get(position);
        holder.address_name.setText(address.getAddress_name());
        holder.address_tel.setText(address.getAddress_tel());
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeByteArray(address.getImg(), 0, address.getImg().length);
        holder.imageView.setImageBitmap(bitmap);
        //监听器配置
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(position);
                }
                return true;
            }
        });
        //结束配置
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
