package com.example.homework;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeViewHolder extends RecyclerView.ViewHolder {
    TextView tv,tv1;
    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        tv=itemView.findViewById(R.id.textView14);
        tv1=itemView.findViewById(R.id.textView22);
    }
}
