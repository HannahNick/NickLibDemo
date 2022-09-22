package com.nick.nicklibdemo.ui;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.ConnectionService;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.nick.nicklib.base.activity.BaseActivity;
import com.nick.nicklibdemo.IMyAidlInterface;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.dto.User;
import com.nick.nicklibdemo.manager.UserManager;
import com.nick.nicklibdemo.service.MyAIDLService;

public class NewProcessActivity extends BaseActivity implements ServiceConnection {

    public static final String SM_CODE = "0x001";
    public static final int REQUEST_CODE = 0x001;
    public static final int RESULT_CODE = 0x0001;
    IMyAidlInterface iMyAidlInterface;


    @Override
    protected int setLayout() {
        return R.layout.activity_new_process;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        if (intent!=null){
            String stringExtra = intent.getStringExtra(SM_CODE);
            LogUtils.i("NewProcessActivity getExtra ".concat(stringExtra));
        }
        LogUtils.i("NewProcessActivity init ");

        String name = UserManager.getInstance().getName();
        LogUtils.i("NewProcessActivity getIntent name:" + name);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void release() {
        if (iMyAidlInterface!=null){
            unbindService(this);
        }
    }

    public void exit(View view){
        Intent intent = new Intent();
        intent.putExtra(SM_CODE,"555");
        setResult(RESULT_CODE,intent);
        finish();
    }

    public void bind(View view){
        Intent intent = new Intent(this, MyAIDLService.class);
        bindService(intent,this,BIND_AUTO_CREATE);
    }

    public void getUser(View view){
        try {
            User user = iMyAidlInterface.getUser();
            ToastUtils.showLong(user.getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        iMyAidlInterface = null;
    }
}