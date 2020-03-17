package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.action.UserRequest;
import com.example.myapplication.model.Cache;
import com.example.myapplication.model.Url;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.ui.userinfo.UserInfoActivity;
import com.example.myapplication.utils.Constant;
import com.example.myapplication.utils.ImageUtils;
import com.example.myapplication.utils.MSP;
import com.example.myapplication.utils.PermissionUtils;
import com.example.myapplication.utils.RequestBuild;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int PERMISSION_REQUEST_CODE = 100001;

    private void initPermission() {
        boolean has = PermissionUtils.checkPermissions(this, BASIC_PERMISSIONS);
        if (!has) {
            PermissionUtils.requestPermissions(this, PERMISSION_REQUEST_CODE,
                    BASIC_PERMISSIONS);
        }
    }

    /*
    private  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public  void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            int REQUEST_EXTERNAL_STORAGE = 1;
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        RequestBuild.RequestInit();
        Url.initUrl(MainActivity.this);
        Cache.init();
        Cache.setDownloadPath(MSP.getDownloadPath(MainActivity.this));
        userLogin();
        /*
        File filePath = new File(Constant.fileSavePath + File.separator);
        Log.e("++++++++++++++++++++++" + filePath.mkdirs() + "=====" + filePath.getPath(), filePath.toString());
        try {
            filePath = new File(filePath + File.separator + "1.txt");
            Log.e("+++++++++++" + filePath.createNewFile() + "+++++++++++" + filePath.mkdirs() + "=====" + filePath.getPath(), filePath.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void userLogin(){/*
        User.setUserId(MSP.getId(MainActivity.this));
        User.setUsername(MSP.getUsername(MainActivity.this));*/
        if(User.getToken()==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        ImageView mIvHead = findViewById(R.id.mIvHead);
        mIvHead.setOnClickListener(new ImageView.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent intent;
                                             Log.e("", User.toStr());

                                             if (User.getToken() != null)
                                                 intent = new Intent((MainActivity.this), UserInfoActivity.class);
                                             else
                                                 intent = new Intent((MainActivity.this), LoginActivity.class);

                                             startActivity(intent);
                                         }
                                     }
        );
        initPermission();
        return true;
    }

    @Override
    public void  onRestart() {
        super.onRestart();
        ImageView mIvHead = findViewById(R.id.mIvHead);
        String filePath=Url.getUrl()+User.getIconimg_url();//Constant.imgSavePath+User.getUserId()+".jpg";
        File file=new File(filePath);
        Log.e(mIvHead.toString(),"________________________________"+file.exists());
        ImageUtils.setImageByFile(this, mIvHead,
                filePath, R.mipmap.ic_launcher_round);
        TextView mTvHead= findViewById(R.id.mTvHead);
        mTvHead.setText(User.getUsername());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Url.initUrl(MainActivity.this);
            LayoutInflater factory = LayoutInflater.from(MainActivity.this);
            @SuppressLint("InflateParams") final View view = factory.inflate(R.layout.ip_setting, null);
            final EditText ip_edit = view.findViewById(R.id.ip);
            final EditText port_edit = view.findViewById(R.id.port);
            ip_edit.setHint("ip");
            ip_edit.setText(Url.getIp());
            port_edit.setHint("port");
            port_edit.setText(Url.getPort());
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("请输入服务器地址")
                    .setView(view)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String ip=ip_edit.getText().toString();
                                    String port=port_edit.getText().toString();
                                    Log.e("",ip+"::::::::::"+port);
                                    new Url(ip,port);
                                    Url.saveUrl(MainActivity.this);
                                }
                            }).setNegativeButton("取消", null).create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
