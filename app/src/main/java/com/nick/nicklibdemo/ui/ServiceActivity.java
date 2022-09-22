package com.nick.nicklibdemo.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nick.nicklib.base.activity.BaseActivity;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.service.MyService;

public class ServiceActivity extends BaseActivity implements View.OnClickListener , ServiceConnection {

    private Button mBtn_bind;
    private Button mBtn_request;

    private MyService.IMyServiceCallBack callBack;


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
                Intent intent = new Intent(this, MyService.class);
                bindService(intent,this,BIND_AUTO_CREATE);
                break;
            case R.id.btn_request:
                String callBackStr = callBack.doSomething("发送请求");
                Toast.makeText(this,callBackStr,Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        callBack = (MyService.IMyServiceCallBack) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}