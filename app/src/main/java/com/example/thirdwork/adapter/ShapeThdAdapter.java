package com.example.thirdwork.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.example.thirdwork.R;
import com.example.thirdwork.activity.DiyActivity;
import com.example.thirdwork.model.ImageResourceModel;
import com.example.thirdwork.utils.ImageLoaderUtil;
import com.example.thirdwork.utils.CommonRecycleViewAdapter;

import java.util.List;

public class ShapeThdAdapter extends CommonRecycleViewAdapter {
    public ShapeThdAdapter(List<ImageResourceModel> images) {
        super(images);
    }

    @Override
    public void click(View v, int position) {
        ImageResourceModel imageResourceModel = getImages().get(position);
        String file_name = imageResourceModel.getName();
        Context context = v.getContext();
        Activity activity = (Activity) context;
        ImageView imageView = activity.findViewById(R.id.diy_image_view);
        Bitmap bitmap = ImageLoaderUtil.getBitmapByName(context,file_name);
        imageView.setImageBitmap(bitmap);
        DiyActivity.SPINNER_SHAPE = position;
        DiyActivity.CAN_SAVE = true;
    }

}
