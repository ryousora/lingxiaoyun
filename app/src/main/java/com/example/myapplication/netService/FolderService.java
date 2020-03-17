package com.example.myapplication.netService;


import com.example.myapplication.model.ResponseResult;
import com.example.myapplication.model.UserDTO;
import com.example.myapplication.model.UserFileDTO;
import com.example.myapplication.model.UserFolderDTO;
import com.example.myapplication.netService.reqbody.FolderReqBody;
import com.example.myapplication.netService.reqbody.MoveReqBody;
import com.example.myapplication.netService.reqbody.RenameFileReqBody;
import com.example.myapplication.netService.reqbody.RenameFolderReqBody;
import com.example.myapplication.netService.reqbody.ShredReqBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FolderService {

    @GET("/user/{username}/disk/folders/{folderId}")
    Call<Map<String,Object>> getFolder(@Path("username") String username, @Path("folderId") Integer folderId, @Query("sort") int sort);

    @GET("/user/{username}/disk/search")
    Call<Map<String,Object>> search(@Path("username") String username,@Query("input") String input);

    @PATCH("/user/{username}/disk/move")
    Call<Map<String,Object>> move(@Path("username") String username,@Body MoveReqBody reqBody);

    @PATCH("/user/{username}/disk/folders/{folderId}")
    Call<UserFolderDTO> renameFolder(@Path("username") String username, @Path("folderId") int folderId, @Body RenameFolderReqBody reqBody);

    @PATCH("/user/{username}/disk/files/{fileId}")
    Call<UserFileDTO> renameFile(@Path("username") String username, @Path("fileId") int fileId, @Body RenameFileReqBody reqBody);

    @POST("/user/{username}/disk/folders")
    Call<UserFolderDTO> newFolder(@Path("username") String username, @Body UserFolderDTO reqBody);

//    @DELETE("/user/{username}/recycle")
    @HTTP(method = "DELETE",path = "/user/{username}/recycle",hasBody = true)
    Call<UserDTO> shred(@Path("username") String username, @Body ShredReqBody reqBody);

}
