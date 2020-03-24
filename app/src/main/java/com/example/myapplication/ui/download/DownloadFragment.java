package com.example.myapplication.ui.download;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Cache;
import com.example.myapplication.utils.MSP;
import com.liulishuo.filedownloader.FileDownloader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DownloadFragment extends Fragment{
        private View root;

        private boolean isGetData = false;

        private List<Map<String,Object>> dataLists = new ArrayList<>();

        private Handler mHandler;

        private RecyclerView recyclerView;

        DownloadRecyclerViewAdapter adapter;

        public static int setPosition =-1;


        @Override
        public void  onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mHandler = new Handler(msg -> {
                switch (msg.arg1) {
                    case 0:
                        listInit();
                        break;
                    default:
                        break;
                }
                return true;
            });


            DownloadRecyclerViewAdapter.TasksManager.getImpl().onCreate(new WeakReference<>(this));

        }

    public void postNotifyDataChanged() {
        if (adapter != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_download, container, false);


        recyclerView = root.findViewById(R.id.download_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new DownloadRecyclerViewAdapter(getContext(),mHandler));


        return root;
    }

    @Override
    public void onDestroy() {
        DownloadRecyclerViewAdapter.TasksManager.getImpl().onDestroy();
        adapter = null;
        FileDownloader.getImpl().pauseAll();
        super.onDestroy();
    }

    public static DownloadRecyclerViewAdapter.TasksManagerModel addDownloadTask(String name, String url){
        DownloadRecyclerViewAdapter.TasksManager tm=DownloadRecyclerViewAdapter.TasksManager.getImpl();
        Log.e(name,url+","+Cache.getDownloadPath()+name);
        return tm.addTask(name,url,Cache.getDownloadPath()+name);
    }


    public void listInit(){

            List<Map<String,Object>> dl_info= MSP.getDL_info(getContext());
            List<String> lName = new ArrayList<>();
            List<String> lDate = new ArrayList<>();
            List<Integer> lSoFarBytes = new ArrayList<>();
            List<Integer> lTotalBytes = new ArrayList<>();

//            List<String> lMd5 = new ArrayList<>();
            if(dl_info!=null){
                for(Map<String,Object> item:dl_info){
                    lName.add((String)item.get("name"));
                    lDate.add((String)item.get("date"));
                    lSoFarBytes.add((Integer)item.get("soFarBytes"));
                    lTotalBytes.add((Integer)item.get("totalBytes"));
//                    lMd5.add((String)item.get("md5"));
                }
            }
        }

        public void setListView(){
            recyclerView = root.findViewById(R.id.mLvFolders);
            adapter = new DownloadRecyclerViewAdapter(this.getContext(),mHandler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(adapter);
        }
}