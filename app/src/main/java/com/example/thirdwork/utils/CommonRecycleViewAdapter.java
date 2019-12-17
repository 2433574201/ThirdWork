package com.example.thirdwork.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thirdwork.R;
import com.example.thirdwork.model.ImageResourceModel;

import java.util.List;

public abstract class CommonRecycleViewAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    List<ImageResourceModel> images;

    public CommonRecycleViewAdapter(List<ImageResourceModel> images) {
        this.images = images;
    }

    public List<ImageResourceModel> getImages() {
        return images;
    }

    public void setImages(List<ImageResourceModel> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common ,parent,false);
        CommonViewHolder holder = new CommonViewHolder(view) {
            @Override
            protected void Click(View v,int position) {
                click(v,position);
            }
        };
        return holder;
    }

    public abstract void click(View v,int position);

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        ImageResourceModel image = images.get(position);
        Glide.with(holder.imageView.getContext())
                .load("file:///android_asset/"+image.getEdit_diy_name())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
        System.out.println();
    }


    @Override
    public int getItemCount() {
        return images.size();
    }

}
