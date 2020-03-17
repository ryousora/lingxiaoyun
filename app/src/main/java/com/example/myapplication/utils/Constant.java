package com.example.myapplication.utils;

import android.os.Environment;

import java.io.File;
import java.util.regex.Pattern;

public class Constant {

    private static String rootStorage= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator;
    private static final String savePath =rootStorage+"sharenote"+ File.separator;
    public static final  String imgSavePath=savePath+"img"+ File.separator;
    public static final  String fileSavePath=savePath+"file"+ File.separator;
    public static String cachePath =savePath+"cache"+ File.separator;
    /*public static final String ipPattern = "`[a-zA-Z0-9][.a-zA-z0-9]+[a-zA-Z0-9]$";*/
    public static final String ipPattern ="([a-zA-Z0-9]+)(\\.[a-zA-Z0-9]+)+";
    public static final String portPattern = "[0-9]{1,5}";
}
