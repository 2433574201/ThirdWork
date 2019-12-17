package com.example.thirdwork.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thirdwork.MainActivity;
import com.example.thirdwork.R;
import com.example.thirdwork.adapter.CenterThdAdapter;
import com.example.thirdwork.adapter.EdgeThdAdapter;
import com.example.thirdwork.adapter.MaterialThdAdapter;
import com.example.thirdwork.adapter.ShapeThdAdapter;
import com.example.thirdwork.element.MaskElement;
import com.example.thirdwork.utils.BitmapUtil;
import com.example.thirdwork.utils.CommonRecycleViewAdapter;
import com.example.thirdwork.model.ImageResourceModel;
import com.example.thirdwork.utils.ImageLoaderUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DiyActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE_OPEN = 3 ;
    private static final int SELECT_CENTER_TYPE = 5;
    private static final int SELECT_OUTER_TYPE = 4;
    public static int SPINNER_SHAPE = -1;
    public static boolean CAN_SAVE = false;
    public static int STAGE = 0;

    private ImageView diy_back;
    private ImageView diy_confirm;
    private ImageView diy_cancel;
    private ImageView get_pic_from_sdcard;
    private RecyclerView recyclerView;
    private ImageView diy_image_view;
    private ImageView diy_shape;
    private ImageView diy_material;
    private ImageView diy_outer;
    private ImageView diy_inner;
    private ImageView select_arrow_1;
    private ImageView select_arrow_2;
    private ImageView select_arrow_3;
    private ImageView select_arrow_4;

    private boolean is_shape = true;
    private boolean is_material = false;
    private boolean is_outer = false;
    private boolean is_inner = false;



    private List<ImageResourceModel> edge_images;
    private List<ImageResourceModel> shape_images;
    private List<ImageResourceModel> material_images;
    private List<ImageResourceModel> center_images;
    private CommonRecycleViewAdapter edge_adapter;
    private CommonRecycleViewAdapter shape_adapter;
    private CommonRecycleViewAdapter material_adapter;
    private CommonRecycleViewAdapter center_adapter;
    private CommonRecycleViewAdapter cur_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy);
        initImageList();

        diy_image_view = findViewById(R.id.diy_image_view);
        diy_back = findViewById(R.id.diy_back);
        diy_confirm = findViewById(R.id.diy_confirm);
        recyclerView = findViewById(R.id.diy_recycle_view);
        diy_shape = findViewById(R.id.diy_shape);
        diy_material = findViewById(R.id.diy_material);
        diy_outer = findViewById(R.id.diy_outer);
        diy_inner = findViewById(R.id.diy_inner);
        diy_cancel = findViewById(R.id.diy_cancel);
        get_pic_from_sdcard = findViewById(R.id.get_pic_from_sdcard);
        select_arrow_1 = findViewById(R.id.select_arrow_1);
        select_arrow_2 = findViewById(R.id.select_arrow_2);
        select_arrow_3 = findViewById(R.id.select_arrow_3);
        select_arrow_4 = findViewById(R.id.select_arrow_4);


        edge_adapter = new EdgeThdAdapter(edge_images);
        shape_adapter = new ShapeThdAdapter(shape_images);
        material_adapter = new MaterialThdAdapter(material_images);
        center_adapter = new CenterThdAdapter(center_images);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        cur_adapter = shape_adapter;
        recyclerView.setAdapter(shape_adapter);

        diy_cancel.setOnClickListener(this);
        get_pic_from_sdcard.setOnClickListener(this);
        diy_confirm.setOnClickListener(this);
        diy_back.setOnClickListener(this);
        diy_inner.setOnClickListener(this);
        diy_outer.setOnClickListener(this);
        diy_material.setOnClickListener(this);
        diy_shape.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if(resultCode==RESULT_OK){
                    //边缘图片
                    byte[] buf = data.getByteArrayExtra("bitmap");
                    Bitmap photo_bmp = BitmapFactory.decodeByteArray(buf, 0, buf.length);
                    processOuter(photo_bmp);
                }
                break;
            case 2:
                if(resultCode==RESULT_OK){
                    //中心图片
                    byte[] buf = data.getByteArrayExtra("bitmap");
                    Bitmap photo_bmp = BitmapFactory.decodeByteArray(buf, 0, buf.length);
                    Bitmap centerBitmap = processSize(photo_bmp);
                    Bitmap newBitmap = BitmapUtil.editBitmapByBitmap(getImageBitmap(),centerBitmap, PorterDuff.Mode.SRC_OVER);
                    diy_image_view.setImageBitmap(newBitmap);
                    diy_image_view.setForeground(null);
                }
                break;
            case REQUEST_IMAGE_OPEN:
                if(resultCode==RESULT_OK) {
                    Uri uri = data.getData();
                    String pic_name = uri.toString();
                    if(STAGE==2){
                        switch (DiyActivity.SPINNER_SHAPE){
                            case -1:
                                Toast.makeText(this,"先选择shape",Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                startActionForOuter(pic_name, MaskElement.MaskElement_1.SHAPE_1_PATTERN_1, MaskElement.MaskElement_1.SHAPE_1_MASK_1);
                                break;
                            case 1:
                                startActionForOuter(pic_name, MaskElement.MaskElement_2.SHAPE_2_PATTERN_1, MaskElement.MaskElement_2.SHAPE_2_MASK_1);
                                break;
                            case 2:
                                startActionForOuter(pic_name, MaskElement.MaskElement_3.SHAPE_3_PATTERN_1, MaskElement.MaskElement_3.SHAPE_3_MASK_1);
                                break;
                            case 3:
                                startActionForOuter(pic_name, MaskElement.MaskElement_4.SHAPE_4_PATTERN_1, MaskElement.MaskElement_4.SHAPE_4_MASK_1);
                                break;
                            case 4:
                                startActionForOuter(pic_name, MaskElement.MaskElement_5.SHAPE_5_PATTERN_1, MaskElement.MaskElement_5.SHAPE_5_MASK_1);
                                break;
                            default:
                                break;
                        }
                    }
                    if(STAGE==3){
                        switch (DiyActivity.SPINNER_SHAPE){
                            case -1:
                                Toast.makeText(this,"先选择shape",Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                startActionForInner(pic_name, MaskElement.MaskElement_1.SHAPE_1_PATTERN_2, MaskElement.MaskElement_1.SHAPE_1_MASK_2);
                                break;
                            case 1:
                                startActionForInner(pic_name, MaskElement.MaskElement_2.SHAPE_2_PATTERN_2, MaskElement.MaskElement_2.SHAPE_2_MASK_2);
                                break;
                            case 2:
                                startActionForInner(pic_name, MaskElement.MaskElement_3.SHAPE_3_PATTERN_2, MaskElement.MaskElement_3.SHAPE_3_MASK_2);
                                break;
                            case 3:
                                startActionForInner(pic_name, MaskElement.MaskElement_4.SHAPE_4_PATTERN_2, MaskElement.MaskElement_4.SHAPE_4_MASK_2);
                                break;
                            case 4:
                                startActionForInner(pic_name, MaskElement.MaskElement_5.SHAPE_5_PATTERN_2, MaskElement.MaskElement_5.SHAPE_5_MASK_2);
                                break;
                            default:
                                break;
                        }
                    }
                }

                break;
            default:
                break;
        }
    }
    public void startActionForOuter(String select_pic, String pattern, String mask){
        Intent intent = new Intent(this, ProcessPicActivity.class);
        intent.putExtra("type", SELECT_OUTER_TYPE);
        intent.putExtra("outer_pic",select_pic);
        intent.putExtra("shape_pattern", pattern);
        intent.putExtra("shape_mask", mask);
        startActivityForResult(intent, EdgeThdAdapter.OUTER_TYPE);
    }
    public void startActionForInner(String select_pic, String pattern, String mask){
        Intent intent = new Intent(this, ProcessPicActivity.class);
        intent.putExtra("type",SELECT_CENTER_TYPE);
        intent.putExtra("inner_pic",select_pic);
        intent.putExtra("shape_pattern_2", pattern);
        intent.putExtra("shape_mask_2", mask);
        startActivityForResult(intent,CenterThdAdapter.INNER_TYPE);
    }
    public Bitmap getImageBitmap(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) diy_image_view.getDrawable();
        return bitmapDrawable.getBitmap();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.diy_back:
                clickDiyBack();
                break;
            case R.id.diy_confirm:
                clickDiyConfirm();
                break;
            case R.id.diy_inner:
                clickDiyInner();
                break;
            case R.id.diy_material:
                clickDiyMaterial();
                break;
            case R.id.diy_outer:
                clickDiyOuter();
                break;
            case R.id.diy_shape:
                clickDiyShape();
                break;
            case R.id.diy_cancel:
                clickDiyCancel();
                break;
            case R.id.get_pic_from_sdcard:
                clickGetPicFromSDcard();
                break;
            default:
                break;
        }
    }

    private void clickGetPicFromSDcard() {
        if(STAGE==2||STAGE==3) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
            startActivityForResult(intent, REQUEST_IMAGE_OPEN);
        }
    }

    private void clickDiyCancel() {
        diy_image_view.setImageBitmap(null);
        CAN_SAVE = false;
        STAGE = 0;
    }

    private void clickDiyShape() {
        STAGE = 0;
        if(cur_adapter!=shape_adapter) {
            recyclerView.setAdapter(shape_adapter);
            cur_adapter = shape_adapter;
        }
        if(is_shape==false) {
            is_shape = true;
        }
        if(select_arrow_1.getVisibility()==View.INVISIBLE){
            select_arrow_1.setVisibility(View.VISIBLE);
            select_arrow_2.setVisibility(View.INVISIBLE);
            select_arrow_3.setVisibility(View.INVISIBLE);
            select_arrow_4.setVisibility(View.INVISIBLE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            diy_image_view.setForeground(null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void clickDiyOuter() {
        STAGE = 2;
        if(cur_adapter!=edge_adapter) {
            recyclerView.setAdapter(edge_adapter);
            cur_adapter = edge_adapter;
        }
        if(is_outer==false) {
            is_outer = true;
        }
        if(select_arrow_3.getVisibility()==View.INVISIBLE){
            select_arrow_1.setVisibility(View.INVISIBLE);
            select_arrow_2.setVisibility(View.INVISIBLE);
            select_arrow_3.setVisibility(View.VISIBLE);
            select_arrow_4.setVisibility(View.INVISIBLE);
        }
        switch (SPINNER_SHAPE){
            case 0:
                diy_image_view.setForeground(ImageLoaderUtil.
                        getDrawableByName(this,"pic/template_pic/1/shape1_hint1.png"));
                break;
            case 1:
                diy_image_view.setForeground(ImageLoaderUtil.
                        getDrawableByName(this,"pic/template_pic/2/shape2_hint1.png"));
                break;
            case 2:
                diy_image_view.setForeground(ImageLoaderUtil.
                        getDrawableByName(this,"pic/template_pic/3/shape3_hint1.png"));
                break;
            case 3:
                diy_image_view.setForeground(ImageLoaderUtil.
                        getDrawableByName(this,"pic/template_pic/4/shape4_hint1.png"));
                break;
            case 4:
                diy_image_view.setForeground(ImageLoaderUtil.
                        getDrawableByName(this,"pic/template_pic/5/shape5_hint1.png"));
                break;
            default:
                break;
        }
    }

    private void clickDiyMaterial() {
        STAGE = 1;
        if(cur_adapter!=material_adapter) {
            recyclerView.setAdapter(material_adapter);
            cur_adapter = material_adapter;
        }
        if(is_material==false) {
            is_material = true;
        }
        if(select_arrow_2.getVisibility()==View.INVISIBLE){
            select_arrow_1.setVisibility(View.INVISIBLE);
            select_arrow_2.setVisibility(View.VISIBLE);
            select_arrow_3.setVisibility(View.INVISIBLE);
            select_arrow_4.setVisibility(View.INVISIBLE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            diy_image_view.setForeground(null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void clickDiyInner() {
        STAGE = 3;
        if(cur_adapter!=center_adapter) {
            recyclerView.setAdapter(center_adapter);
            cur_adapter = center_adapter;
        }
        if(is_inner==false) {
            is_inner = true;
        }
        if(select_arrow_4.getVisibility()==View.INVISIBLE){
            select_arrow_1.setVisibility(View.INVISIBLE);
            select_arrow_2.setVisibility(View.INVISIBLE);
            select_arrow_3.setVisibility(View.INVISIBLE);
            select_arrow_4.setVisibility(View.VISIBLE);
        }
        switch (SPINNER_SHAPE){
            case 0:
                diy_image_view.setForeground(ImageLoaderUtil.
                            getDrawableByName(this,"pic/template_pic/1/shape1_hint2.png"));

                break;
            case 1:
                diy_image_view.setForeground(ImageLoaderUtil.
                            getDrawableByName(this,"pic/template_pic/2/shape2_hint2.png"));
                break;
            case 2:
                diy_image_view.setForeground(ImageLoaderUtil.
                            getDrawableByName(this,"pic/template_pic/3/shape3_hint2.png"));
                break;
            case 3:
                diy_image_view.setForeground(ImageLoaderUtil.
                            getDrawableByName(this,"pic/template_pic/4/shape4_hint2.png"));
                break;
            case 4:
                diy_image_view.setForeground(ImageLoaderUtil.
                        getDrawableByName(this,"pic/template_pic/5/shape5_hint2.png"));
                break;
            default:
                break;
        }
    }

    private void clickDiyConfirm() {
        if(CAN_SAVE) {
            HstryRekdActivity.POSITION = 0;
            //保存
            OutputStream os = null;
            try {
                os = openFileOutput(MainActivity.FILE_PRE_NAME + MainActivity.spinners.size() + ".png", MODE_PRIVATE);
                getImageBitmap().compress(Bitmap.CompressFormat.PNG, 100, os);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this,"至少要选择shape!",Toast.LENGTH_SHORT).show();
        }
    }

    private void clickDiyBack() {
        finish();
    }

    private void initImageList(){
        edge_images = ImageLoaderUtil.getEdgeImageResource();
        shape_images = ImageLoaderUtil.getShapeImageResource();
        center_images = ImageLoaderUtil.getCenterImageResource();
        material_images = ImageLoaderUtil.getMaterialImageResource();
    }
    private Bitmap processSize(Bitmap bitmap){
        Bitmap result = null;
        switch (SPINNER_SHAPE){
            case -1:
                break;
            case 0:
                result = BitmapUtil.changeSize(bitmap,240,240);
                break;
            case 1:
                result = BitmapUtil.changeSize(bitmap,170,170);
                break;
            case 2:
                result = BitmapUtil.changeSize(bitmap,160,160);
                break;
            case 3:
                result = BitmapUtil.changeSize(bitmap,180,180);
                break;
            case 4:
                result = BitmapUtil.changeSize(bitmap,210,210);
                break;
            default:
                break;
        }
        return result;
    }
    private void processOuter(Bitmap photo_bmp){
        Matrix matrix = new Matrix();
        Bitmap nPhoto_bmp = null;
        Bitmap newOuterBitmap_1 = null;
        Bitmap newOuterBitmap_2 = null;
        Bitmap newOuterBitmap_3 = null;
        Bitmap newOuterBitmap_4 = null;
        Bitmap newBitmap_1 = null;
        Bitmap newBitmap_2 = null;
        Bitmap newBitmap_3 = null;
        Bitmap newBitmap_4 = null;
        Bitmap newBitmap = null;

        switch (SPINNER_SHAPE){
            case -1:
                break;
            case 0:
                matrix.reset();
                matrix.setRotate(-45);
                newOuterBitmap_1 = Bitmap.createBitmap(photo_bmp,0,0,photo_bmp.getWidth(),photo_bmp.getHeight(),matrix,true);
                newBitmap_1 = BitmapUtil.editBitmapByBitmap(getImageBitmap(),newOuterBitmap_1, PorterDuff.Mode.SRC_OVER,42,42);
                matrix.reset();matrix.setRotate(135);
                newOuterBitmap_2 = Bitmap.createBitmap(photo_bmp,0,0,photo_bmp.getWidth(),photo_bmp.getHeight(),matrix,true);
                newBitmap = BitmapUtil.editBitmapByBitmap(newBitmap_1,newOuterBitmap_2, PorterDuff.Mode.SRC_OVER,357,357);
                diy_image_view.setImageBitmap(newBitmap);
                break;
            case 1:
                nPhoto_bmp = BitmapUtil.changeSize(photo_bmp,160,160);
                newOuterBitmap_1 = Bitmap.createBitmap(nPhoto_bmp);
                newBitmap_1 = BitmapUtil.editBitmapByBitmap(getImageBitmap(),newOuterBitmap_1, PorterDuff.Mode.SRC_OVER,270,80);
                matrix.reset();matrix.setRotate(-30);
                newOuterBitmap_2 = Bitmap.createBitmap(nPhoto_bmp,0,0,nPhoto_bmp.getWidth(),nPhoto_bmp.getHeight(),matrix,true);
                newBitmap_2 = BitmapUtil.editBitmapByBitmap(newBitmap_1,newOuterBitmap_2, PorterDuff.Mode.SRC_OVER,77,335);
                matrix.reset();matrix.setRotate(30);
                newOuterBitmap_3 = Bitmap.createBitmap(nPhoto_bmp,0,0,nPhoto_bmp.getWidth(),nPhoto_bmp.getHeight(),matrix,true);
                newBitmap = BitmapUtil.editBitmapByBitmap(newBitmap_2,newOuterBitmap_3, PorterDuff.Mode.SRC_OVER,406,335);
                diy_image_view.setImageBitmap(newBitmap);
                break;
            case 2:
                nPhoto_bmp = BitmapUtil.changeSize(photo_bmp,160,160);
                newBitmap_1 = BitmapUtil.editBitmapByBitmap(getImageBitmap(),nPhoto_bmp, PorterDuff.Mode.SRC_OVER,80,270);
                newBitmap = BitmapUtil.editBitmapByBitmap(newBitmap_1,nPhoto_bmp, PorterDuff.Mode.SRC_OVER,460,270);
                diy_image_view.setImageBitmap(newBitmap);
                break;
            case 3:
                nPhoto_bmp = BitmapUtil.changeSize(photo_bmp,168,142);
                newBitmap_1 = BitmapUtil.editBitmapByBitmap(getImageBitmap(),nPhoto_bmp, PorterDuff.Mode.SRC_OVER,266,68);
                matrix.reset();matrix.setRotate(-72);
                newOuterBitmap_1 = Bitmap.createBitmap(nPhoto_bmp,0,0,nPhoto_bmp.getWidth(),nPhoto_bmp.getHeight(),matrix,true);
                newBitmap_2 = BitmapUtil.editBitmapByBitmap(newBitmap_1,newOuterBitmap_1, PorterDuff.Mode.SRC_OVER,57,182);
                matrix.reset();matrix.setRotate(-144);
                newOuterBitmap_2 = Bitmap.createBitmap(nPhoto_bmp,0,0,nPhoto_bmp.getWidth(),nPhoto_bmp.getHeight(),matrix,true);
                newBitmap_3 = BitmapUtil.editBitmapByBitmap(newBitmap_2,newOuterBitmap_2, PorterDuff.Mode.SRC_OVER,117,413);
                matrix.reset();matrix.setRotate(72);
                newOuterBitmap_3 = Bitmap.createBitmap(nPhoto_bmp,0,0,nPhoto_bmp.getWidth(),nPhoto_bmp.getHeight(),matrix,true);
                newBitmap_4 = BitmapUtil.editBitmapByBitmap(newBitmap_3,newOuterBitmap_3, PorterDuff.Mode.SRC_OVER,457,182);
                matrix.reset();matrix.setRotate(144);
                newOuterBitmap_4 = Bitmap.createBitmap(nPhoto_bmp,0,0,nPhoto_bmp.getWidth(),nPhoto_bmp.getHeight(),matrix,true);
                newBitmap = BitmapUtil.editBitmapByBitmap(newBitmap_4,newOuterBitmap_4, PorterDuff.Mode.SRC_OVER,364,413);
                diy_image_view.setImageBitmap(newBitmap);
                break;
            case 4:
                nPhoto_bmp = BitmapUtil.changeSize(photo_bmp,120,120);
                newBitmap_1 = BitmapUtil.editBitmapByBitmap(getImageBitmap(),nPhoto_bmp, PorterDuff.Mode.SRC_OVER,290,510);
                matrix.reset();matrix.setRotate(-60);
                newOuterBitmap_1 = Bitmap.createBitmap(nPhoto_bmp,0,0,nPhoto_bmp.getWidth(),nPhoto_bmp.getHeight(),matrix,true);
                newBitmap_2 = BitmapUtil.editBitmapByBitmap(newBitmap_1,newOuterBitmap_1, PorterDuff.Mode.SRC_OVER,77,158);
                matrix.reset();;matrix.setRotate(60);
                newOuterBitmap_2 = Bitmap.createBitmap(nPhoto_bmp,0,0,nPhoto_bmp.getWidth(),nPhoto_bmp.getHeight(),matrix,true);
                newBitmap = BitmapUtil.editBitmapByBitmap(newBitmap_2,newOuterBitmap_2, PorterDuff.Mode.SRC_OVER,458,158);
                diy_image_view.setImageBitmap(newBitmap);
                break;
            default:
                break;
        }
    }
}
