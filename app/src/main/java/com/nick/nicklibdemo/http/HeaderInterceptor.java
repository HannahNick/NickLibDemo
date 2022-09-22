package com.nick.nicklibdemo.http;

import android.content.Context;
import android.text.TextUtils;

import com.nick.nicklibdemo.util.ApkUtil;
import com.nick.nicklibdemo.util.EncryptUtil;
import com.nick.nicklib.util.SPUtil;
import com.nick.nicklibdemo.util.ScreenUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private Context mContext;
    public HeaderInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        String token = SPUtil.getInstance().getString(SPUtil.TOKEN, "");
        token = EncryptUtil.createToken(token);
        Request request;

        request = originalRequest.newBuilder()
                .header("source-type", "android")
//                .header("IMEI", ApkUtil.getDeviceId(mContext))
                .header("UA", getHeaderUserAgent())
                .header("encryptType", "2")
                .header("role-type", "cs")
                .header("token",token)
                .method(originalRequest.method(), originalRequest.body())
                .build();


        //获取token
        Response response = chain.proceed(request);
        String returnToken = response.headers().get("token");
        if (!TextUtils.isEmpty(returnToken)) {
            SPUtil.getInstance().putString(SPUtil.TOKEN, returnToken);
        }

        return response;
    }

    public String getHeaderUserAgent() {
        return ApkUtil.getVersionCode(mContext) + "&ADR&"
                + ScreenUtil.getScreenWidth(mContext) + "&"
                + ScreenUtil.getScreenHeight(mContext) + "&"
                + ApkUtil.getModel();
    }
}

