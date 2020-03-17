package com.example.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPreferencesUtil {


    public static void setIntSharedPreferences(Context context, String name, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setBooleanSharedPreferences(Context context, String name, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setFloatSharedPreferences(Context context, String name, String key, float value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void setStringSharedPreferences(Context context, String name, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(key)) {
            editor.remove(key);
            editor.apply();
        }
        editor.putString(key, value);
        editor.commit();
    }

    public static void setLongSharedPreferences(Context context, String name, String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void setStringSetSharedPreferences(Context context, String name, String key, Set<String> value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static int getIntSharedPreferences(Context context, String name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static boolean getBooleanSharedPreferences(Context context, String name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    public static float getFloatSharedPreferences(Context context, String name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0);
    }

    public static String getStringSharedPreferences(Context context, String name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static long getLongSharedPreferences(Context context, String name, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public static Set<String> getStringSetSharedPreferences(Context context, String name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sharedPreferences.getStringSet(key, null);
    }


}
