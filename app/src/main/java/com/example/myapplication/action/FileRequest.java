package com.example.myapplication.action;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.model.Url;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserFileDTO;
import com.example.myapplication.netService.reqbody.UploadReqBody;
import com.example.myapplication.ui.download.DownloadFragment;
import com.example.myapplication.utils.FileUtils;
import com.example.myapplication.utils.RequestBuild;
import com.example.myapplication.model.ResponseResult;
import com.example.myapplication.netService.FileService;
import com.example.myapplication.utils.ToastUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FileRequest {

    private static Handler mHandler;

    public static void getHandler(Handler handler){
        mHandler=handler;
    }

    public static void uploadImg(final Context context, String filePath, final ImageView mIvHead,final Bitmap bitmap) {
        File imgFile = new File(filePath);

        Log.e("img_upload-----------" + filePath, imgFile.toString());

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
        //RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), imgFile);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用file
        builder.addFormDataPart("file", imgFile.getName(), requestFile);
        List<MultipartBody.Part> parts = builder.build().parts();
        FileService service = RequestBuild.getRetrofit().create(FileService.class);

        service.uploadImg(parts).enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                Map data = result.getData();
                Log.e("", result.toString());
                if (result.getStatus() == 201) {
                    Log.e("url______",(String)data.get("url"));
                    User.setIconimg_url((String) data.get("url"));
                    ToastUtils.showMessage(context, "上传成功！");
                    mIvHead.setImageBitmap(bitmap);
                } else
                    ToastUtils.showMessage(context, "上传出错！请重试");
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                ToastUtils.showMessage(context, "上传出错！请重试");
            }
        });
    }
    public static void uploadFile(final Context context,String username, String filePath,Integer parentId) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file_other", file.getName(), requestFile);

        Log.e("file_upload-----------" + filePath, file.toString());

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("file", file.getName(), requestFile);
        List<MultipartBody.Part> parts = builder.build().parts();
        FileService service = RequestBuild.getRetrofit().create(FileService.class);

        UploadReqBody reqBody=new UploadReqBody(User.getUserId(), FileUtils.getFileMD5(file),FileUtils.getFileName(file.getName()),FileUtils.getFileType(filePath),parentId);
        Log.e("", reqBody.toString());

        service.uploadFile(username,parts,reqBody.toMap()).enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                ResponseResult result = response.body();
                Map data = result.getData();
                Log.e("", result.toString());

                Message message = Message.obtain();
                message.arg1 = 0;
                mHandler.sendMessage(message);

                if (result.getStatus() == 201) {
                    Log.e("url______",(String)data.get("url"));
//                    User.setIconimg_url((String) data.get("url"));
                    ToastUtils.showMessage(context, "上传成功！");
                } else if (result.getStatus() == 202) {
                    Log.e("url______",(String)data.get("url"));
                    ToastUtils.showMessage(context, "服务器已存在资源，秒传成功！");
                } else
                    ToastUtils.showMessage(context, "上传出错！请重试");
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                ToastUtils.showMessage(context, "上传出错！请重试");
            }
        });
    }
    public static void getFileUrl(final Context context, String username, int fileId) {

        FileService service = RequestBuild.getRetrofit().create(FileService.class);
        Call<UserFileDTO> call = service.getFileInfo(username,fileId);

        call.enqueue(new Callback<UserFileDTO>() {
            @Override
            public void onResponse(Call<UserFileDTO> call, Response<UserFileDTO> response) {
                UserFileDTO result = response.body();
                if(result==null){
                    ToastUtils.showMessage(context,"信息出错！请重试！");
                    return;
                }
                String url= Url.getUrl()+result.getFileUrl();

                ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", url);
                clipboard.setPrimaryClip(clip);

                ToastUtils.showMessage(context,"成功复制下载链接！");

            }
            @Override
            public void onFailure(Call<UserFileDTO> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}