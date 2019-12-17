package com.example.thirdwork.utils;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdwork.R;

public abstract class CommonViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    public CommonViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.item_common_image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Click(v,position);
            }

        });
    }

    protected abstract void Click(View v,int position);
}
