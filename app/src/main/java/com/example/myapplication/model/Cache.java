package com.example.myapplication.model;

import com.example.myapplication.utils.RequestBuild;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {
    private static Map<Integer,List<UserFolderDTO>> folders;
    private static Map<Integer,List<UserFileDTO>> files;
    private static Map<Integer,Note> notes;
    private static List<Note> userNotes;
    private static ParentIdStack inParentId;
    private static int moveType;
    private static Integer moveF;

//    private static RequestBuild requestBuild;

    private static String downloadPath;
    private static String max_download;

    public static void init(){
        folders= new HashMap<>();
        files=new HashMap<>();
        notes=new HashMap<>();
        inParentId=new ParentIdStack();
        moveF=0;
        moveType=0;
//        requestBuild=new RequestBuild();
    }
//    public static RequestBuild getRequestBuild(){
//        return requestBuild;
//    }
    public static ParentIdStack getInParentId() {
        return inParentId;
    }

    public static void putFolders(Integer parentId,List<UserFolderDTO> folders){
        Cache.folders.put(parentId,folders);
    }
    public static void putFiles(Integer parentId,List<UserFileDTO> files){
        Cache.files.put(parentId,files);
    }
    public static void replaceFolders(Integer parentId,List<UserFolderDTO> folders){
        Cache.folders.replace(parentId,folders);
    }
    public static void replaceFiles(Integer parentId,List<UserFileDTO> files){
        Cache.files.replace(parentId,files);
    }

    public static void putNote(Integer noteId,Note note){
        Cache.notes.put(noteId,note);
    }
    public static void putUserNote(List<Note> notes){
        Cache.userNotes=notes;
    }
    public static List<UserFolderDTO> getFolders(Integer parentId){/*
        List<UserFolderDTO> list= new ArrayList<>();
        for(UserFolderDTO userFolderDTO:folders.get(parentId)){

        }*/

        return Cache.folders.get(parentId);
    }
    public static List<UserFileDTO> getFiles(Integer parentId){
        return Cache.files.get(parentId);
    }
    public static Note getNote(Integer noteId){
        return Cache.notes.get(noteId);
    }
    public static List<Note> gutUserNote(){
        return Cache.userNotes;
    }

    public static String getDownloadPath() {
        return downloadPath;
    }

    public static void setDownloadPath(String downloadPath) {
        Cache.downloadPath = downloadPath;
    }

    public static String getMax_download() {
        return max_download;
    }

    public static void setMax_download(String max_download) {
        Cache.max_download = max_download;
        int max=Integer.parseInt(max_download);
        FileDownloader.getImpl().setMaxNetworkThreadCount(max);
    }

    public static int getMoveType() {
        return moveType;
    }

    public static void setMoveType(int moveType) {
        Cache.moveType = moveType;
    }

    public static Integer getMoveF() {
        return moveF;
    }

    public static void setMoveF(Integer moveF) {
        Cache.moveF = moveF;
    }

    public static void delP(Integer id){
        Cache.folders.remove(id);
        Cache.files.remove(id);
    }
}
