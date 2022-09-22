package com.nick.nicklibdemo.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.nick.nicklib.BuildConfig;
import com.nick.nicklibdemo.api.Api;
import com.nick.nicklibdemo.application.MyApplication;
import com.nick.nicklibdemo.constants.BaseUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nick on 2017/9/7.
 * 网络请求管理类
 */

public class HttpManager {

    private OkHttpClient mClient = new OkHttpClient()
            .newBuilder()
            .addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(BuildConfig.DEBUG ?
                            HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
            .addInterceptor(new HeaderInterceptor(MyApplication.getInstance()))
            .addNetworkInterceptor(BuildConfig.DEBUG ?new StethoInterceptor():new EmptyInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
    private Retrofit mCommonRetrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl.URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    private static final Gson mGson = new Gson();

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        return Holder.sManager;
    }

    public Gson getGson(){
        return mGson;
    }

    /**
     * 网络接口
     */
    private Api api = mCommonRetrofit.create(Api.class);

    public Api getApi() {
        return api;
    }

    public void setReleaseApi(){
//        BaseUrl.URL = BaseUrl.RELEASE_URL;
//        api =new Retrofit.Builder()
//                .baseUrl(BaseUrl.URL)
//                .client(mClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build()
//                .create(Api.class);
    }

    public static class Holder {
        private static final HttpManager sManager = new HttpManager();
    }
}
