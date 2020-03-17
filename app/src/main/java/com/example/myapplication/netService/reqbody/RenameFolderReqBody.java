package com.example.myapplication.netService.reqbody;

public class RenameFolderReqBody {
    private String folderName;

    public RenameFolderReqBody(String folderName) {
        this.folderName=folderName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public String toString() {
        return "RenameFolderReqBody{" +
                "folderName='" + folderName + '\'' +
                '}';
    }
}