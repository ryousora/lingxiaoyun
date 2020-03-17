package com.example.myapplication.model;

import java.io.Serializable;
import java.util.Map;

public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回结果码
     */
    private int status;
    /**
     * 操作结果信息
     */
    private String message;
    /**
     * 返回的数据
     */
    private Map<String, Object> data;

    private T data2;

    public ResponseResult() {
    }

    public ResponseResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseResult(Map<String, Object> data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public ResponseResult(T data2, int status, String message) {
        this.data2 = data2;
        this.status = status;
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public T getData2() {
        return data2;
    }

    public void setData2(Map<String, Object> data2) {
        this.data2 = (T) data2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int code) {
        this.status = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}