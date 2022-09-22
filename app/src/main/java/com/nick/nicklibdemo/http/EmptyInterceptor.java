package com.nick.nicklibdemo.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nick on 2019-09-18.
 * 空拦截器没有什么实际作用
 */
public class EmptyInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        return chain.proceed(originalRequest);
    }
}
