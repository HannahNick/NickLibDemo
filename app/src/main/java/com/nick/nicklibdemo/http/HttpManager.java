package com.nick.nicklibdemo.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.nick.nicklib.BuildConfig;
import com.nick.nicklibdemo.api.Api;
import com.nick.nicklibdemo.application.MyApplication;
import com.nick.nicklibdemo.constants.BaseUrl;
import com.nick.nicklibdemo.dns.OkHttpDns;
import com.nick.nicklibdemo.dns.SSLSocketClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.dnsoverhttps.DnsOverHttps;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nick on 2017/9/7.
 * 网络请求管理类
 */

public class HttpManager {
    OkHttpClient bootstrapClient = new OkHttpClient.Builder().build();

//    DnsOverHttps dns = new DnsOverHttps.Builder()
//            .client(bootstrapClient)
//            .url(HttpUrl.parse("http://www.baidu.com"))
//            .bootstrapDnsHosts(getName("14.215.177.39"), getName("8.8.8.8"))
//            .build();

    private OkHttpClient mClient = bootstrapClient
            .newBuilder()
            .dns(OkHttpDns.getInstance())
            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
            .addInterceptor(new HeaderInterceptor(MyApplication.getInstance()))
            .addNetworkInterceptor(BuildConfig.DEBUG ?new StethoInterceptor():new EmptyInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();


    private Retrofit mCommonRetrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl.URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    private InetAddress getName(String name){
        try {
            return InetAddress.getByName(name);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OkHttpClient getClient(){
        return mClient;
    }


    private HttpManager() {


//        try {
//            OkHttpClient mClient = new OkHttpClient()
//                    .newBuilder()
//                    .build();
//            DnsOverHttps dns = new DnsOverHttps.Builder()
//                    .url(HttpUrl.parse("http://www.google.com"))
//                    .bootstrapDnsHosts(InetAddress.getByName("14.215.177.39"), InetAddress.getByName("8.8.8.8"))
//                    .build();
//            mClient = mClient.newBuilder()
//                    .dns(dns)
////                    .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ?
////                                    HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
//                    .addInterceptor(new HeaderInterceptor(MyApplication.getInstance()))
//                    .addNetworkInterceptor(BuildConfig.DEBUG ?new StethoInterceptor():new EmptyInterceptor())
//                    .connectTimeout(15, TimeUnit.SECONDS)
//                    .readTimeout(15, TimeUnit.SECONDS)
//                    .writeTimeout(15, TimeUnit.SECONDS)
//                    .build();
//            Retrofit mCommonRetrofit = new Retrofit.Builder()
//                    .baseUrl(BaseUrl.URL)
//                    .client(mClient)
//                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//            api = mCommonRetrofit.create(Api.class);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }

    }

    public static HttpManager getInstance() {
        return Holder.sManager;
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
