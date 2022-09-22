package com.nick.nicklibdemo.application;

import android.app.Application;

import com.nick.nicklib.util.SPUtil;

/**
 * Created by Nick on 9/14/22 4:59 PM.
 */
public class MyApplication extends Application {

    private static MyApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initUtil();
    }

    public void initUtil(){
        SPUtil.getInstance().init(this);
    }


    public static MyApplication getInstance(){
        return mApplication;
    }
}
