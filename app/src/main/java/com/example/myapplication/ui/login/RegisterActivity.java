package com.example.myapplication.ui.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.R;
import com.example.myapplication.action.UserRequest;
import com.example.myapplication.utils.RequestBuild;
import com.example.myapplication.model.ResponseResult;
import com.example.myapplication.model.User;
import com.example.myapplication.netService.UserService;

import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends FragmentActivity implements View.OnClickListener, KeyboardWatcher.SoftKeyboardStateListener {
    private DrawableTextView logo;
    private EditText et_username1;
    private TextView tip_used;
    private EditText et_password1;
    private  EditText et_password2;
    private ImageView iv_clean_username1;
    private Button btn_regist;

    private int screenHeight = 0;//屏幕高度
    private float scale = 0.8f; //logo缩放比例
    private View body1;
    private KeyboardWatcher keyboardWatcher;

    private long perTim=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();

        keyboardWatcher = new KeyboardWatcher(findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);

    }
    private void initView() {
        logo = findViewById(R.id.logo);
        et_username1 = findViewById(R.id.et_username1);
        tip_used=findViewById(R.id.tv_tip_used);
        et_password1 = findViewById(R.id.et_password1);
        et_password2=findViewById(R.id.et_password2);
        iv_clean_username1 = findViewById(R.id.iv_clean_username1);
        btn_regist = findViewById(R.id.btn_regist);
        body1 = findViewById(R.id.body1);
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        findViewById(R.id.close1).setOnClickListener(this);
    }

    private void initListener() {
        iv_clean_username1.setOnClickListener(this);
        et_username1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_username1.getVisibility() == View.GONE) {
                    iv_clean_username1.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_username1.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(RegisterActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    et_username1.setSelection(s.length());
                }
                long curTim=new Date().getTime();
                if(curTim-perTim>1000) {//隔1秒检测节约资源
                    perTim=curTim;
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            String username = et_username1.getText().toString();
                            Log.e("",username);
                            if (username.length() > 0) {
                                UserService service = RequestBuild.getRetrofit().create(UserService.class);
                                Call<ResponseResult> call = service.checkName(username);
                                call.enqueue(new Callback<ResponseResult>() {
                                    @Override
                                    public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                                        ResponseResult result = response.body();
                                        Map data = result.getData();
                                        Log.e("", result.toString());
                                        if (result.getStatus() == 200) {
                                            tip_used.setVisibility(View.GONE);
                                        } else tip_used.setVisibility(View.VISIBLE);
                                    }
                                    @Override
                                    public void onFailure(Call<ResponseResult> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else tip_used.setVisibility(View.GONE);
                        }
                    }, 1000);//延迟1秒检测避免漏判
                }
            }
        });
        et_password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(RegisterActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    et_password1.setSelection(s.length());
                }
            }
        });

        et_password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(RegisterActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    et_password2.setSelection(s.length());
                }
            }
        });
        btn_regist.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v) {
                                             if(!et_password1.getText().toString().equals(et_password2.getText().toString())){
                                                 Toast.makeText(RegisterActivity.this, "两次输入的密码不匹配", Toast.LENGTH_SHORT).show();
                                                 et_password1.setText("");
                                                 et_password2.setText("");
                                                 return;
                                             }
                                             String username=et_username1.getText().toString();
                                             String password=et_password1.getText().toString();
                                             new User(username,password);
                                             UserRequest.register(RegisterActivity.this);
                                         }
                                     }
        );
    }

    /**
     * 缩小
     *
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX).with(mAnimatorScaleY);

        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();

    }

    /**
     * f放大
     *
     * @param view
     */
    public void zoomOut(final View view) {
        if (view.getTranslationY()==0){
            return;
        }
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();

    }
    private boolean flag = false;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_username1:
                et_username1.setText("");
                break;
            case R.id.close1:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardWatcher.removeSoftKeyboardStateListener(this);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardSize) {
        int[] location = new int[2];
        body1.getLocationOnScreen(location); //获取body在屏幕中的坐标,控件左上角
        int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y+ body1.getHeight()) ;
        if (keyboardSize > bottom){
            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body1, "translationY", 0.0f, -(keyboardSize - bottom));
            mAnimatorTranslateY.setDuration(300);
            mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorTranslateY.start();
            zoomIn(logo, keyboardSize - bottom);

        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body1, "translationY", body1.getTranslationY(), 0);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorTranslateY.start();
        zoomOut(logo);
    }

}
