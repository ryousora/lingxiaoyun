package com.example.myapplication.action;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.myapplication.netService.FileService;
import com.example.myapplication.utils.RequestBuild;
import com.example.myapplication.model.User;
import com.example.myapplication.netService.FolderService;
import com.example.myapplication.utils.Constant;
import com.example.myapplication.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DownloadRequest {

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
