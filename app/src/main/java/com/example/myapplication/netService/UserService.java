package com.example.myapplication.netService;

import com.example.myapplication.model.ResponseResult;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserDTO;
import com.example.myapplication.netService.reqbody.LoginReqBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("user/register")
    Call<ResponseResult> register(@Body LoginReqBody user);

    @POST("user/login")
    Call<ResponseResult> login(@Body LoginReqBody user);

    @POST("user/checkname")
    Call<ResponseResult> checkName(@Body String username);

    @POST("user/check/{token}")
    Call<ResponseResult> check(@Path("token") String path,@Body String name);

    @POST("user/{username}/changePass")
    Call<ResponseResult> changePass(@Path("username") String username, @Body LoginReqBody user);

    @POST("user/{username}/edit")
    Call<ResponseResult> edit(@Path("username") String username, @Body UserDTO user);

    @GET("user/{username}/getUserInfo")
    Call<ResponseResult> getUserInfo(@Path("username") String username);

}
