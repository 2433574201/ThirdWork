package com.example.thirdwork.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thirdwork.R;
import com.example.thirdwork.activity.DiyActivity;
import com.example.thirdwork.element.MaskElement;
import com.example.thirdwork.model.ImageResourceModel;
import com.example.thirdwork.utils.ImageLoaderUtil;
import com.example.thirdwork.utils.CommonRecycleViewAdapter;

import java.util.List;

public class MaterialThdAdapter extends CommonRecycleViewAdapter {
    public MaterialThdAdapter(List<ImageResourceModel> images) {
        super(images);
    }

    @Override
    public void click(View v, int position) {
        Activity activity = (Activity) v.getContext();
        ImageView imageView = activity.findViewById(R.id.diy_image_view);
        ImageResourceModel image = getImages().get(position);
        String material_filename = image.getName();
        switch (DiyActivity.SPINNER_SHAPE){
            case -1:
                Toast.makeText(v.getContext(),"请先完成骨架选择",Toast.LENGTH_SHORT).show();
                break;
            case 0:
                makeSpinnerMaterial(v,material_filename,
                        MaskElement.MaskElement_1.SHAPE_1_SHADOW,
                        MaskElement.MaskElement_1.SHAPE_1_PATTERN,imageView);
                break;
            case 1:
                //Toast.makeText(v.getContext(),"2",Toast.LENGTH_SHORT).show();
                makeSpinnerMaterial(v,material_filename,
                        MaskElement.MaskElement_2.SHAPE_2_SHADOW,
                        MaskElement.MaskElement_2.SHAPE_2_PATTERN,imageView);
                break;
            case 2:
                //Toast.makeText(v.getContext(),"3",Toast.LENGTH_SHORT).show();
                makeSpinnerMaterial(v,material_filename,
                        MaskElement.MaskElement_3.SHAPE_3_SHADOW,
                        MaskElement.MaskElement_3.SHAPE_3_PATTERN,imageView);
                break;
            case 3:
                //Toast.makeText(v.getContext(),"4",Toast.LENGTH_SHORT).show();
                makeSpinnerMaterial(v,material_filename,
                        MaskElement.MaskElement_4.SHAPE_4_SHADOW,
                        MaskElement.MaskElement_4.SHAPE_4_PATTERN,imageView);
                break;
            case 4:
                //Toast.makeText(v.getContext(),"5",Toast.LENGTH_SHORT).show();
                makeSpinnerMaterial(v,material_filename,
                        MaskElement.MaskElement_5.SHAPE_5_SHADOW,
                        MaskElement.MaskElement_5.SHAPE_5_PATTERN,imageView);
                break;
            default:
                break;
        }
    }

    public void makeSpinnerMaterial(View v,String material_filename,String shadow_path,String pattern_path,ImageView target){
        Bitmap material_pic = ImageLoaderUtil.getBitmapByName(v.getContext(),material_filename);
        Bitmap shadow_pic = ImageLoaderUtil.getBitmapByName(v.getContext(),shadow_path);
        Bitmap pattern_pic = ImageLoaderUtil.getBitmapByName(v.getContext(), pattern_path);
        Bitmap bitmap = Bitmap.createBitmap(material_pic.getWidth(),material_pic.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(material_pic,0,0,null);
        canvas.drawBitmap(pattern_pic,0,0,paint);
        canvas.drawBitmap(shadow_pic,0,0,null);
        target.setImageBitmap(bitmap);
    }
}
