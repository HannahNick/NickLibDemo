package com.nick.nicklibdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;


/**
 * Created by Nick on 2022/9/20 09:47.
 */
public class MyService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i("MyService onCreate call");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("MyService onStartCommand call");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        LogUtils.i("MyService onDestroy call");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceBinder();
    }

    public static class MyServiceBinder extends Binder implements IMyServiceCallBack{


        @Override
        public String doSomething(String str) {
            String format = String.format("MyServiceBinder has deal str %1$s", str);
            return format;
        }


    }

    public interface IMyServiceCallBack{
        String doSomething(String str);
    }
}
