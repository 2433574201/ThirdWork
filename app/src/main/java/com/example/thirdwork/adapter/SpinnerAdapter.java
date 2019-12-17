package com.example.thirdwork.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thirdwork.R;
import com.example.thirdwork.activity.HstryRekdActivity;
import com.example.thirdwork.env.MyApplication;


import java.io.File;
import java.util.List;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.ViewHolder> {
    private static final String TAG = "SpinnerAdapter";

    private List<String> spinners;

    public SpinnerAdapter(List<String> spinners) {
        this.spinners = spinners;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         ImageView imageView;
         ImageView select_bar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_view);
            select_bar = itemView.findViewById(R.id.select_bar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int last = HstryRekdActivity.POSITION;
            HstryRekdActivity.POSITION = this.getAdapterPosition();
            notifyItemChanged(last,1);
            notifyItemChanged(this.getAdapterPosition(),1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }else {
            if(position==HstryRekdActivity.POSITION){
                holder.select_bar.setVisibility(View.VISIBLE);
            }else {
                holder.select_bar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String spinner = spinners.get(position);
        holder.select_bar.setVisibility(View.INVISIBLE);
        File file = new File(MyApplication.getContext().getFilesDir(),spinner);
        Glide.with(holder.imageView.getContext())
                .load(file)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
        //Log.d(TAG, "onBindViewHolder: "+position);
        if(position==HstryRekdActivity.POSITION){
            holder.select_bar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return spinners.size();
    }

}
