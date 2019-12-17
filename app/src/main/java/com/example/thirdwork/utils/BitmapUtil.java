package com.example.thirdwork.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;

public class BitmapUtil {
    public static Bitmap shotView(View v){
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(),v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
    public static Bitmap editBitmapByBitmap(Bitmap dest,Bitmap src,PorterDuff.Mode mode){
        Bitmap bitmap = Bitmap.createBitmap(dest.getWidth(),dest.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(mode));
        canvas.drawBitmap(dest,0,0,null);
        canvas.drawBitmap(src,(dest.getWidth()-src.getWidth())/2,(dest.getHeight()-src.getHeight())/2,paint);
        return bitmap;
    }
    public static Bitmap editBitmapByBitmap(Bitmap dest,Bitmap src){
        Bitmap bitmap = Bitmap.createBitmap(dest.getWidth(),dest.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(dest,0,0,null);
        canvas.drawBitmap(src,(dest.getWidth()-src.getWidth())/2,(dest.getHeight()-src.getHeight())/2,paint);
        return bitmap;
    }
    public static Bitmap editBitmapByBitmap(Bitmap dest,Bitmap src,PorterDuff.Mode mode,int x,int y){
        Bitmap bitmap = Bitmap.createBitmap(dest.getWidth(),dest.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(mode));
        canvas.drawBitmap(dest,0,0,null);
        canvas.drawBitmap(src,x,y,paint);
        return bitmap;
    }

    public static Bitmap changeSize(Bitmap bitmap,int n_w,int n_h){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleX =((float) n_w)/w;
        float scaleY = ((float) n_h)/h;
        matrix.setScale(scaleX,scaleY);
        Bitmap nBitmap =Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);
        return nBitmap;
    }
}
