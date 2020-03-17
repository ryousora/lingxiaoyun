package com.example.myapplication.ui.userinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;
import com.example.myapplication.action.FileRequest;
import com.example.myapplication.action.UserRequest;
import com.example.myapplication.model.Url;
import com.example.myapplication.model.User;
import com.example.myapplication.utils.Constant;
import com.example.myapplication.utils.GlideLoadEngine;
import com.example.myapplication.utils.ImageUtils;
import com.example.myapplication.utils.ToastUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import org.reactivestreams.Subscriber;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 账号信息详情页
 * Created by wudeng on 2017/8/31.
 */

public class UserInfoActivity extends FragmentActivity implements View.OnClickListener, View.OnTouchListener {

    private static final String TAG = UserInfoActivity.class.getSimpleName();

    @BindView(R.id.layout_head)
    RelativeLayout mLayoutHead;
    @BindView(R.id.iv_head_picture)
    ImageView mIvHead;
    @BindView(R.id.tv_account_userId)
    TextView mTvUserId;
    @BindView(R.id.et_account_username)
    EditText mEtUsername;
    @BindView(R.id.tv_account_sex)
    TextView mTvSex;
    @BindView(R.id.tv_account_email)
    TextView mTvEmail;
    @BindView(R.id.et_account_email)
    EditText mEtEmail;
    @BindView(R.id.iv_back_btn)
    ImageView mIvBack;
    @BindView(R.id.iv_menu_btn)
    ImageView mIvMenu;
    @BindView(R.id.btn_login_out)
    Button login_out;
    // 头像本地路径
    private String mHeadImgPath = "";
    // 获取图像请求码
    private static final int SELECT_PHOTO = 1;
    private static final int TAKE_PHOTO = 0;
    // 信息是否有被更新
    private boolean haveAccountChange = false;
    // 是否处于编辑状态
    private boolean isEditor=false;
    // 输入服务，用于显示键盘
    private InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_userinfo);
        /*UserRequest.check(this);*/
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);/*
        new Thread(new Runnable() {
            @Override
            public void run() {*/
                /*
            }}).run();*/
//        Log.e("gender", "" + User.getGender());

        showData();
        init();
/*
        Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/Download/cloud.png");
        Log.e(""+fileIsExists("/storage/emulated/0/Download/cloud.png"),"_______________");
        mIvHead.setImageBitmap(bitmap);*/
    }
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    private void selectPic()
    {/*
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .capture(true)  // 使用相机，和 captureStrategy 一起使用
                .captureStrategy(new CaptureStrategy(true, "com.example"))
//        R.style.Matisse_Zhihu (light mode)
//        R.style.Matisse_Dracula (dark mode)
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .maxSelectable(1)
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return new HashSet<MimeType>() {{
                            add(MimeType.PNG);
                        }};
                    }

                    @Override
                    public IncapableCause filter(Context context, Item item) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(item.getContentUri());
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeStream(inputStream, null, options);
                            int width = options.outWidth;
                            int height = options.outHeight;

//                            if (width >= 500)
//                                return new IncapableCause("宽度超过500px");

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                })
//                .gridExpectedSize((int) getResources().getDimension(R.dimen.imageSelectDimen))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.87f)
                .imageEngine(new GlideLoadEngine())
                .forResult(SELECT_PHOTO);*/
        Matisse.from(this).choose(MimeType.ofImage(), false)
                .countable(false)
                .maxSelectable(1)
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return new HashSet<MimeType>() {{
                            add(MimeType.PNG);
                        }};
                    }

                    @Override
                    public IncapableCause filter(Context context, Item item) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(item.getContentUri());
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeStream(inputStream, null, options);
                            int width = options.outWidth;
                            int height = options.outHeight;

                            if (width<20||height<20)
                                return new IncapableCause("图片过小！");

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        return null;
                    }
                })
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.87f)
                .imageEngine(new GlideLoadEngine())
                .forResult(1);
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==REQUEST_CODE_CHOOSE_PHOTO_ALBUM && resultCode ==RESULT_OK) {

            List mSelected = Matisse.obtainResult(data);

            Log.d("Matisse", "mSelected: " +mSelected);

        }

    }*/

    // 显示数据
    public void showData() {
        /*DownloadRequest.downloadImg(User.getIconimg_url(), User.getUserId());*/
        ImageUtils.setImageByUri(this, mIvHead,
                Url.getUrl()+User.getIconimg_url(), R.mipmap.bg_img_default);
        mTvUserId.setText(String.valueOf(User.getUserId()));
        mEtUsername.setText(User.getUsername());
        if (User.getGender() == 1) {
            mTvSex.setText("男");
        } else if (User.getGender() == 2) {
            mTvSex.setText("女");
        } else {
            mTvSex.setText("未知");
        }
        if (User.getEmail() != null) {
            mTvEmail.setText(User.getEmail());
            mEtEmail.setText(User.getEmail());
        }
        else mTvEmail.setText("未知");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 文字
        mLayoutHead.setOnClickListener(this);
        mTvSex.setOnClickListener(this);
        mTvEmail.setOnClickListener(this);

        // 标题栏
        mIvBack.setOnClickListener(this);
        mIvMenu.setOnClickListener(this);
        // 输入框
        mEtUsername.setOnTouchListener(this);

        // 结束编辑，相当于初始化为非编辑状态
        finishEdit();
        login_out.setOnClickListener(v -> {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
            builder.setMessage("确定退出登录？");
            builder.setTitle("提示");
            builder.setPositiveButton("确定", (dialog, which) -> {
                User.loginOut(this);
                dialog.dismiss();
            });
            builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_head:
                selectPic();
//                setHeadImg();
                break;
            case R.id.tv_account_sex:
                setSex();
                break;
            case R.id.tv_account_email:
                mTvEmail.setVisibility(View.GONE);
                mEtEmail.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_back_btn:
                this.finish();
                break;
            case R.id.iv_menu_btn:
                if (isEditor) {
                    finishEdit();
                } else {
                    startEdit();
                }
                break;
        }
    }

    // EditText 获取焦点并将光标移动到末尾
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isEditor) {
            if (v.getId() == R.id.et_account_username) {
                mEtUsername.requestFocus();
                mEtUsername.setSelection(mEtUsername.getText().length());
                mInputMethodManager.showSoftInput(mEtUsername, 0);
            }
            else if (v.getId() == R.id.et_account_email) {
                mEtEmail.requestFocus();
                mEtEmail.setSelection(mEtEmail.getText().length());
                mInputMethodManager.showSoftInput(mEtEmail, 0);
            }
            return true;
        }
        return false;
    }

    /**
     * 启动编辑
     */
    private void startEdit() {
        mIvMenu.setImageResource(R.mipmap.done);
        // 可点击
        mLayoutHead.setClickable(true);
        mTvSex.setClickable(true);
        mTvEmail.setClickable(true);
        mEtUsername.setClickable(true);

        isEditor = true;
    }

    /**
     * 结束编辑，判断是否有修改，决定是否同步缓存数据
     */
    private void finishEdit() {

        if (!mEtEmail.getText().toString()
                .equals(User.getEmail())) {
            User.setEmail(mEtEmail.getText().toString());
            haveAccountChange = true;
        }
        mTvEmail.setVisibility(View.VISIBLE);
        mTvEmail.setText(User.getEmail());
        mEtEmail.setVisibility(View.GONE);

        if (!mEtUsername.getText().toString()
                .equals(User.getUsername())) {
            User.setUsername(mEtUsername.getText().toString());
            haveAccountChange = true;
        }

        if (haveAccountChange) {
            User.setUsername(mEtUsername.getText().toString());

            // 将数据更新到服务器
            UserRequest.edit(this);
            // 将数据更新到缓存
            User.saveSP(this);

            haveAccountChange = false;
        }

        mIvMenu.setImageResource(R.mipmap.editor);
        // 不可点击
        mLayoutHead.setClickable(false);
        mTvSex.setClickable(false);
        mTvEmail.setClickable(false);
        // 不可编辑
        mEtUsername.setFocusable(false);
        mEtUsername.setFocusableInTouchMode(false);

        isEditor = false;
    }

    /**
     * 设置性别
     */
    private void setSex(){
        final int[] selected = new int[1];
        if (User.getGender() != 1) {
            if (User.getGender() == 2) {
                selected[0] = 1;//女
            } else {
                selected[0] = 2;//未知
            }
        }  //默认为男

        final String[] items = new String[]{"男", "女", "未知"};
        new AlertDialog.Builder(this)
                .setTitle("性别")
                .setSingleChoiceItems(items, selected[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which != selected[0]) {
                            if (which == 0) {
                                User.setGender(1);
                                mTvSex.setText("男");
                            } else if (which == 1) {
                                User.setGender(2);
                                mTvSex.setText("女");
                            } else {
                                User.setGender(3);
                                mTvSex.setText("未知");
                            }
                            haveAccountChange = true;
                        }
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /**
     * 设置头像，拍照或选择照片
     */
    private void setHeadImg() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_set_head_img, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        TextView take = view.findViewById(R.id.tv_take_photo);
        TextView select = view.findViewById(R.id.tv_select_img);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mHeadImgPath = Constant.cachePath + File.separator + "img"
                            + File.separator + User.getUserId() + ".jpg";
                    Uri uri = Uri.fromFile(new File(mHeadImgPath));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, TAKE_PHOTO);
                } catch (Exception e) {
                    ToastUtils.showMessage(UserInfoActivity.this, "启动相机出错！请重试");
                    e.printStackTrace();
                }

            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                /*intent.setType("image/*");*/
                startActivityForResult(Intent.createChooser(intent, "选择头像图片"), SELECT_PHOTO);
            }
        });
        alertDialog.show();
    }

/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("","requestCode:"+requestCode+"...resultCode:"+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                dealTakePhotoResult();
            } else if (requestCode == SELECT_PHOTO) {
                mHeadImgPath = ImageUtils.getFilePathFromUri(UserInfoActivity.this, data.getData());
                Log.e("imgpath",mHeadImgPath);
                dealTakePhotoResult();
            }
        }
    }
*/
@SuppressLint("CheckResult")
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
        if (resultCode == RESULT_OK) {
            mHeadImgPath = Matisse.obtainPathResult(data).get(0);
            if (mHeadImgPath != null) {

                Observable.just(mHeadImgPath)
                        .map(new Function<String, Bitmap>() {
                            @Override
                            public Bitmap apply(String s) throws Exception {
                                return ImageUtils.getBitmapFromFile(mHeadImgPath, 500, 500);
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Bitmap>() {
                            @Override
                            public void accept(Bitmap bitmap) throws Exception {
                                uploadHeadImg(bitmap);
                                Glide.with(UserInfoActivity.this)
                                        .asBitmap() // some .jpeg files are actually gif
                                        .load(mHeadImgPath)
                                        .apply(new RequestOptions() {{
                                            override(Target.SIZE_ORIGINAL);
                                        }})
                                        .into(mIvHead);
                            }
                        });
/*
                Bitmap bitmap = ImageUtils.getBitmapFromFile(mHeadImgPath, 500, 500);
                uploadHeadImg(bitmap);
                Glide.with(this)
                        .asBitmap() // some .jpeg files are actually gif
                        .load(mHeadImgPath)
                        .apply(new RequestOptions() {{
                            override(Target.SIZE_ORIGINAL);
                        }})
                        .into(mIvHead);*/
            } else
                Toast.makeText(this, "uri为null", Toast.LENGTH_SHORT).show();
        }
    }
}
    /**
     * 处理拍照回传数据
     *//*
    private void dealTakePhotoResult() {
        Flowable.just(mHeadImgPath)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String path) throws Exception {
                        // 调整旋转角度，压缩
                        int bitmapDegree = ImageUtils.getBitmapDegree(mHeadImgPath);
                        Bitmap bitmap = ImageUtils.getBitmapFromFile(mHeadImgPath, 500, 500);
                        Log.e("bitmap", bitmap != null ? bitmap.toString() : "bitmap-----null!!!");
                        bitmap = ImageUtils.rotateBitmapByDegree(bitmap, bitmapDegree);
                        ImageUtils.saveBitmap2Jpg(bitmap, path);
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) {
                        // 显示，记录更新，同步至服务器
                        if (bitmap != null) {
                            // 上传至服务器
                            uploadHeadImg(bitmap);
                        }
                    }
                });
    }
*/
    /**
     * 将头像数据上传至服务器存储，并保存到本地
     */
    private void uploadHeadImg(final Bitmap bitmap) {
        String filePath=Constant.imgSavePath+User.getUserId()+".jpg";
        File file=new File(filePath);//将要保存图片的路径
        Log.e(file.getPath(),""+file.exists());
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        FileRequest.uploadImg(this,filePath,mIvHead,bitmap);
        haveAccountChange=true;
    }

}
