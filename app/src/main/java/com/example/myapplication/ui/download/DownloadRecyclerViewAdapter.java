package com.example.myapplication.ui.download;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.action.FolderRequest;
import com.example.myapplication.model.User;
import com.example.myapplication.netService.reqbody.RenameFileReqBody;
import com.example.myapplication.netService.reqbody.RenameFolderReqBody;
import com.example.myapplication.utils.FileUtils;
import com.example.myapplication.utils.Utils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DownloadRecyclerViewAdapter extends RecyclerView.Adapter<DownloadRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private Handler mHandler;
    private String file_name;

    public DownloadRecyclerViewAdapter(Context context, Handler mHandler){
        this.context = context;
        this.mHandler=mHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(
                LayoutInflater.from(
                        context)
                        .inflate(R.layout.item_download, parent, false));

        holder.action.setOnClickListener(taskActionOnClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
/*

        holder.name.setText((String)data.get(position).get("name"));
        String status=data.get(position).get("soFarBytes")+"/"+data.get(position).get("totalBytes");
        holder.status1.setText(status);
        holder.status2.setText((String)data.get(position).get("date"));*/
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("是否删除该文件？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new File(TasksManager.getImpl().get(holder.pos).getPath()).delete();
                        TasksManager.getImpl().deleteTask(holder.id,position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0,getItemCount());
                    }
                }).setNegativeButton("取消",null).create().show();
                return true;
            }
        });
/*
        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("这里是点击下载的响应事件", "xz");
            }
        });
*/

        final TasksManagerModel model = TasksManager.getImpl().get(position);

        holder.update(model.getId(), position);
        holder.action.setTag(holder);
        holder.name.setText(model.getName());
//        holder.status2.setText(Utils.bytes2kb(FileDownloader.getImpl().getTotal(holder.id)));

        TasksManager.getImpl().updateViewHolder(holder.id, holder);

        holder.action.setEnabled(true);


        if (TasksManager.getImpl().isReady()) {
            final int stat = TasksManager.getImpl().getStatus(model.getId(), model.getPath());
            if (stat == FileDownloadStatus.pending || stat == FileDownloadStatus.started ||
                    stat == FileDownloadStatus.connected) {
                // start task, but file not created yet
                holder.updateDownloading(stat, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else if (!new File(model.getPath()).exists() &&
                    !new File(FileDownloadUtils.getTempPath(model.getPath())).exists()) {
                // not exist file
                holder.updateNotDownloaded(stat, 0, 0);
            } else if (TasksManager.getImpl().isDownloaded(stat)) {
                // already downloaded and exist
                holder.updateDownloaded();
            } else if (stat == FileDownloadStatus.progress) {
                // downloading
                holder.updateDownloading(stat, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else {
                // not start
                holder.updateNotDownloaded(stat, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            }
        } else {
            holder.status2.setText(R.string.tasks_manager_demo_status_loading);
            holder.action.setEnabled(false);
        }

    }

    private FileDownloadListener taskDownloadListener = new FileDownloadSampleListener() {

        private ViewHolder checkCurrentHolder(final BaseDownloadTask task) {
            final ViewHolder tag = (ViewHolder) task.getTag();
            if (tag.id != task.getId()) {
                return null;
            }

            return tag;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes);

            tag.status2.setText(R.string.tasks_manager_demo_status_pending);
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            final ViewHolder tag = checkCurrentHolder(task);

            tag.status2.setText(R.string.tasks_manager_demo_status_started);
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);

            tag.status2.setText(R.string.tasks_manager_demo_status_connected);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                    , task.getLargeFileTotalBytes());
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.paused(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());

            tag.status2.setText(R.string.tasks_manager_demo_status_paused);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloaded();
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }
    };


    private View.OnClickListener taskActionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() == null) {
                return;
            }

            ViewHolder holder = (ViewHolder) v.getTag();

            CharSequence action = ((TextView) v).getText();
            if (action.equals("暂停")) {
                // to pause
                FileDownloader.getImpl().pause(holder.id);
            } else if (action.equals("开始")) {
                // to start
                // to start
                final TasksManagerModel model = TasksManager.getImpl().get(holder.pos);
                final BaseDownloadTask task = FileDownloader.getImpl().create(model.getUrl())
                        .setPath(model.getPath())
                        .setCallbackProgressTimes(100)
                        .setListener(taskDownloadListener);

                TasksManager.getImpl().addTaskForViewHolder(task);

                TasksManager.getImpl().updateViewHolder(holder.id, holder);

                task.start();
            } else if (action.equals("删除")) {
                // to delete
                new File(TasksManager.getImpl().get(holder.pos).getPath()).delete();
                holder.action.setEnabled(true);
                holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
            }else if (action.equals("打开")) {
                // to open
                File file = new File(TasksManager.getImpl().get(holder.pos).getPath());
                FileUtils.openFile(file,context);
            }
        }
    };

    public static class TasksManager {
        private final static class HolderClass {
            private final static TasksManager INSTANCE
                    = new DownloadRecyclerViewAdapter.TasksManager();
        }

        public static TasksManager getImpl() {
            return HolderClass.INSTANCE;
        }

        private TasksManagerDBController dbController;
        private List<TasksManagerModel> modelList;

        private TasksManager() {
            dbController = new TasksManagerDBController();
            modelList = dbController.getAllTasks();

            //initDemo();
        }

        private void initDemo() {
            if (modelList.size() <= 0) {
                final int demoSize = Constant.BIG_FILE_URLS.length;
                for (int i = 0; i < demoSize; i++) {
                    final String url = Constant.BIG_FILE_URLS[i];
                    addTask(url,url);
                }
            }
        }

        private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();

        public void addTaskForViewHolder(final BaseDownloadTask task) {
            taskSparseArray.put(task.getId(), task);
        }

        public void removeTaskForViewHolder(final int id) {
            taskSparseArray.remove(id);
        }

        public void deleteTask(int id,int pos){
            modelList.remove(pos);
            dbController.deleteTask(id);
        }

        public void updateViewHolder(final int id, final ViewHolder holder) {
            final BaseDownloadTask task = taskSparseArray.get(id);
            if (task == null) {
                return;
            }

            task.setTag(holder);
        }

        public void releaseTask() {
            taskSparseArray.clear();
        }

        private FileDownloadConnectListener listener;

        private void registerServiceConnectionListener(final WeakReference<DownloadFragment>
                                                               activityWeakReference) {
            if (listener != null) {
                FileDownloader.getImpl().removeServiceConnectListener(listener);
            }

            listener = new FileDownloadConnectListener() {

                @Override
                public void connected() {
                    if (activityWeakReference == null
                            || activityWeakReference.get() == null) {
                        return;
                    }

                    activityWeakReference.get().postNotifyDataChanged();
                }

                @Override
                public void disconnected() {
                    if (activityWeakReference == null
                            || activityWeakReference.get() == null) {
                        return;
                    }

                    activityWeakReference.get().postNotifyDataChanged();
                }
            };

            FileDownloader.getImpl().addServiceConnectListener(listener);
        }

        private void unregisterServiceConnectionListener() {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
            listener = null;
        }

        public void onCreate(final WeakReference<DownloadFragment> activityWeakReference) {
            if (!FileDownloader.getImpl().isServiceConnected()) {
                FileDownloader.getImpl().bindService();
                registerServiceConnectionListener(activityWeakReference);
            }
        }

        public void onDestroy() {
            unregisterServiceConnectionListener();
            releaseTask();
        }

        public boolean isReady() {
            return FileDownloader.getImpl().isServiceConnected();
        }

        public TasksManagerModel get(final int position) {
            return modelList.get(position);
        }

        public TasksManagerModel getById(final int id) {
            for (TasksManagerModel model : modelList) {
                if (model.getId() == id) {
                    return model;
                }
            }

            return null;
        }

        /**
         * @param status Download Status
         * @return has already downloaded
         * @see FileDownloadStatus
         */
        public boolean isDownloaded(final int status) {
            return status == FileDownloadStatus.completed;
        }

        public int getStatus(final int id, String path) {
            return FileDownloader.getImpl().getStatus(id, path);
        }

        public long getTotal(final int id) {
            return FileDownloader.getImpl().getTotal(id);
        }

        public long getSoFar(final int id) {
            return FileDownloader.getImpl().getSoFar(id);
        }

        public int getTaskCounts() {
            return modelList.size();
        }

        public TasksManagerModel addTask(final String name, final String url) {
//            return addTask(name, url, createPath(url));
            return DownloadFragment.addDownloadTask(name, url);
        }

        public TasksManagerModel addTask(final String name, final String url, final String path) {
            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
                return null;
            }

            final int id = FileDownloadUtils.generateId(url, path);
            TasksManagerModel model = getById(id);
            if (model != null) {
                return model;
            }
            final TasksManagerModel newModel = dbController.addTask(name, url, path);
            if (newModel != null) {
                modelList.add(newModel);
            }

            return newModel;
        }

        public String createPath(final String url) {
            if (TextUtils.isEmpty(url)) {
                return null;
            }

            return FileDownloadUtils.getDefaultSaveFilePath(url);
        }
    }


    private static class TasksManagerDBController {
        public final static String TABLE_NAME = "tasksmanger";
        private final SQLiteDatabase db;

        private TasksManagerDBController() {
            TasksManagerDBOpenHelper openHelper = new TasksManagerDBOpenHelper(MainActivity.CONTEXT);

            db = openHelper.getWritableDatabase();
        }

        public List<TasksManagerModel> getAllTasks() {
            final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            final List<TasksManagerModel> list = new ArrayList<>();
            try {
                if (!c.moveToLast()) {
                    return list;
                }

                do {
                    TasksManagerModel model = new TasksManagerModel();
                    model.setId(c.getInt(c.getColumnIndex(TasksManagerModel.ID)));
                    model.setName(c.getString(c.getColumnIndex(TasksManagerModel.NAME)));
                    model.setUrl(c.getString(c.getColumnIndex(TasksManagerModel.URL)));
                    model.setPath(c.getString(c.getColumnIndex(TasksManagerModel.PATH)));
//                    model.setTotal(c.getLong(c.getColumnIndex(TasksManagerModel.TOTAL)));
                    list.add(model);
                } while (c.moveToPrevious());
            } finally {
                if (c != null) {
                    c.close();
                }
            }

            return list;
        }

        public TasksManagerModel addTask(final String name,final String url, final String path) {
            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
                return null;
            }

            // have to use FileDownloadUtils.generateId to associate TasksManagerModel with FileDownloader
            final int id = FileDownloadUtils.generateId(url, path);

            TasksManagerModel model = new TasksManagerModel();
            model.setId(id);
            model.setName(name);
            model.setUrl(url);
            model.setPath(path);
//            model.setTotal(TasksManager.getImpl().getTotal(id));

            final boolean succeed = db.insert(TABLE_NAME, null, model.toContentValues()) != -1;
            return succeed ? model : null;
        }

        public void deleteTask(int id){
            db.delete(TABLE_NAME,"id=?", new String[]{String.valueOf(id)});
        }


    }


    private static class TasksManagerDBOpenHelper extends SQLiteOpenHelper {
        public final static String DATABASE_NAME = "tasksmanager2.db";
        public final static int DATABASE_VERSION = 2;

        public TasksManagerDBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TasksManagerDBController.TABLE_NAME
                    + String.format(
                    "("
                            + "%s INTEGER PRIMARY KEY, " // id, download id
                            + "%s VARCHAR, " // name
                            + "%s VARCHAR, " // url
                            + "%s VARCHAR " // path
//                            + "%s BIGINT " // total
                            + ")"
                    , TasksManagerModel.ID
                    , TasksManagerModel.NAME
                    , TasksManagerModel.URL
                    , TasksManagerModel.PATH
//                    , TasksManagerModel.TOTAL

            ));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion == 1 && newVersion == 2) {
                db.delete(TasksManagerDBController.TABLE_NAME, null, null);
            }
        }
    }

    public static class TasksManagerModel {
        public final static String ID = "id";
        public final static String NAME = "name";
        public final static String URL = "url";
        public final static String PATH = "path";
//        public final static String TOTAL = "total";

        private int id;
        private String name;
        private String url;
        private String path;
//        private long total;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public ContentValues toContentValues() {
            ContentValues cv = new ContentValues();
            cv.put(ID, id);
            cv.put(NAME, name);
            cv.put(URL, url);
            cv.put(PATH, path);
//            cv.put(TOTAL, total);
            return cv;
        }
/*
        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }*/
    }


    @Override
    public int getItemCount() {
        return TasksManager.getImpl().getTaskCounts();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView status1;
        private TextView status2;
        private ProgressBar progress;
        private Button action;
        private View item;
        private int pos;
        private int id;
        private long total;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.download_name_tv);
            status1=itemView.findViewById(R.id.task_status1_tv);
            status2=itemView.findViewById(R.id.task_status2_tv);
            progress=itemView.findViewById(R.id.task_pb);
            action=itemView.findViewById(R.id.task_action_btn);
            item=itemView.findViewById(R.id.download_info);
        }


        public void update(final int id, final int position) {
            this.id = id;
            this.pos = position;
        }


        public void updateDownloaded() {
            progress.setMax(1);
            progress.setProgress(1);
            action.setText("打开");
            if(total!=0)
                status1.setText(Utils.bytes2kb(total));
            else status1.setText("100%");
            status2.setText(R.string.tasks_manager_demo_status_completed);
        }

        public void updateNotDownloaded(final int status, final long sofar, final long total) {
            if (sofar > 0 && total > 0) {
                final float percent = sofar
                        / (float) total;
                progress.setMax(100);
                progress.setProgress((int) (percent * 100));
            } else {
                progress.setMax(1);
                progress.setProgress(0);
            }
            switch (status) {
                case FileDownloadStatus.error:
                    status2.setText(R.string.tasks_manager_demo_status_error);
                    break;
                case FileDownloadStatus.paused:
                    status2.setText(R.string.tasks_manager_demo_status_paused);
                    break;
                default:
                    status2.setText(R.string.tasks_manager_demo_status_not_downloaded);
                    break;
            }
            status1.setText(String.format("%s / %s", Utils.bytes2kb(sofar), Utils.bytes2kb(total)));
            action.setText("开始");
        }

        public void updateDownloading(final int status, final long sofar, final long total) {
            final float percent = sofar / (float) total;
            progress.setMax(100);
            progress.setProgress((int) (percent * 100));

            switch (status) {
                case FileDownloadStatus.pending:
                    status2.setText(R.string.tasks_manager_demo_status_pending);
                    break;
                case FileDownloadStatus.started:
                    status2.setText(R.string.tasks_manager_demo_status_started);
                    break;
                case FileDownloadStatus.connected:
                    status2.setText(R.string.tasks_manager_demo_status_connected);
                    break;
                case FileDownloadStatus.progress:
                    status2.setText(R.string.tasks_manager_demo_status_progress);
                    break;
                default:
                    status2.setText(MainActivity.CONTEXT.getString(
                            R.string.tasks_manager_demo_status_downloading, status));
                    break;
            }
            this.total=total;
            status1.setText(String.format("%s / %s", Utils.bytes2kb(sofar), Utils.bytes2kb(total)));
            action.setText("暂停");
        }

    }
}