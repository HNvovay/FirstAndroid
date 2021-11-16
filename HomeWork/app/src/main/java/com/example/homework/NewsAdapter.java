package com.example.homework;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<HomeViewHolder> {
    List<News> list;

    public NewsAdapter(List<News> list) {
        this.list = list;
    }
    //自定义监听器；
    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }
    private OnItemClickListener listener;
    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemLongClickListener {
        void onClick(int position);
    }
    private OnItemLongClickListener longClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
    //结束定义
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_viewholder,null);
        final View view1= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show,null);
        final HomeViewHolder holder=new HomeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, final int position) {
        News news=list.get(position);
        holder.tv.setText(news.getNews_title());
        holder.tv1.setText(String.valueOf(news.getNews_great()));
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
