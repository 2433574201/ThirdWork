package com.example.thirdwork.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thirdwork.R;
import com.example.thirdwork.activity.DiyActivity;
import com.example.thirdwork.activity.ProcessPicActivity;
import com.example.thirdwork.model.ImageResourceModel;
import com.example.thirdwork.utils.CommonRecycleViewAdapter;

import java.util.List;

import static com.example.thirdwork.element.MaskElement.*;

public class CenterThdAdapter extends CommonRecycleViewAdapter {
    public CenterThdAdapter(List<ImageResourceModel> images) {
        super(images);
    }
    public static final int INNER_TYPE = 2;
    @Override
    public void click(View v, int position) {
        Activity activity = (Activity) v.getContext();
        ImageResourceModel imageResourceModel = getImages().get(position);
        ImageView imageView = activity.findViewById(R.id.diy_image_view);
        switch (DiyActivity.SPINNER_SHAPE){
            case -1:
                Toast.makeText(v.getContext(),"先选择shape",Toast.LENGTH_SHORT).show();
                break;
            case 0:
                startAction(activity, imageResourceModel.getName(), MaskElement_1.SHAPE_1_PATTERN_2, MaskElement_1.SHAPE_1_MASK_2);
                break;
            case 1:
                startAction(activity, imageResourceModel.getName(),MaskElement_2.SHAPE_2_PATTERN_2,MaskElement_2.SHAPE_2_MASK_2);
                break;
            case 2:
                startAction(activity, imageResourceModel.getName(),MaskElement_3.SHAPE_3_PATTERN_2,MaskElement_3.SHAPE_3_MASK_2);
                break;
            case 3:
                startAction(activity, imageResourceModel.getName(),MaskElement_4.SHAPE_4_PATTERN_2,MaskElement_4.SHAPE_4_MASK_2);
                break;
            case 4:
                startAction(activity, imageResourceModel.getName(),MaskElement_5.SHAPE_5_PATTERN_2,MaskElement_5.SHAPE_5_MASK_2);
                break;
            default:
                break;
        }

    }

    public void startAction(Activity activity,String inner_pic,String pattern,String mask){
        Intent intent = new Intent(activity, ProcessPicActivity.class);
        intent.putExtra("type",INNER_TYPE);
        intent.putExtra("inner_pic",inner_pic);
        intent.putExtra("shape_pattern_2", pattern);
        intent.putExtra("shape_mask_2", mask);
        activity.startActivityForResult(intent,INNER_TYPE);
    }


}
