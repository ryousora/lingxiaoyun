package com.example.myapplication.utils;

import com.example.myapplication.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestBuild {
//    public static Gson gson;

    public static Retrofit retrofit;

    public static void RequestInit(){
        /*GsonBuilder builder=new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();*/

        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
//    public static Gson getGson(){
//        return gson;
//    }
    public static Retrofit getRetrofit() {
        return retrofit;
    }
    private static OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("token", User.getToken()==null?"":User.getToken())
                                .addHeader("contentType","application/json;charset=UTF-8")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    public static void setRetrofit(String Url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Url)
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient())
                .build();
    }


}
