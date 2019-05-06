package com.voldy.myblog.vo;

/**
 * TODO
 * 返回对象
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/5
 **/
public class ResponseVO {
    private boolean success; //处理是否成功
    private String message;  //处理消息
    private Object body;    //返回数据

    public ResponseVO() {
    }

    public ResponseVO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseVO(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
