package com.example.thirdwork.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.thirdwork.R;
import com.example.thirdwork.utils.BitmapUtil;
import com.example.thirdwork.utils.DegreesUtil;
import com.example.thirdwork.utils.ImageLoaderUtil;
import com.example.thirdwork.view.CustomButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProcessPicActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private Intent intent;
    private int type;
    private String bg_pic_filename;
    private String pattern_pic_filename;
    private String mask_pic_filename;

    ImageView process_pic_image_view_mask;
    ImageView process_pic_image_view;
    ImageView foreground_image_view;
    ImageView invisiable_image_view;
    CustomButton cancel_button;
    CustomButton confirm_button;

    private Bitmap sourceBitmap;

    private float distance_of_finger = -1;
    float scale;
    private float last_x;
    private float last_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_pic);
        intent = getIntent();
        type = intent.getIntExtra("type",0);
        initFilename(type);
        cancel_button = findViewById(R.id.process_pic_cancel_button);
        confirm_button = findViewById(R.id.process_pic_done_button);
        cancel_button.setOnClickListener(this);
        confirm_button.setOnClickListener(this);
        process_pic_image_view_mask = findViewById(R.id.process_pic_image_view_mask);
        invisiable_image_view = findViewById(R.id.in_visiable_image_view);
        process_pic_image_view = findViewById(R.id.process_pic_image_view);
        foreground_image_view = findViewById(R.id.foreground_image_view);
        foreground_image_view.setOnTouchListener(this);
        initImageView();
        if(type==1||type==2)
            sourceBitmap = ImageLoaderUtil.getBitmapByName(this,bg_pic_filename);
        if(type==4||type==5){
            Uri uri = Uri.parse(bg_pic_filename);
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            sourceBitmap = BitmapFactory.decodeStream(is);
        }

    }

    private void initImageView() {
        if(type==4||type==5){
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(bg_pic_filename)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Glide.with(this).load(bitmap)
                    .into(process_pic_image_view);
        }
        if(type==1||type==2) {
            Glide.with(this).load("file:///android_asset/" + bg_pic_filename)
                    .into(process_pic_image_view);
        }
        Glide.with(this).load("file:///android_asset/" + mask_pic_filename)
//                .override(1050,1050)
                .into(foreground_image_view);
        Glide.with(this).load("file:///android_asset/" + pattern_pic_filename)
//                .override(1050,1050)
                .into(invisiable_image_view);
    }

    private void initFilename(int type) {
        if(type==1||type==4){
            //边缘图片处理
            bg_pic_filename = intent.getStringExtra("outer_pic");
            pattern_pic_filename = intent.getStringExtra("shape_pattern");
            mask_pic_filename = intent.getStringExtra("shape_mask");
        }
        if(type==2||type==5){
            //中心图片处理
            bg_pic_filename = intent.getStringExtra("inner_pic");
            pattern_pic_filename = intent.getStringExtra("shape_pattern_2");
            mask_pic_filename = intent.getStringExtra("shape_mask_2");
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                last_x = event.getX();
                last_y = event.getY();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) process_pic_image_view.getDrawable();
                Bitmap b  = bitmapDrawable.getBitmap();

                break;
            case MotionEvent.ACTION_MOVE:
                touchHandler(event);
                break;
            default:
                distance_of_finger = -1;
                break;
        }
        return true;
    }
    public void  touchHandler(MotionEvent event){
        if(event.getPointerCount()==2) {
            float cur_distance = DegreesUtil.getDistanceOfFingue(event);
            if (distance_of_finger < 0) distance_of_finger = cur_distance;
            scale = cur_distance/distance_of_finger;
            if(scale<1)scale = 1;
            if(scale>6)scale = 6;
//            Matrix matrix = new Matrix();
//            matrix.setScale(scale+1,scale+1);
//            Log.d("SCALE", "touchHandler: "+scale);
//            Bitmap nbitmap = Bitmap.createBitmap(sourceBitmap,0,0,sourceBitmap.getWidth(),
//                    sourceBitmap.getHeight(),matrix,false);
//            process_pic_image_view.setImageBitmap(nbitmap);
            process_pic_image_view.setScaleX(scale);
            process_pic_image_view.setScaleY(scale);
        }else if(event.getPointerCount()==1){
//            Matrix matrix = new Matrix();
//            matrix.setTranslate(event.getX()-last_x,event.getY()-last_y);
//            Bitmap curBitmap = ((BitmapDrawable)process_pic_image_view.getDrawable()).getBitmap();
//            Bitmap nbitmap = Bitmap.createBitmap(curBitmap,0,0,curBitmap.getWidth(),
//                    curBitmap.getHeight(),matrix,true);
//            process_pic_image_view.setImageBitmap(nbitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.process_pic_cancel_button:
                clickCancel();
                break;
            case R.id.process_pic_done_button:
                clickDone();
                break;
            default:
                break;
        }
    }

    private void clickDone() {
        Bitmap shot_bitmap = BitmapUtil.shotView(process_pic_image_view);
        Bitmap n_shot_bitmap = Bitmap.createBitmap(shot_bitmap,
                (int)((process_pic_image_view.getWidth()-(process_pic_image_view.getWidth()/scale))/2),
                (int)((process_pic_image_view.getHeight()-(process_pic_image_view.getHeight()/scale))/2)
                ,(int)(process_pic_image_view.getWidth()/scale),
                (int)(process_pic_image_view.getHeight()/scale));
        n_shot_bitmap = BitmapUtil.changeSize(n_shot_bitmap,foreground_image_view.getWidth(),foreground_image_view.getHeight());
        BitmapDrawable drawable = (BitmapDrawable) invisiable_image_view.getDrawable();
        Bitmap pattern = drawable.getBitmap();
        Bitmap result_bitmap = BitmapUtil.editBitmapByBitmap(pattern,n_shot_bitmap);
        Bitmap newBitmap = BitmapUtil.changeSize(result_bitmap,240,187);

        byte[] buf ;
        buf = bitmap2Bytes(newBitmap);
        Intent intent = new Intent();
        intent.putExtra("bitmap", buf);
        setResult(RESULT_OK,intent);
        finish();
    }
    private byte[] bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private void clickCancel() {
        finish();
    }
}
