package com.example.myapplication.utils;

import android.content.Context;

import cn.qqtheme.framework.util.StorageUtils;


public class MSP {

    private static final String data="data";

    public static int getId(Context context) {
        return SharedPreferencesUtil.getIntSharedPreferences(context,data,"id");
    }

    public static void setId(int id, Context context) {
        SharedPreferencesUtil.setIntSharedPreferences(context,data,"id",id);
    }

    public static String getUsername(Context context) {
        return SharedPreferencesUtil.getStringSharedPreferences(context,data,"username");
    }

    public static void setUsername(String username, Context context) {
        SharedPreferencesUtil.setStringSharedPreferences(context,data,"username",username);
    }

    public static String getIP(Context context) {
        return SharedPreferencesUtil.getStringSharedPreferences(context,data,"ip");
    }

    public static void setIP(String ip, Context context) {
        SharedPreferencesUtil.setStringSharedPreferences(context,data,"ip",ip);
    }

    public static String getPort(Context context) {
        return SharedPreferencesUtil.getStringSharedPreferences(context,data,"port");
    }

    public static void setPort(String port, Context context) {
        SharedPreferencesUtil.setStringSharedPreferences(context,data,"port",port);
    }

    public static String getToken(Context context) {
        return SharedPreferencesUtil.getStringSharedPreferences(context,data,"token");
    }

    public static void setToken(String token, Context context) {
        SharedPreferencesUtil.setStringSharedPreferences(context,data,"token",token);
    }

    public static String getDownloadPath(Context context) {
        String path=SharedPreferencesUtil.getStringSharedPreferences(context,data,"path");
        if (path==null|| "".equals(path)){
            path= StorageUtils.getDownloadPath();
        }
        return path;
    }

    public static void setDownloadPath(String path, Context context) {
        SharedPreferencesUtil.setStringSharedPreferences(context,data,"path",path);
    }

}