package com.nick.nicklibdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.blankj.utilcode.util.LogUtils;
import com.nick.nicklibdemo.IMyAidlInterface;
import com.nick.nicklibdemo.dto.User;
import com.nick.nicklibdemo.manager.UserManager;

/**
 * Created by Nick on 2022/9/20 15:11.
 */
public class MyAIDLService extends Service {

    IMyAidlInterface.Stub stub =new IMyAidlInterface.Stub(){

        @Override
        public User getUser() throws RemoteException {
            User user = new User();
            user.setName(UserManager.getInstance().getName());
            return user;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public void onDestroy() {
        LogUtils.i("MyAIDLService onDestroy call");
        super.onDestroy();
    }
}
