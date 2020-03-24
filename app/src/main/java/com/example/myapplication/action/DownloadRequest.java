package com.example.myapplication.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.model.Cache;
import com.example.myapplication.model.Url;
import com.example.myapplication.model.UserFileDTO;
import com.example.myapplication.netService.FileService;
import com.example.myapplication.ui.download.DownloadFragment;
import com.example.myapplication.utils.RequestBuild;
import com.example.myapplication.model.User;
import com.example.myapplication.netService.FolderService;
import com.example.myapplication.utils.Constant;
import com.example.myapplication.utils.FileUtils;
import com.example.myapplication.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadRequest {


    public static void downloadFile(final Context context, String username, int fileId){
        FileService service = RequestBuild.getRetrofit().create(FileService.class);
        Call<UserFileDTO> call = service.getFileInfo(username,fileId);

        call.enqueue(new Callback<UserFileDTO>() {
            @Override
            public void onResponse(Call<UserFileDTO> call, Response<UserFileDTO> response) {
                UserFileDTO result = response.body();
                if(result==null){
                    ToastUtils.showMessage(context,"下载错误！");
                    return;
                }
                String url= Url.getUrl()+result.getFileUrl();
                DownloadFragment.addDownloadTask(result.getFileName()+"."+result.getFileType(),url);
                Log.e("11111",result.toString());
                ToastUtils.showMessage(context,"成功创建下载任务！");

            }
            @Override
            public void onFailure(Call<UserFileDTO> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void download(String username,Map<String,String> fileId) {
        FileService service = RequestBuild.getRetrofit().create(FileService.class);
        /*
        service.downloadFile(username, fileId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Bitmap bitmap = null;
                        byte[] bys;
                        try {
                            bys = responseBody.bytes();
                            bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.length);
                            try {
                                FileUtils.saveImg(bitmap, Constant.imgSavePath, String.valueOf(User.getUserId()));
                                String finSavePath = Constant.imgSavePath + File.separator + User.getUserId() + ".jpg";
                                Log.e("",finSavePath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //你的处理
                    }

                    @Override
                    public void onComplete() {
                        //你的处理
                    }
                });*/
    }

}
