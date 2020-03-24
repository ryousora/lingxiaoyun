package com.example.myapplication.netService;

import com.example.myapplication.model.ResponseResult;
import com.example.myapplication.model.UserFileDTO;
import com.example.myapplication.netService.reqbody.RenameFileReqBody;
import com.example.myapplication.netService.reqbody.UploadReqBody;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface FileService {

    @Multipart
    @POST("image/upload")
    Call<ResponseResult> uploadImg(@Part List<MultipartBody.Part> imgFile);

    @Multipart
    @POST("/users/{username}/disk/files")
    Call<ResponseResult> uploadFile(@Path("username") String username, @Part List<MultipartBody.Part> file,@PartMap Map<String,String> reqBody);

    @DELETE("/users/{username}/disk/files")
    Call<ResponseResult> uploadCancel(@Path("username") String username,@Query("cancel") String md5);

    @GET("/users/{username}/disk/files")
    Call<ResponseResult> uploadResume(@Path("username") String username,@Query("resume") String md5);

    @GET("/users/{username}/disk/files")
    Call<ResponseResult> downloadFile(@Path("username") String username,@QueryMap Map<String, String> fileId);

    @GET("/user/{username}/disk/files/{fileId}")
    Call<UserFileDTO> getFileInfo(@Path("username") String username, @Path("fileId") int fileId);

}
