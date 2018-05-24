package org.ruanwei.core.web;

import java.io.Serializable;


/**
 * Created by zhongxianyao.
 * Time 2018/5/24
 * Desc 文件描述
 */
public class BaseResult implements Serializable {

    /**
     * 标识本次调用是否返回
     */
    private boolean success;

    /**
     * 本次调用返回code
     */
    private int code;

    /**
     * 本次调用返回的消息，一般为错误消息
     */
    private String message;


    public boolean isSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }


    public int getCode() {
        return code;
    }


    public void setCode(int code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("success=").append(success);
        sb.append(", code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
