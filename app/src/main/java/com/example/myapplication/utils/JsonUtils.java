/*
package com.example.myapplication.utils;

import java.lang.reflect.Type;
import com.google.gson.Gson;
*/
/**
 * Java对象和JSON字符串相互转化工具类
 *//*

public final class JsonUtils {

    private static Gson gson=RequestBuild.getGson();

    private JsonUtils(){}

    */
/**
     * 对象转换成json字符串
     * @param obj
     * @return
     *//*

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    */
/**
     * json字符串转成对象
     * @param str
     * @param type
     * @return
     *//*

    public static <T> T fromJson(String str, Type type) {
        return gson.fromJson(str, type);
    }

    */
/**
     * json字符串转成对象
     * @param str
     * @param type
     * @return
     *//*

    public static <T> T fromJson(String str, Class<T> type) {
        return gson.fromJson(str, type);
    }

}*/
