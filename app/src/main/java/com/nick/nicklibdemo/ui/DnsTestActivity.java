package com.nick.nicklibdemo.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.frostnerd.dnschanger.helper.DNSHelper;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.http.HttpManager;
import com.nick.nicklibdemo.service.AccessVpnService;

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
//        if (!isAccessibilitySettingsOn(getApplicationContext())) {
//            Toast.makeText(getApplicationContext(), "请开启辅助服务", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//            startActivity(intent);
//        }
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


    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        // TestService为对应的服务
        final String service = getPackageName() + "/" + AccessVpnService.class.getCanonicalName();
        // com.z.buildingaccessibilityservices/android.accessibilityservice.AccessibilityService
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public void startAccessService(View view){
        Intent intent = new Intent(this,AccessVpnService.class);
        startService(intent);
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