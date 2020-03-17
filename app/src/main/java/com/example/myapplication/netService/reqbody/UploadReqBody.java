package com.example.myapplication.netService.reqbody;

import java.util.HashMap;
import java.util.Map;

public class UploadReqBody {
    private Integer userId;
    private String fileMd5;
    private String fileName;
    private String fileType;
    private Integer parentId;

    public UploadReqBody(Integer userId, String fileMd5, String fileName, String fileType, Integer parentId) {
        this.userId = userId;
        this.fileMd5 = fileMd5;
        this.fileName = fileName;
        this.fileType = fileType;
        this.parentId = parentId;
    }

    public UploadReqBody() {
    }

    public Map<String,String> toMap(){
        Map<String, String> result=new HashMap<>();
        result.put("userId",userId.toString());
        result.put("fileMd5",fileMd5);
        result.put("fileName",fileName);
        result.put("fileType",fileType);
        result.put("parentId",parentId.toString());
        return result;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "UploadReqBody{" +
                "userId=" + userId +
                ", fileMd5='" + fileMd5 + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}