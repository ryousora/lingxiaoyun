package com.example.myapplication.netService.reqbody;

public class NoteInsertReqBody {
    private Integer userId;

    private Integer parentId;

    private String title;

    private String content;

    public NoteInsertReqBody() {
    }

    public NoteInsertReqBody(Integer userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
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
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public Integer getParentId() {
        return parentId;
    }

}