package com.example.myapplication.ui.files;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.action.FileRequest;
import com.example.myapplication.action.FolderRequest;
import com.example.myapplication.model.Cache;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserFileDTO;
import com.example.myapplication.model.UserFolderDTO;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.qqtheme.framework.picker.FilePicker;
import cn.qqtheme.framework.util.StorageUtils;

public class FilesFragment extends Fragment {

//    private HomeViewModel homeViewModel;

    private View root;

    private boolean isGetData = false;

    private List<Map<String,Object>> dataLists = new ArrayList<>();

    private Handler mHandler;

    private RecyclerView recyclerView;

    public static int setPosition =-1;


    /*public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_files, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getParentId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }*/

    @Override
    public void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cache.init();
        Cache.getInParentId().push(0);

//        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        mHandler = new Handler(msg -> {
//            Log.e("","handler获取到message!!!!!!!!!!!!!!!!!!!!!!!!!");
            switch (msg.arg1) {
                case 0:
                    FolderRequest.getFolder(this.getContext(), User.getUsername(), String.valueOf(Cache.getInParentId().peek()), "0");
                    /*listInit(Cache.getInParentId().peek());
                    setListView();*/
                    break;
                case 1:
                    listInit(Cache.getInParentId().peek());
                    setListView();
                    Log.e("setPosition", setPosition +"");
                    if(setPosition !=-1){
                        recyclerView.scrollToPosition(setPosition);
                        setPosition =-1;
                    }
                    break;
                default:
                    break;
            }
            return true;
        });

        FolderRequest.getHandler(mHandler);
        FileRequest.getHandler(mHandler);
/*
        if(User.getUserId()!=null&&User.getToken()!=null) {
            FolderRequest.getFolder(this.getContext(), User.getUsername(), String.valueOf(Cache.getInParentId().peek()), "0", homeViewModel, this);
        }*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_files, container, false);

        final FloatingActionButton actionA = root.findViewById(R.id.fab_folder);
        actionA.setOnClickListener(view -> {
            final EditText et = new EditText(getContext());
            new AlertDialog.Builder(getContext()).setTitle("新建文件夹").setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FolderRequest.newFolder(getContext(),User.getUsername(),new UserFolderDTO(User.getUserId(),Cache.getInParentId().peek(),et.getText().toString()));
                }
            }).setNegativeButton("取消",null).create().show();
        });
        final FloatingActionButton actionB = root.findViewById(R.id.fab_file);
        actionB.setOnClickListener(view -> {
            //选择并上传文件
            pickFile();
        });

        /*
        if(User.getUserId()!=null&&Cache.getFolders(User.getUserId())!=null) {
            List<String> foldersName = Cache.getFolders(User.getUserId()).stream().map(UserFolderDTO::getFolderName).collect(Collectors.toList());
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), android.R.layout.simple_list_item_1, foldersName);
            ListView listView = root.findViewById(R.id.mLvFolders);
            listView.setAdapter(arrayAdapter);
        }*/

        return root;
    }

    public void pickFile() {
        FilePicker picker = new FilePicker(Objects.requireNonNull(getActivity()), FilePicker.FILE);
        picker.setShowHideDir(false);
        picker.setRootPath(StorageUtils.getExternalRootPath());//getExternalPrivatePath(getActivity()));
        //picker.setAllowExtensions(new String[]{".apk"});
        picker.setOnFilePickListener(currentPath ->
                FileRequest.uploadFile(getContext(),User.getUsername(), currentPath, Cache.getInParentId().peek()));
        picker.show();
    }

    /*@Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            if(User.getUserId()!=null&&User.getToken()!=null) {
                FolderRequest.getFolder(this.getContext(), User.getUsername(), String.valueOf(Cache.getInParentId().peek()), "0", homeViewModel, this);
            }
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }*/
    @Override
    public void onResume() {
        if (!isGetData) {
            if(User.getUserId()!=null&&User.getToken()!=null) {
                FolderRequest.getFolder(this.getContext(), User.getUsername(), String.valueOf(Cache.getInParentId().peek()), "0");
                isGetData = true;
            }
        }
        super.onResume();
    }

    public void listInit(Integer parentId){

        dataLists.clear();

        List<String> lName=new ArrayList<>();
        List<String> lType=new ArrayList<>();
        List<String> lDate=new ArrayList<>();
        List<Integer> lImageViews=new ArrayList<>();

        List<UserFolderDTO> folderDTOS=Cache.getFolders(parentId);
        if(folderDTOS.size()!=0) {
            Log.e("00000000000000000000", folderDTOS.get(0).toString());
            for (UserFolderDTO folderDTO : folderDTOS) {
                lName.add(folderDTO.getFolderName());
                lType.add("");
                lDate.add(folderDTO.getCreateTime().toString());
                lImageViews.add(R.mipmap.folder);
            }
        }
        List<UserFileDTO> fileDTOS=Cache.getFiles(parentId);
        if(fileDTOS.size()!=0) {
            Log.e("00000000000000000000", fileDTOS.get(0).toString());
            for (UserFileDTO fileDTO : fileDTOS) {
                lName.add(fileDTO.getFileName());
                lType.add("."+fileDTO.getFileType());
                lDate.add(fileDTO.getCreateTime().toString());
                lImageViews.add(R.mipmap.file);
            }
        }

        String[] name;
        String[] type;
        String[] data;
        int[] imageViews;
        /*theme=new String[lTheme.size()];
        content=new String[lContent.size()];
        imageViews=new int[lImageViews.size()];*/
        name=lName.toArray(new String[0]);
        type=lType.toArray(new String[0]);
        for(int i=0;i<name.length;i++){
            name[i]=name[i]+type[i];
        }
        data=lDate.toArray(new String[0]);
        imageViews=lImageViews.stream().mapToInt(Integer::intValue).toArray();

        Log.e("listview","name"+ Arrays.toString(name) +"date"+ Arrays.toString(data) +"imageview"+ Arrays.toString(imageViews));

        if(Cache.getInParentId().peek()!=0){
            Map<String, Object> map = new HashMap<>();
            map.put("fileImage", R.mipmap.back);
            map.put("fileName", "返回上一级目录");
            map.put("fileDate", "");
            dataLists.add(map);
        }
        for(int i=0;i<name.length;i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("fileImage", imageViews[i]);
            map.put("fileName", name[i]);
            map.put("fileDate", data[i]);
            dataLists.add(map);
        }
    }

    public void setListView(){
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(this.getContext()), android.R.layout.simple_list_item_1, list);
        ListView listView = root.findViewById(R.id.mLvFolders);
        listView.setAdapter(arrayAdapter);*/
        /*SimpleAdapter adapter=new SimpleAdapter(this.getContext(), dataLists,R.layout.item_files,
                new String[]{"fileImage","fileName","fileDate"},new int[]{R.id.fileImage,R.id.fileName,R.id.fileDate});*/
        recyclerView = root.findViewById(R.id.mLvFolders);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getContext(),dataLists,mHandler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        /*
        RecyclerView.setAdapter(adapter);
        recyclerView.setOnItemClickListener((parent, view, setPosition, id) -> {
            if(Cache.getInParentId().peek()!=0) {
                setPosition--;
                if (setPosition == -1) {
                    FolderRequest.getFolder(getContext(), User.getUsername(), String.valueOf(Cache.getInParentId().pop()), "0", homeViewModel, FilesFragment.this);
                    return;
                }
            }
            List<UserFolderDTO> folders=Cache.getFolders(Cache.getInParentId().peek());
            if(setPosition<folders.size()){
                Cache.getInParentId().push(folders.get(setPosition).getFolderId());
                Log.e("",setPosition+".........."+folders.get(setPosition).toString());
                FolderRequest.getFolder(getContext(), User.getUsername(), String.valueOf(Cache.getInParentId().peek()),"0" , homeViewModel, FilesFragment.this);
            }else{
                List<UserFileDTO> files=Cache.getFiles(Cache.getInParentId().peek());
                setPosition-=folders.size();
                Log.e("",setPosition+".........."+files.get(setPosition).toString());
            }
        });*/
    }
}