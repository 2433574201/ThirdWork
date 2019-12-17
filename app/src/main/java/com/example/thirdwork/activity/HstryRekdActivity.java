package com.example.thirdwork.activity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.thirdwork.MainActivity;
import com.example.thirdwork.R;
import com.example.thirdwork.adapter.SpinnerAdapter;

public class HstryRekdActivity extends BaseActivity implements View.OnClickListener {

    public static int POSITION = 0;
    private ImageButton back_button;
    private ImageButton to_diy_button;
    private RecyclerView recyclerView;
    private SpinnerAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hstry_rekd);
        spinnerAdapter = new SpinnerAdapter(MainActivity.spinners);
        recyclerView = findViewById(R.id.top_recycle_view);
        processRecycleView(recyclerView);
        back_button = findViewById(R.id.his_back);
        to_diy_button = findViewById(R.id.to_diy);
        back_button.setOnClickListener(this);
        to_diy_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.his_back:
                clickBackButton();
                break;
            case R.id.to_diy:
                clickToDiyButton();
                break;
            default:
                break;
        }
    }

    private void clickToDiyButton() {
        Intent intent  = new Intent(this,DiyActivity.class);
        startActivity(intent);
    }

    private void clickBackButton() {
        Intent intent = new Intent();
        //将需要传入的信息带入Intent中，传递给MainActivity
        intent.putExtra("position",POSITION);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void processRecycleView(RecyclerView view){
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        view.setLayoutManager(manager);
        //view.getItemAnimator().setChangeDuration(0);
        view.setAdapter(spinnerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
