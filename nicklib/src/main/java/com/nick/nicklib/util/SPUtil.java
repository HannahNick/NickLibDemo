package com.nick.nicklib.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by Nick on 2017/9/7.
 */

public class SPUtil {
    private SharedPreferences sp = null;
    private SharedPreferences.Editor editor = null;
    /**
     * Preference文件名
     */
    private static final String SHARE_PREFREENCE_NAME = "heyguys.pre";



    /**
     * 上次更新时间
     */
    public static final String LAST_UPDATE_PRODUCT = "LAST_UPDATE_PRODUCT";
    public static final String IS_SHOW_GUIDE = "IS_SHOW_GUIDE";

    //用户登录token
    public static final String TOKEN = "token";
    //上次登录时间
    public static final String LAST_LOGIN = "LAST_LOGIN";
    //用户Id
    public static final String USER_ID = "USER_ID";
    //当前版本
    public static final String VERSION_NAME = "VERSION_NAME";
    //城市ID
    public static final String CITY_ID = "CITY_ID";
    //城市名称
    public static final String CITY_NAME = "CITY_NAME";
    //食品厂客户认证
    public static final String BIGCUSTOMER_QULIFICATION_TIME = "BIGCUSTOMER_QULIFICATION_TIME";
    //每天的日期
    public static final String DAY_TIME = "DAY_TIME";
    //幕帘弹出次数
    public static final String POPWINDOW_TIMES = "POPWINDOW_TIMES";
    //城市地址版本号
    public static final String LOCATION_TOKEN = "LOCATION_TOKEN";
    //所有城市区街道地址信息
    public static final String LOCATION_CONTENT = "LOCATION_CONTENT";
    //用户选择的地址信息
    public static final String ADDRESS_DATA = "ADDRESS_DATA";
    //首次安装
    public static final String FIRST_INSTALL = "FIRST_INSTALL";

    private SPUtil() {

    }

    public static SPUtil getInstance() {
        return Holder.sUtil;
    }

    public void init(Application app){
        sp = app.getSharedPreferences(SHARE_PREFREENCE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void putLong(String key, Long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, int defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public boolean isKeyExist(String key) {
        return sp.contains(key);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }


    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    private static class Holder{
        private static final SPUtil sUtil = new SPUtil();
    }
}
