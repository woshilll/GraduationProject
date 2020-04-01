package com.yang.graduation.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用数据传输对象
 * @author 李洋
 * @date 2020/3/28 16:57
 */
@Data
public class ResponseResult<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回对象
     */
    private T data;
    public ResponseResult() {
        super();
    }
    public ResponseResult(Integer code) {
        super();
        this.code = code;
    }
    public ResponseResult(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
    public ResponseResult(Integer code, Throwable throwable) {
        super();
        this.code = code;
        this.message = throwable.getMessage();
    }
    public ResponseResult(Integer code, T data) {
        super();
        this.code = code;
        this.data = data;
    }
    public ResponseResult(Integer code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
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
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResponseResult<?> other = (ResponseResult<?>) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        return true;
    }
    public static class CodeStatus {
        /**
         * 正确
         */
        public static final Integer OK = 20000;

        /**
         * 非法请求
         */
        public static final Integer ILLEGAL_REQUEST = 50008;

        /**
         * 客户端已登录
         */
        public static final Integer CLIENTS_LOGGED = 50012;

        /**
         * token过期
         */
        public static final Integer TOKEN_EXPIRED = 50014;

        /**
         * 登录失败 用户名或密码错误
         */
        public static final Integer LOGIN_FAIL = 50000;

        /**
         * 更新失败
         */
        public static final Integer UPDATE_FAIL = 50001;

        /**
         * 上传失败
         */
        public static final Integer UPLOAD_FAIL = 50002;
    }
}
