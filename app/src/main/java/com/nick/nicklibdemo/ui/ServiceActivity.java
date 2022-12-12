package com.nick.nicklibdemo.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.nick.nicklib.base.activity.BaseActivity;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.service.MyService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class ServiceActivity extends BaseActivity implements View.OnClickListener , ServiceConnection {

    private Button mBtn_bind;
    private Button mBtn_request;

    private MyService.IMyServiceCallBack callBack;
    private Queue<Runnable> runnables = new LinkedList<>();


    @Override
    protected int setLayout() {
        return R.layout.activity_service;
    }

    @Override
    protected void findViews() {
        mBtn_bind = findViewById(R.id.btn_bind);
        mBtn_request = findViewById(R.id.btn_request);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {
        mBtn_bind.setOnClickListener(this);
        mBtn_request.setOnClickListener(this);
    }

    @Override
    protected void release() {
        unbindService(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bind:
                checkServiceAlive();
                break;
            case R.id.btn_request:
                String callBackStr = callBack.doSomething("发送请求");
                Toast.makeText(this,callBackStr,Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private void checkServiceAlive(){
        if (!ServiceUtils.isServiceRunning(MyService.class)){
            Intent intent = new Intent(ServiceActivity.this, MyService.class);
            bindService(intent,ServiceActivity.this,BIND_AUTO_CREATE);
            runnables.add(() -> {
                String str = callBack.doSomething("发送请求");
                LogUtils.i(str);
            });
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        callBack = (MyService.IMyServiceCallBack) service;
        while (!runnables.isEmpty()){
            runnables.poll().run();
        }
        LogUtils.i("所有任务执行完");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}