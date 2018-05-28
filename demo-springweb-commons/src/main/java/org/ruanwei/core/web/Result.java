package org.ruanwei.core.web;

/**
 * Created by zhongxianyao.
 * Time 2018/5/24
 * Desc 文件描述
 */
public class Result<T> extends BaseResult {
    private T data;

    public Result(){}

    public Result(int code, String message) {
        super(code, message);
    }

    public Result(T data) {
        this.data = data;
    }


    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("success=").append(isSuccess());
        sb.append(", code=").append(getCode());
        sb.append(", message='").append(getMessage());
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
