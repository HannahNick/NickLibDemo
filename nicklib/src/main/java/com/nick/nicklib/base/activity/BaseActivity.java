package com.nick.nicklib.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity基础类,页面初始化的基本流程模板
 * Created by Nick on 9/14/22 9:24 AM.
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        findViews();
        initListener();
        init();
    }

    /**
     * 设置对应的布局
     */
    protected abstract int setLayout();

    /**
     * 初始化控件
     */
    protected abstract void findViews();

    /**
     * 初始化除了控件以外的其他数据
     */
    protected abstract void init();

    /**
     * 初始化各种控件的事件响应
     */
    protected abstract void initListener();

    /**
     * 释放内存、解决内存泄漏
     */
    protected abstract void release();

    @Override
    protected void onDestroy() {
        release();
        super.onDestroy();
    }
}
