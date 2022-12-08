package com.nick.nicklibdemo.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.frostnerd.dnschanger.helper.DNSHelper;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.http.HttpManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class DnsTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dns_test);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DNSHelper.getInstance().handleCallBack(requestCode, resultCode);
    }

    public void importHost(View view){
        DNSHelper.getInstance().importHost("www.google.com", "39.101.64.103");
    }

    public void clearHost(View view){
        DNSHelper.getInstance().clearHostCache();
    }

    public void stop(View view){
        DNSHelper.getInstance().stopVpn();
    }

    public void requestConnect(View view){
        DNSHelper.getInstance().startVpn(this);
    }



    private void apiTest(){
        Request request = new Request.Builder()
                .url("http://open.vipexam.org/")
                .get()
                .build();

        HttpManager.getInstance()
                .getClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.i(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtils.i(response.body().toString());
                    }
                });
    }
}