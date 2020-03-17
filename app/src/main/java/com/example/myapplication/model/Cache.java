package com.example.myapplication.model;

import com.google.gson.Gson;

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

    private static String downloadPath;

    public static void init(){
        folders= new HashMap<>();
        files=new HashMap<>();
        notes=new HashMap<>();
        inParentId=new ParentIdStack();
    }
    public static ParentIdStack getInParentId() {
        return inParentId;
    }

    public static void putFolders(Integer parentId,List<UserFolderDTO> folders){
        Cache.folders.put(parentId,folders);
    }
    public static void putFiles(Integer parentId,List<UserFileDTO> files){
        Cache.files.put(parentId,files);
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
}
