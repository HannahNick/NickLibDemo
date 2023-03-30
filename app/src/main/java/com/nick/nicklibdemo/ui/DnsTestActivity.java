package com.nick.nicklibdemo.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.LogUtils;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.button.MaterialButton;
import com.nick.dnschanger.database.entities.IPPortPair;
import com.nick.dnschanger.dialogs.DNSEntryListDialog;
import com.nick.dnschanger.helper.DNSHelper;
import com.nick.dnschanger.util.PreferencesAccessor;
import com.nick.dnschanger.util.ThemeHandler;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.http.HttpManager;
import com.nick.nicklibdemo.service.AccessVpnService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class DnsTestActivity extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;
    private MaterialButton mBtn_vpn;
    private TextView tv_main_service;
    private TextView tv_second_service;
    private YoYo.YoYoString mYoYoString;
    private DNSEntryListDialog mServiceDiaLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dns_test);
//        if (!isAccessibilitySettingsOn(getApplicationContext())) {
//            Toast.makeText(getApplicationContext(), "请开启辅助服务", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//            startActivity(intent);
//        }
        mBtn_vpn = findViewById(R.id.btn_toggle_vpn);
        tv_main_service = findViewById(R.id.tv_main_service);
        tv_second_service = findViewById(R.id.tv_second_service);
        lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.setAnimation(R.raw.no_sign);
        buttonAnimatePlay();
    }

    private void buttonAnimatePlay(){
        mYoYoString = YoYo.with(Techniques.Pulse)
                .duration(2000)
                .repeat(YoYo.INFINITE)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .playOn(mBtn_vpn);
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

    public void requestConnect(View view){
        boolean serviceRunning = DNSHelper.getInstance().toggleVpn(this);
        if (serviceRunning){
            lottieAnimationView.resumeAnimation();
            mYoYoString.stop();
            mBtn_vpn.setText("停止Vpn");
            mBtn_vpn.setBackgroundColor(Color.parseColor("#ea2027"));
        }else {
            lottieAnimationView.pauseAnimation();
            buttonAnimatePlay();
            mBtn_vpn.setBackgroundColor(Color.parseColor("#74b9ff"));
            mBtn_vpn.setText("开启Vpn");
        }
    }

    public void selectDns(View view){
        showDialog();
    }

    private void showDialog(){
        new DNSEntryListDialog(this, ThemeHandler.getDialogTheme(this), new DNSEntryListDialog.OnProviderSelectedListener() {
                @Override
                public void onProviderSelected(String name, IPPortPair dns1, IPPortPair dns2, IPPortPair dns1V6, IPPortPair dns2V6) {
                    boolean port = PreferencesAccessor.areCustomPortsEnabled(DnsTestActivity.this);
                    boolean ipEnabled = PreferencesAccessor.isIPv4Enabled(DnsTestActivity.this);
                    if(ipEnabled){
                        PreferencesAccessor.Type.DNS1.saveDNSPair(DnsTestActivity.this, dns1);
                        PreferencesAccessor.Type.DNS2.saveDNSPair(DnsTestActivity.this, dns2);
                    }
                    LogUtils.i("dns1:"+dns1.toString(port)+"|dns2:"+dns2.toString(port)+"|dns1V6:"+dns1V6.toString(port)+"|dns2v6:"+dns2V6.toString(port));
                    tv_main_service.setText(String.format("Main Service:%1$s",dns1.getAddress()));
                    tv_second_service.setText(String.format("Second Service:%1$s",dns2.getAddress()));
                    DNSHelper.getInstance().updateVpn();
                }
            }).show();
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



    public void apiTest(View view){
        Request request = new Request.Builder()
//                .url("http://open.vipexam.org/")
                .url("https://www.qq.com")
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