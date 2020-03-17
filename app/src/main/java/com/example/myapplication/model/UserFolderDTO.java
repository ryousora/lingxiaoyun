package com.example.myapplication.model;

import java.util.Date;

public class UserFolderDTO /*extends Sortable */{
    private Integer folderId;

    private Integer userId;

    private Integer parentId;

    private String folderName;

    private Date createTime;

    private Date modifyTime;

    private Date deleteTime;

    public UserFolderDTO(Integer userId, Integer parentId, String folderName) {
        this.userId = userId;
        this.parentId = parentId;
        this.folderName = folderName;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName == null ? null : folderName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public String toString() {
        return "UserFolderDTO{" +
                "folderId=" + folderId +
                ", userId=" + userId +
                ", parentId=" + parentId +
                ", folderName='" + folderName + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", deleteTime=" + deleteTime +
                '}';
    }
}