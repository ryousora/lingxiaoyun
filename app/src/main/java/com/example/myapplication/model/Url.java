package com.example.myapplication.model;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.utils.Constant;
import com.example.myapplication.utils.MSP;
import com.example.myapplication.utils.RequestBuild;

public class Url {
    private static String ip;
    private static String port;

    public Url(){

    }
    public Url(String ip,String port){
        Url.ip =ip;
        Url.port=port;
        if(!ip.matches(Constant.ipPattern))
            Log.e("ip",ip);
        if(!port.matches(Constant.portPattern))
            Log.e("port",port);
        if(ip.matches(Constant.ipPattern)&&port.matches(Constant.portPattern))
            RequestBuild.setRetrofit(getUrl());
        else RequestBuild.setRetrofit("http://localhost:8080/");
    }

    public static String getUrl(){
        return "http://"+ip+":"+port+"/";
    }

    public static void  saveUrl(Context context){
        MSP.setIP(ip, context);
        MSP.setPort(port,context);
    }

    public static void initUrl(Context context){
        new Url(MSP.getIP(context),MSP.getPort(context));
        Log.e("",ip+"::::::::::"+port);
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Url.ip = ip;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        Url.port = port;
    }
}
