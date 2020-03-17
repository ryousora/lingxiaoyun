package com.example.myapplication.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Note {
    private Integer id;

    private Integer parentId;

    private Integer userId;

    private String title;

    private String content;

    private  Integer imageId;

    private Integer thumb_up;

    private Integer thumb_down;

    private Boolean isDelete;

    private Date createTime;

    private Date updateTime;

    public Note() {
    }

    public Note(Integer userId, Integer parentId, String title, String content) {
        this.userId = userId;
        this.parentId = parentId;
        this.title = title;
        this.content = content;
        this.imageId=0;
        isDelete=false;
        thumb_up=thumb_down=0;
    }

    public Note(Integer userId, Integer parentId, String title, String content,int imageId) {
        this.userId = userId;
        this.parentId = parentId;
        this.title = title;
        this.content = content;
        this.imageId=imageId;
        isDelete=false;
        thumb_up=thumb_down=0;
    }

    public Note(Integer id, Integer parentId, Integer userId, String title, String content, Integer imageId, Integer thumb_up, Integer thumb_down, Boolean isDelete, Date createTime, Date updateTime) {
        this.id = id;
        this.parentId = parentId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageId = imageId;
        this.thumb_up = thumb_up;
        this.thumb_down = thumb_down;
        this.isDelete = isDelete;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getThumb_up() {
        return thumb_up;
    }

    public void setThumb_up(Integer thumb_up) {
        this.thumb_up = thumb_up;
    }

    public Integer getThumb_down() {
        return thumb_down;
    }

    public void setThumb_down(Integer thumb_down) {
        this.thumb_down = thumb_down;
    }
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Map<String, Object> getNote(){
        Map<String, Object> data = new HashMap<>();
        data.put("id",id);
        data.put("userId",userId);
        data.put("title",title);
        data.put("content",content);
        data.put("imageId",imageId);
        data.put("isDelete",isDelete);
        data.put("createTime",createTime);
        data.put("updateTime",updateTime);
        return data;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageId='" + imageId + '\'' +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}