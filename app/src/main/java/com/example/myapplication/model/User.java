package com.example.myapplication.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.utils.MSP;

public class User {
    private static Integer userId;
    private static String username;
    private static String password;
    private static String token;
    private static int gender;
    private static String email;
    private static String iconimg_url;

    public User() {
        userId=-1;
        token="0";
        gender=0;
    }

    public User(String username, String password) {
        userId=-1;
        User.username = username;
        User.password = password;
        token="0";
        gender=0;
    }

    public static void saveSP(Context context){
        MSP.setId(userId,context);
        MSP.setUsername(username,context);
        MSP.setToken(token,context);
    }

    public static void loginOut(Context context){
        userId=-1;
        password=null;
        token="0";
        gender=0;
        email=null;
        iconimg_url=null;
//        saveSP(context);
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(Integer userId) {
        User.userId = userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static int getGender() {
        return gender;
    }

    public static void setGender(int gender) {
        User.gender = gender;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getIconimg_url() {
        return iconimg_url;
    }

    public static void setIconimg_url(String iconimg_url) {
        User.iconimg_url = iconimg_url;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        User.token = token;
    }

    public static String toStr() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", iconimg_url='" + iconimg_url + '\'' +
                '}';
    }
}
