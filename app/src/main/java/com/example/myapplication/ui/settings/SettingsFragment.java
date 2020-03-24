package com.example.myapplication.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.action.FolderRequest;
import com.example.myapplication.model.Cache;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserFolderDTO;
import com.example.myapplication.utils.MSP;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.FilePicker;
import cn.qqtheme.framework.util.StorageUtils;

public class SettingsFragment extends Fragment {

    private View root;

    @BindView(R.id.download_path)
    RelativeLayout download_path;
    @BindView(R.id.tv_download_path)
    TextView tv_download_path;
    @BindView(R.id.download_max)
    RelativeLayout download_max;
    @BindView(R.id.tv_download_max)
    TextView tv_download_max;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_settings, container, false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this, root);

        tv_download_path.setText(Cache.getDownloadPath());
        tv_download_max.setText(Cache.getMax_download());

        download_path.setOnClickListener(v -> {
            onDirPicker(root);
            MSP.setDownloadPath(Cache.getDownloadPath(),getContext());
        });

        download_max.setOnClickListener(view -> {
            final EditText et = new EditText(getContext());
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            new AlertDialog.Builder(getContext()).setTitle("最多同时下载文件数（1-9）").setView(et).setPositiveButton("确定", (dialog, which)
                    -> tv_download_max.setText("0".equals(et.getText().toString())?"1":et.getText().toString())).setNegativeButton("取消",null).create().show();
        });

        return root;
    }
    private void onDirPicker(View view) {
        FilePicker picker = new FilePicker(Objects.requireNonNull(this.getActivity()), FilePicker.DIRECTORY);
        picker.setRootPath(Cache.getDownloadPath());//(StorageUtils.getExternalRootPath() + "Download/");
        picker.setItemHeight(30);
        picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            @Override
            public void onFilePicked(String currentPath) {
                currentPath=currentPath+"/";
                Cache.setDownloadPath(currentPath);
                tv_download_path.setText(Cache.getDownloadPath());
            }
        });
        picker.show();
    }
    @Override
    public void onStop() {
        MSP.setDownloadPath(Cache.getDownloadPath(),getContext());
        String max_download = (String)tv_download_max.getText();
        /*if("0".equals(max_download)){
            max_download="1";
        }*/
        Cache.setMax_download(max_download);
        MSP.setDownload_max(max_download,getContext());

        super.onStop();
    }
}