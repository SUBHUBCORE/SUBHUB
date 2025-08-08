package io.subHub.common.utils;



import io.subHub.common.exception.ErrorCode;

import java.io.Serializable;

/**
 * Response data
 *
 * @author By
 * @since 1.0.0
 */

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Encoding：0=success ，other = fail
     */
    private int code = 0;
    /**
     * message content
     */
    private String msg = "success";
    /**
     *
     */
    private boolean success = true;
    /**
     * Response data
     */
    private T data;

    public Result<T> ok(T data) {
        this.setData(data);
        return this;
    }

    public boolean success(){
        return code == 0;
    }

    public Result<T> error() {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = MessageUtils.getMessage(this.code);
        this.success = false;
        return this;
    }

    public Result<T> error(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(this.code);
        this.success = false;
        return this;
    }

    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = false;
        return this;
    }

    public Result<T> error(String msg) {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
        this.success = false;
        return this;
    }

    public Result<T> success(String msg) {
        this.code = 0;
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
