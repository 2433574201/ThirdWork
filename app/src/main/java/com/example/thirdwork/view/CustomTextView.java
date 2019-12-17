package com.example.thirdwork.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.thirdwork.R;

public class CustomTextView extends AppCompatTextView {
    private float mCorner;
    private Context mContext;
    private Paint paint;
    private int backgroundColor = Color.parseColor("#1F120B");
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

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    public void init(Context context,AttributeSet attrs){
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomTextView );
        try{
            mCorner = a.getDimension(R.styleable.CustomTextView_mTCorner,10);
        }finally {
            a.recycle();
        }
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(0,0,canvas.getWidth(),canvas.getHeight());
        canvas.drawRoundRect(rectF,mCorner,mCorner,paint);
        super.onDraw(canvas);
    }
}
