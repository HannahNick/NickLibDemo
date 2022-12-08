package com.nick.nicklibdemo.dns;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.nick.nicklibdemo.constants.BaseUrl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

public class OkHttpDns implements Dns {
    private static final Dns SYSTEM = Dns.SYSTEM;
//    HttpDnsService httpdns;//httpdns 解析服务
    private static OkHttpDns instance = null;
    private OkHttpDns() {
//        this.httpdns = HttpDns.getService(context, "account id");
    }
    public static OkHttpDns getInstance() {
        if(instance == null) {
            instance = new OkHttpDns();
        }
        return instance;
    }


    @NonNull
    @Override
    public List<InetAddress> lookup(@NonNull String hostname) throws UnknownHostException {
//        if ("shop.haohuoji.com.cn".equals(hostname)){
//            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName("119.23.82.204"));
//            return inetAddresses;
//        }
//        if ("www.google.com".equals(hostname)){
//            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName("14.215.177.39"));
//            return inetAddresses;
//        }
//        if ("www.baidu.com".equals(hostname)){
//            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName("14.215.177.39"));
//            return inetAddresses;
//        }
        //通过异步解析接口获取ip
//        String ip = httpdns.getIpByHostAsync(hostname);
//        if(ip != null) {
//            //如果ip不为null，直接使用该ip进行网络请求
//            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
//            Log.e("OkHttpDns", "inetAddresses:" + inetAddresses);
//            return inetAddresses;
//        }
        LogUtils.i("params hostName:"+hostname);
        List<InetAddress> lookup = Dns.SYSTEM.lookup(hostname);
        for (InetAddress inetAddress : lookup) {
            LogUtils.i("hostname:"+inetAddress.getHostName(),"hostAddress:"+inetAddress.getHostAddress(),"canonicalHostName"+inetAddress.getCanonicalHostName());
        }
        //如果返回null，走系统DNS服务解析域名
        return lookup;
    }

}
