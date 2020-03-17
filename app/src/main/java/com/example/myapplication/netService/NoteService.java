package com.example.myapplication.netService;

import com.example.myapplication.model.Note;
import com.example.myapplication.model.ResponseResult;
import com.example.myapplication.netService.reqbody.LoginReqBody;
import com.example.myapplication.netService.reqbody.NoteInsertReqBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NoteService {

    @POST("user/{username}/notes/insert")
    Call<ResponseResult> insertNote(@Path("username") String username,@Body NoteInsertReqBody reqBody);

    @GET("user/{username}/notes/{id}")
    Call<ResponseResult> getNote(@Path("username") String username,@Path("id") int id);

    @POST("user/{username}/notes/{id}/edit")
    Call<ResponseResult> editNote(@Path("username") String username,@Path("id") int id,@Body String title,@Body String content);

    @GET("user/{username}/notes")
    Call<ResponseResult<List<Note>>> getNoteList(@Path("username") String username);
}
