package com.example.myapplication.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.model.Cache;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_settings, container, false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this, root);

        tv_download_path.setText(Cache.getDownloadPath());

        download_path.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onDirPicker(root);
            }
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
                Cache.setDownloadPath(currentPath);
                tv_download_path.setText(Cache.getDownloadPath());
            }
        });
        picker.show();
    }
}