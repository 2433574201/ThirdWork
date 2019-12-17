package com.example.thirdwork.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thirdwork.R;
import com.example.thirdwork.activity.DiyActivity;
import com.example.thirdwork.activity.ProcessPicActivity;
import com.example.thirdwork.element.MaskElement;
import com.example.thirdwork.model.ImageResourceModel;
import com.example.thirdwork.utils.CommonRecycleViewAdapter;

import java.util.List;

public class EdgeThdAdapter extends CommonRecycleViewAdapter {

    public static final int OUTER_TYPE = 1;
    public EdgeThdAdapter(List<ImageResourceModel> images) {
        super(images);
    }

    @Override
    public void click(View v, int position) {
        Activity activity = (Activity) v.getContext();
        ImageView imageView = activity.findViewById(R.id.diy_image_view);
        ImageResourceModel imageResourceModel = getImages().get(position);
        switch (DiyActivity.SPINNER_SHAPE){
                case -1:
                    Toast.makeText(v.getContext(),"先选择shape",Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    startAction(activity, imageResourceModel.getName(), MaskElement.MaskElement_1.SHAPE_1_PATTERN_1, MaskElement.MaskElement_1.SHAPE_1_MASK_1);
                    break;
                case 1:
                    startAction(activity, imageResourceModel.getName(), MaskElement.MaskElement_2.SHAPE_2_PATTERN_1, MaskElement.MaskElement_2.SHAPE_2_MASK_1);
                    break;
                case 2:
                    startAction(activity, imageResourceModel.getName(), MaskElement.MaskElement_3.SHAPE_3_PATTERN_1, MaskElement.MaskElement_3.SHAPE_3_MASK_1);
                    break;
                case 3:
                    startAction(activity, imageResourceModel.getName(), MaskElement.MaskElement_4.SHAPE_4_PATTERN_1, MaskElement.MaskElement_4.SHAPE_4_MASK_1);
                    break;
                case 4:
                    startAction(activity, imageResourceModel.getName(), MaskElement.MaskElement_5.SHAPE_5_PATTERN_1, MaskElement.MaskElement_5.SHAPE_5_MASK_1);
                    break;
                default:
                break;
        }

    }

    public void startAction(Activity activity,String outer_pic,String pattern,String mask){
        Intent intent = new Intent(activity, ProcessPicActivity.class);
        intent.putExtra("type",OUTER_TYPE);
        intent.putExtra("outer_pic",outer_pic);
        intent.putExtra("shape_pattern", pattern);
        intent.putExtra("shape_mask", mask);
        activity.startActivityForResult(intent,OUTER_TYPE);
    }

}
