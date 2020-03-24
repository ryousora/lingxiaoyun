package com.example.myapplication.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;
import java.util.Objects;


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

    public static String getDownload_max(Context context) {
        return SharedPreferencesUtil.getStringSharedPreferences(context,data,"download_max");
    }

    public static void setDownload_max(String download_max, Context context) {
        SharedPreferencesUtil.setStringSharedPreferences(context,data,"download_max",download_max);
    }

    public static List<Map<String,Object>> getDL_info(Context context) {
        String list_json=SharedPreferencesUtil.getStringSharedPreferences(context,data,"dl_info");
        if(!"".equals(list_json)){
            Gson gson=new Gson();
            return gson.fromJson(list_json,new TypeToken<List<Map<String,Object>>>(){}.getType());
        }
        return null;
    }

    public static void setDL_info(List<Map<String,Object>> dl_info, Context context) {
        Gson gson=new Gson();
        String list_json=gson.toJson(dl_info);
        SharedPreferencesUtil.setStringSharedPreferences(context,data,"dl_info",list_json);
    }

}