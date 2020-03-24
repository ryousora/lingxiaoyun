package com.example.myapplication.action;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.myapplication.model.Cache;
import com.example.myapplication.model.UserDTO;
import com.example.myapplication.model.UserFileDTO;
import com.example.myapplication.model.UserFolderDTO;
import com.example.myapplication.netService.FolderService;
import com.example.myapplication.netService.reqbody.MoveReqBody;
import com.example.myapplication.netService.reqbody.RenameFileReqBody;
import com.example.myapplication.netService.reqbody.RenameFolderReqBody;
import com.example.myapplication.netService.reqbody.ShredReqBody;
import com.example.myapplication.utils.RequestBuild;
import com.example.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FolderRequest {

//    private static Gson gson=RequestBuild.getGson();

    private static Handler mHandler;

    public static void getHandler(Handler handler){
        mHandler=handler;
    }
    public static void getFolder(final Context context, String username, final String folderId, String sort){

        FolderService service = RequestBuild.getRetrofit().create(FolderService.class);
        Call<Map<String,Object>> call = service.getFolder(username,Integer.valueOf(folderId),Integer.valueOf(sort));
        Log.e("getFolder_________"+username,folderId);

        call.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                Map<String,Object> result = response.body();
                Log.e("",result.toString());
//                List<UserFolderDTO> folders= gson.fromJson(gson.toJson(result.get("folders")),new TypeToken<List<UserFolderDTO>>(){}.getType());
//                List<UserFileDTO> files= gson.fromJson(gson.toJson(result.get("files")),new TypeToken<List<UserFileDTO>>(){}.getType());
                List<UserFolderDTO> folders= JSON.parseArray(JSONObject.toJSONString(result.get("folders")),UserFolderDTO.class);
                List<UserFileDTO> files= JSON.parseArray(JSONObject.toJSONString(result.get("files")),UserFileDTO.class);
                Log.e("folder",folders.toString());
                Log.e("file",files.toString());
                /*if(Cache.getFolders(Integer.valueOf(folderId))!=null||Cache.getFiles(Integer.valueOf(folderId))!=null) {
                    Cache.delP(Integer.valueOf(folderId));
                }*/
                Cache.putFolders(Integer.valueOf(folderId), folders);
                Cache.putFiles(Integer.valueOf(folderId), files);
                Message message = Message.obtain();
                message.arg1 = 1;
                mHandler.sendMessage(message);/*
                homeViewModel.refurbish();

                homeViewModel.getParentId().observe(homeFragment, parentId -> {
                    if(Cache.getInParentId()!=null) {
                        homeFragment.listInit(parentId);
                    }
                    homeFragment.setListView();
                });*/
                }
            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void search(final Context context, String username, final String input){

        FolderService service = RequestBuild.getRetrofit().create(FolderService.class);
        Call<Map<String,Object>> call = service.search(username,input);

        call.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                Map<String,Object> result = response.body();
                Log.e("",result.toString());/*
                List<UserFolderDTO> folders= (List<UserFolderDTO>) result.get("folders");
                List<UserFileDTO> files= (List<UserFileDTO>) result.get("files");
                Cache.putFolders(0,folders);
                Cache.putFiles(0,files);*/
            }
            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void move(final Context context, String username, MoveReqBody reqBody){

        FolderService service = RequestBuild.getRetrofit().create(FolderService.class);
        Call<Map<String,Object>> call = service.move(username,reqBody);

        call.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                Map<String,Object> result = response.body();
                Log.e("move",result.toString());

                Message message = Message.obtain();
                message.arg1 = 0;
                mHandler.sendMessage(message);
            }
            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void renameFolder(final Context context, String username, int folderId , RenameFolderReqBody reqBody){

        FolderService service = RequestBuild.getRetrofit().create(FolderService.class);
        Call<UserFolderDTO> call = service.renameFolder(username,folderId,reqBody);

        call.enqueue(new Callback<UserFolderDTO>() {
            @Override
            public void onResponse(Call<UserFolderDTO> call, Response<UserFolderDTO> response) {
                UserFolderDTO result = response.body();
                Log.e("",result.toString());
                ToastUtils.showMessage(context,"文件夹重命名成功！");

                Message message = Message.obtain();
                message.arg1 = 0;
                mHandler.sendMessage(message);
            }
            @Override
            public void onFailure(Call<UserFolderDTO> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void renameFile(final Context context, String username, int fileId , RenameFileReqBody reqBody){

        FolderService service = RequestBuild.getRetrofit().create(FolderService.class);
        Call<UserFileDTO> call = service.renameFile(username,fileId,reqBody);

        call.enqueue(new Callback<UserFileDTO>() {
            @Override
            public void onResponse(Call<UserFileDTO> call, Response<UserFileDTO> response) {
                UserFileDTO result = response.body();
                Log.e("",result.toString());
                ToastUtils.showMessage(context,"文件重命名成功！");

                Message message = Message.obtain();
                message.arg1 = 0;
                mHandler.sendMessage(message);
            }
            @Override
            public void onFailure(Call<UserFileDTO> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void newFolder(final Context context, String username, UserFolderDTO reqBody){

        FolderService service = RequestBuild.getRetrofit().create(FolderService.class);
        Call<UserFolderDTO> call = service.newFolder(username,reqBody);

        call.enqueue(new Callback<UserFolderDTO>() {
            @Override
            public void onResponse(Call<UserFolderDTO> call, Response<UserFolderDTO> response) {
                UserFolderDTO result = response.body();
                Log.e("",result.toString());
                if(result.getFolderId()!=-1) {
                    Message message = Message.obtain();
                    message.arg1 = 0;
                    mHandler.sendMessage(message);
                    ToastUtils.showMessage(context, "创建成功！");
                }else
                    ToastUtils.showMessage(context, "命名重复！创建失败！");
            }
            @Override
            public void onFailure(Call<UserFolderDTO> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void delete(final Context context, String username, ShredReqBody reqBody){

        FolderService service = RequestBuild.getRetrofit().create(FolderService.class);
        Call<UserDTO> call = service.shred(username,reqBody);

        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                UserDTO result = response.body();
                Log.e("",result.toString());

                Toast.makeText(context, "删除成功！", Toast.LENGTH_LONG).show();

                Message message = Message.obtain();
                message.arg1 = 0;
                mHandler.sendMessage(message);
            }
            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
