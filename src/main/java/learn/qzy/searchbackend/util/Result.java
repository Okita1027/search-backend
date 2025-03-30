package learn.qzy.searchbackend.util;

import lombok.*;

import java.io.Serializable;

/**
 * @author qzy
 * @create 2024/12/12 13:12 星期四
 * @title 通用结果类
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
