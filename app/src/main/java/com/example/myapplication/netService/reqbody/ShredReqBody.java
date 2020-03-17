package com.example.myapplication.netService.reqbody;

import java.util.ArrayList;
import java.util.List;

public class ShredReqBody {
    private List<Integer> files;
    private List<Integer> folders;

    public ShredReqBody(){
        files=new ArrayList<>();
        folders=new ArrayList<>();
    }
    public List<Integer> getFiles() {
        return files;
    }

    public void setFiles(List<Integer> files) {
        this.files = files;
    }

    public List<Integer> getFolders() {
        return folders;
    }

    public void setFolders(List<Integer> folders) {
        this.folders = folders;
    }

    @Override
    public String toString() {
        return "ShredReqBody{" +
                "files=" + files +
                ", folders=" + folders +
                '}';
    }
}