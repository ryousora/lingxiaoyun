package com.example.myapplication.action;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.model.Cache;
import com.example.myapplication.model.Note;
import com.example.myapplication.model.ResponseResult;
import com.example.myapplication.netService.NoteService;
import com.example.myapplication.netService.reqbody.NoteInsertReqBody;
import com.example.myapplication.utils.RequestBuild;

import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteRequesr {

    public static void insertNote(final Context context, String username, NoteInsertReqBody reqBody){

        NoteService service = RequestBuild.getRetrofit().create(NoteService.class);
        Call<ResponseResult> call = service.insertNote(username,reqBody);

        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                Log.e("",result.toString());
                if(result.getStatus()==201){
                    Toast.makeText(context, "发布成功！", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getNote(final Context context, String username, int id){

        NoteService service = RequestBuild.getRetrofit().create(NoteService.class);
        Call<ResponseResult> call = service.getNote(username,id);

        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                Log.e("",result.toString());
                if(result.getStatus()==201){
                    Map data=result.getData();
                    Note note=new Note((Integer)data.get("id"),
                            (Integer)data.get("parentId"),
                            (Integer)data.get("userId"),
                            (String)data.get("title"),
                            (String)data.get("content"),
                            (Integer)data.get("imageId"),
                            (Integer)data.get("thumb_up"),
                            (Integer)data.get("thumb_down"),
                            (Boolean)data.get("isDelete"),
                            (Date)data.get("createTime"),
                            (Date)data.get("updateTime"));
                    Cache.putNote((Integer)data.get("id"),note);
                }
            }
            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void editNote(final Context context, String username, int id,String title,String content){

        NoteService service = RequestBuild.getRetrofit().create(NoteService.class);
        Call<ResponseResult> call = service.editNote(username,id,title,content);

        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                Log.e("",result.toString());
                if(result.getStatus()==201){
                    Toast.makeText(context, "修改成功！", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getNoteList(final Context context, String username){

        NoteService service = RequestBuild.getRetrofit().create(NoteService.class);
        Call<ResponseResult<List<Note>>> call = service.getNoteList(username);

        call.enqueue(new Callback<ResponseResult<List<Note>>>() {
            @Override
            public void onResponse(Call<ResponseResult<List<Note>>> call, Response<ResponseResult<List<Note>>> response) {
                ResponseResult<List<Note>> result = response.body();
                Log.e("",result.toString());
                if (result.getStatus()==201){
                    Cache.putUserNote(result.getData2());
                }
            }
            @Override
            public void onFailure(Call<ResponseResult<List<Note>>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
