package com.example.homework;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView address_tel, address_name;
    Button btn_delete;
    public AddressViewHolder(@NonNull View itemView) {
        super(itemView);
        address_tel =itemView.findViewById(R.id.textView100);
        address_name =itemView.findViewById(R.id.textView18);
        btn_delete=itemView.findViewById(R.id.button5);
        imageView=itemView.findViewById(R.id.imageView16);
    }
}
