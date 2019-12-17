package com.example.thirdwork.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.thirdwork.model.ImageResourceModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageLoaderUtil {
    public static final String ASSETS_PRE = "";
    public static final String POST = ".png";

    //中心图片 1-10
    public static final String CENTER_PRE = "pic/center_pic/";
    public static final String CENTER_PATTERN = "center/inner_pattern_";
    public static final String CENTER_EDIT_DIY = "thund_center/edit_diy_inner_pattern";
    //边缘图片 1-10
    public static final String EDGE_PRE = "pic/edge_pic/";
    public static final String EDGE_PATTERN = "edge/outer_pattern_";
    public static final String EDGE_EDIT_DIY = "thund_edge/edit_diy_outer_pattern";
    //材质图片 1-16
    public static final String MATERIAL_PRE = "pic/material_pic/";
    public static final String MATERIAL_PATTERN = "material/material_";
    public static final String MATERIAL_EDIT_DIY = "thund_material/edit_diy_material";
    //形状图片 1-5
    public static final String SHAPE_PRE = "pic/shape_pic/";
    public static final String SHAPE_PATTERN = "shape/shape";
    public static final String SHAPE_EDIT_DIY = "thund_shape/edit_diy_shape";


    public static List<ImageResourceModel> getImageByName(String pre, String pattern, String edit_diy, int start, int end ){
        List<ImageResourceModel> list = new ArrayList<>();
        for(int i=start;i<=end;i++) {
            String name = ASSETS_PRE + pre + pattern + i + POST;
            String edit_diy_name = ASSETS_PRE + pre + edit_diy + i + POST;
            ImageResourceModel imageResourceModel = new ImageResourceModel(edit_diy_name,name);
            list.add(imageResourceModel);
        }
        return list;
    }

    public static List<ImageResourceModel> getCenterImageResource(){
        return getImageByName(CENTER_PRE,CENTER_PATTERN,CENTER_EDIT_DIY,1,10);
    }
    public static List<ImageResourceModel> getEdgeImageResource(){
        return getImageByName(EDGE_PRE,EDGE_PATTERN,EDGE_EDIT_DIY,1,10);
    }
    public static List<ImageResourceModel> getMaterialImageResource(){
        return getImageByName(MATERIAL_PRE,MATERIAL_PATTERN,MATERIAL_EDIT_DIY,1,16);
    }
    public static List<ImageResourceModel> getShapeImageResource(){
        return getImageByName(SHAPE_PRE,SHAPE_PATTERN,SHAPE_EDIT_DIY,1,5);
    }

    public static Bitmap getBitmapByName(Context context ,String name){
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            Context mCon = context;
            is = context.getAssets().open(name);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is!=null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static Drawable getDrawableByName(Context context,String name){
        InputStream is = null;
        Drawable drawable = null;
        Bitmap bitmap = null;
        try {
            is = context.getAssets().open(name);
            bitmap = BitmapFactory.decodeStream(is);
            drawable = new BitmapDrawable(context.getResources(),bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return drawable;
    }
}
