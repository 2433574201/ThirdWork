package com.example.thirdwork.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.example.thirdwork.R;

public class CustomButton extends AppCompatButton {

    private float mCorner;
    private Context mContext;
    private Paint mPaint;
    private int mBackgroundColor = Color.parseColor("#fef13c");

    public float getmCorner() {
        return mCorner;
    }

    public void setmCorner(float mCorner) {
        this.mCorner = mCorner;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    public void init(Context context, AttributeSet attrs){
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton);
        try{
            mCorner = typedArray.getDimension(R.styleable.CustomButton_mCorner,10);
        }finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mBackgroundColor);
        RectF rectF = new RectF(0,0,canvas.getWidth(),canvas.getHeight());
        canvas.drawRoundRect(rectF,mCorner,mCorner,mPaint);
        super.onDraw(canvas);
    }
}
