package com.example.myapplication.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.utils.RequestBuild;
import com.example.myapplication.model.ResponseResult;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserDTO;
import com.example.myapplication.netService.UserService;
import com.example.myapplication.netService.reqbody.LoginReqBody;
import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.utils.ToastUtils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRequest {

    public static void register(final Context context){
        UserService service = RequestBuild.getRetrofit().create(UserService.class);
        LoginReqBody user = new LoginReqBody(User.getUsername(),User.getPassword());
        Call<ResponseResult> call = service.register(user);

        Log.e("",  user.getUsername()+"..."+user.getPassword());

        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                Log.e("",result.toString());
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_LONG).show();
                if(result.getStatus()==200) {
                    User.setUsername((String)result.getData().get("username"));
                    User.saveSP(context);
                    ((Activity)context).finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void login(final Context context){
        UserService service = RequestBuild.getRetrofit().create(UserService.class);
        LoginReqBody user = new LoginReqBody(User.getUsername(),User.getPassword());
        Call<ResponseResult> call = service.login(user);

        Log.e("",  user.getUsername()+"..."+user.getPassword());

        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                if(result==null){
                    ToastUtils.showMessage(context,"网络错误！请重试！");
                    return;
                }
                Map data = result.getData();
                Log.e("",result.toString());
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_LONG).show();
                if(result.getStatus()==200) {
                    User.setUsername((String)data.get("username"));
                    /*User.setUserId(Integer.valueOf((String) Objects.requireNonNull(data.get("id"))));*/
                    User.setUserId(/*Double.valueOf((double)*/(int)data.get("id")/*).intValue()*/);
                    User.setToken((String)data.get("token"));
                    User.saveSP(context);
                    ((Activity)context).finish();
                    getUserInfo(context);
                }
            }
            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getUserInfo(final Context context) {

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                */
        UserService service = RequestBuild.getRetrofit().create(UserService.class);
        Call<ResponseResult> call = service.getUserInfo(User.getUsername());/*
                Response<ResponseResult> response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert response != null;
                ResponseResult result = response.body();*/
        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                Map data = result.getData();
                //UserDTO user = RequestBuild.getGson().fromJson((String)data.get("user"),UserDTO.class);
                Map user = (Map) data.get("user");
                Log.e("", result.toString());
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_LONG).show();
                if (result.getStatus() == 201) {
                    assert user != null;
                    User.setUsername((String) user.get("username"));
                    User.setUserId(/*Double.valueOf((double) */(int)user.get("userId")/*).intValue()*/);
                    User.setGender(/*Double.valueOf((double) */(int)user.get("gender")/*).intValue()*/);
                    User.setEmail((String) user.get("email"));
                    User.setIconimg_url((String) user.get("iconimg_url"));
                    User.saveSP(context);
/*
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);*/
                } else {
                    Toast.makeText(context, "系统出错！", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
//                    ((Activity) context).finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
            /*}
        }).run();*/
    }

/*
    public static void check(final Context context){
        UserService service = RequestBuild.getRetrofit().create(UserService.class);
        Call<ResponseResult> call = service.check(User.getToken(),User.getUsername());

        Log.e("",  User.getUsername());

        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                Map data = result.getData();
                Log.e("",result.toString());
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_LONG).show();
                if(result.getStatus()==200) {
                    User.setUsername((String) data.get("username"));
                    User.setUserId((int) data.get("id"));
                    User.saveSP(context);
                }else{
                    */
/*Toast.makeText(context, "验证出错，请重新登录", Toast.LENGTH_LONG).show();*//*

                    Intent intent=new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                t.printStackTrace();
                Log.e("",t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
*/

    public static void edit(final Context context){
        UserService service = RequestBuild.getRetrofit().create(UserService.class);
        UserDTO userDTO=new UserDTO();
        Call<ResponseResult> call = service.edit(User.getUsername(),userDTO);

        Log.e("userDTO", userDTO.toString());

        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                Log.e("",result.toString());
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_LONG).show();
                if(result.getStatus()==201) {
                    Toast.makeText(context, "修改成功！", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "修改失败，请稍后重试", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
