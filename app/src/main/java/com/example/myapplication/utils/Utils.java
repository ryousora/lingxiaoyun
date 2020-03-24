package com.example.myapplication.utils;

import java.text.DecimalFormat;

public class Utils {
    //定义GB的计算常量
    private static final int GB = 1024 * 1024 *1024;
    //定义MB的计算常量
    private static final int MB = 1024 * 1024;
    //定义KB的计算常量
    private static final int KB = 1024;
    public static String bytes2kb(long bytes){
        //格式化小数
        DecimalFormat format = new DecimalFormat("###.0");
        if (bytes / GB >= 1){
            return format.format((double)bytes / GB) + "GB";
        }
        else if (bytes / MB >= 1){
            return format.format((double)bytes / MB) + "MB";
        }
        else if (bytes / KB >= 1){
            return format.format((double)bytes / KB) + "KB";
        }else {
            return bytes + "B";
        }
    }
}
