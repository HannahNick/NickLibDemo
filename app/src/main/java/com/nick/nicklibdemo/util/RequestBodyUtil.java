package com.nick.nicklibdemo.util;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Nick on 2017/11/3.
 * 生成RequestBody
 */

public class RequestBodyUtil {

    private Gson mGson = new Gson();

    private RequestBodyUtil(){
    }

    public static RequestBodyUtil getInstance(){
        return Holder.sUtil;
    }

    /**
     * 标准请求模式
     * @param obj
     * @return
     */
    public RequestBody getRequestBody(Object obj){
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),mGson.toJson(obj));
    }


    private static class Holder{
        private static final RequestBodyUtil sUtil = new RequestBodyUtil();
    }
}
