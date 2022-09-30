package com.nick.nicklibdemo.ui;

import android.view.View;

import com.nick.nicklib.base.activity.BaseActivity;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.view.MySurfaceView;

/**
 * Created by Nick on 2022/9/30 14:55.
 */
public class SurfaceViewActivity extends BaseActivity {

    private MySurfaceView mySurfaceView;

    @Override
    protected int setLayout() {
        return R.layout.activity_surfaceview;
    }

    @Override
    protected void findViews() {
        mySurfaceView = findViewById(R.id.msv_v);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void release() {
        mySurfaceView.stop();
    }

}
