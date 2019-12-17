package com.example.thirdwork;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ImageView;

import com.example.thirdwork.activity.BaseActivity;
import com.example.thirdwork.activity.HstryRekdActivity;
import com.example.thirdwork.env.MyApplication;
import com.example.thirdwork.utils.DegreesUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "MainActivity";

    private static final int TO_HISTORY_ACTIVITY = 1;
    public static final String FILE_PRE_NAME = "spinner_";

    private Bitmap bitmap;
    public static LinkedList<String> spinners = new LinkedList<>();
    private ImageView slide_up_image;
    private ImageView main_image;

    //处理image旋转用的成员
    private float last_x;
    private float last_y;
    private float center_x;
    private float center_y;
    private float degree = 0;
    private float velocity;
    private VelocityTracker tracker;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpinners();
        timer = new Timer();
        tracker = VelocityTracker.obtain();
        slide_up_image = findViewById(R.id.slide_up_image_view);
        slide_up_image.setOnClickListener(this);
        main_image = findViewById(R.id.main_image_view);
        main_image.setOnTouchListener(this);
        if(spinners.size()!=0) {
            bitmap = getBitmapInFile(spinners.getFirst());
        }
        if(bitmap!=null) {
            main_image.setImageBitmap(bitmap);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.slide_up_image_view:
                toOtherActivity(HstryRekdActivity.class,TO_HISTORY_ACTIVITY);
                break;
            default:
                break;
        }
    }
    public void toOtherActivity(Class a,int requestCode){
        Intent intent = new Intent(this,a);
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TO_HISTORY_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    //根据获取得到的信息，将对应的Bitmap设置到ImageView中
                    int position = data.getIntExtra("position",0);
                    String spinner_name = spinners.get(position);
                    Bitmap bitmap = getBitmapInFile(spinner_name);
                    main_image.setImageBitmap(bitmap);
                }
                break;
            default:
                break;
        }
    }
    public static void initSpinners(){
        String[] files = MyApplication.getContext().fileList();
        spinners.clear();
        spinners.addAll(Arrays.asList(files));
        Collections.reverse(spinners);
    }

    public Bitmap getBitmapInFile(String name) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = openFileInput(name);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
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
        return bitmap;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        handlerImageOnTouch(v,event);
        return true;
    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        timer.cancel();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        timer.cancel();
//    }

    private void handlerImageOnTouch(View v, MotionEvent event) {
        tracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                center_x = main_image.getWidth()/2;
                center_y = main_image.getHeight()/2;
                last_x = event.getX();
                last_y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float cur_x = event.getX();
                float cur_y = event.getY();
                degree = (float) ((degree+DegreesUtil.getRotate(center_x,center_y,last_x,last_y,cur_x,cur_y))%360);
                Log.d("Main","degree "+degree);
                main_image.setRotation(degree);
//                tracker.computeCurrentVelocity(1000);
//                Log.d(TAG, "handlerImageOnTouch: "+tracker.getXVelocity()+" "+tracker.getYVelocity());
                break;
            case MotionEvent.ACTION_UP:
                tracker.computeCurrentVelocity(1000);
                velocity = Math.abs(tracker.getXVelocity());

                Log.d(TAG, "handlerImageOnTouch: "+tracker.getXVelocity()+" "+tracker.getYVelocity());
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        float flag = degree/Math.abs(degree);
                        main_image.setRotation(-((degree+flag*velocity*10)%360));
                        velocity = velocity/1.004f;
                        if(velocity<0.1){
                            cancel();
                        }
                    }
                },0,5);
                break;
            default:
                break;
        }
    }
}
