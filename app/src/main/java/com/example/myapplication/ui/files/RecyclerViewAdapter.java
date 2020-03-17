package com.example.myapplication.ui.files;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.action.FolderRequest;
import com.example.myapplication.model.Cache;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserFileDTO;
import com.example.myapplication.model.UserFolderDTO;
import com.example.myapplication.netService.reqbody.RenameFileReqBody;
import com.example.myapplication.netService.reqbody.RenameFolderReqBody;
import com.example.myapplication.netService.reqbody.ShredReqBody;
import com.example.myapplication.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Map<String,Object>> data;
    private Handler mHandler;
    private String file_type;
    private String file_name;

    public RecyclerViewAdapter(Context context, List<Map<String, Object>> data, Handler mHandler){
        this.context = context;
        this.data = data;
        this.mHandler=mHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_files,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.name.setText((String)data.get(position).get("fileName"));
        holder.date.setText((String)data.get(position).get("fileDate"));
        holder.image.setImageResource((int)data.get(position).get("fileImage"));

        holder.fileInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Map info=getPosInfo(position);
                int type=(int)info.get("type");
                int id=(int)info.get("id");
                Log.e("这里是长按的响应事件", "" + info);
                return true;
            }
        });

        holder.fileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map info=getPosInfo(position);
                int type=(int)info.get("type");
                int id=(int)info.get("id");
                Log.e("这里是点击的响应事件", "" + info);
                if(type==0){
                    FolderRequest.getFolder(v.getContext(), User.getUsername(), String.valueOf(Cache.getInParentId().pop()), "0");
                    return;
                }
                if (type==1){
                    Cache.getInParentId().push(id);
                    FolderRequest.getFolder(v.getContext(), User.getUsername(), String.valueOf(id),"0");
                    return;
                }
                if (type==2){
                    ToastUtils.showMessage(v.getContext(),"暂未实现！");
                }
            }
        });

        holder.file_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map info=getPosInfo(position);
                int type=(int)info.get("type");
                int id=(int)info.get("id");
                Log.e("这里是点击下载的响应事件", "" + info);
            }
        });

        holder.file_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map info=getPosInfo(position);
                int type=(int)info.get("type");
                int id=(int)info.get("id");
                Log.e("这里是点击移动的响应事件", "" + info);
            }
        });

        holder.file_rename.setOnClickListener(v -> {
            Map info=getPosInfo(position);
            int type=(int)info.get("type");
            int id=(int)info.get("id");
            Log.e("这里是点击重命名的响应事件", "" + info);
            Log.e("position", position +"");
            FilesFragment.setPosition =position;
            final EditText et = new EditText(v.getContext());
            et.setText(file_name);
            new AlertDialog.Builder(v.getContext()).setTitle("重命名").setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(type==1) {
                        FolderRequest.renameFolder(v.getContext(), User.getUsername(), id, new RenameFolderReqBody(et.getText().toString()));
                    }
                    if(type==2){
                        FolderRequest.renameFile(v.getContext(),User.getUsername(),id,new RenameFileReqBody(et.getText().toString(),file_type));
                    }
                }
            }).setNegativeButton("取消",null).create().show();
        });

        holder.file_delete.setOnClickListener(v -> {
            Map info=getPosInfo(position);
            int type=(int)info.get("type");
            int id=(int)info.get("id");
            Log.e("这里是点击删除的响应事件", "" + info);
            FilesFragment.setPosition =position;
            if(type==0)
                return;
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("确定删除该"+((type==1)?"文件夹":"文件")+"？");
            builder.setTitle("提示");
            builder.setPositiveButton("确定", (dialog, which) -> {
                ShredReqBody reqBody=new ShredReqBody();
                if (type==1){
                    List<Integer> folders=reqBody.getFolders();
                    folders.add(id);
                    reqBody.setFolders(folders);
                }
                if(type==2){
                    List<Integer> files=reqBody.getFiles();
                    files.add(id);
                    reqBody.setFolders(files);
                }
                FolderRequest.delete(v.getContext(),User.getUsername(),reqBody);
                dialog.dismiss();
            });
            builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

    }
    private Map<String,Integer> getPosInfo(final int position){
        /*
        type:0  返回上级
        type:1  文件夹
        type:2  文件
        id:  点击的folderId或者fileId
         */

        Map<String,Integer> info=new HashMap<>();
        int p=position;
        if(Cache.getInParentId().peek()!=0) {
            p--;
            if (p == -1) {
                info.put("type",0);
                info.put("id",-1);
                return info;
            }
        }
        List<UserFolderDTO> folders=Cache.getFolders(Cache.getInParentId().peek());
        if(p<folders.size()){
            info.put("type",1);
            info.put("id",folders.get(p).getFolderId());
            file_name=folders.get(p).getFolderName();
        }else{
            p-=folders.size();
            List<UserFileDTO> files=Cache.getFiles(Cache.getInParentId().peek());
            info.put("type",2);
            info.put("id",files.get(p).getFileId());
            file_name=files.get(p).getFileName();
            file_type=files.get(p).getFileType();
        }
        return info;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView date;
        private ImageView image;
        private View fileInfo;
        private TextView file_download;
        private TextView file_move;
        private TextView file_rename;
        private TextView file_delete;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.fileName);
            date=itemView.findViewById(R.id.fileDate);
            image=itemView.findViewById(R.id.fileImage);
            fileInfo=itemView.findViewById(R.id.fileInfo);
            file_download=itemView.findViewById(R.id.file_download);
            file_move=itemView.findViewById(R.id.file_move);
            file_rename=itemView.findViewById(R.id.file_rename);
            file_delete=itemView.findViewById(R.id.file_delete);
        }
    }
}