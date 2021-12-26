package com.alpha.common.beans.response;

import com.alpha.common.beans.enums.ResponseCode;
import com.alpha.common.util.StringUtils;

import java.io.Serializable;

/**
 * 统一返回结果类
 * Created by jiming on 2019/11/11.
 */
public class ResultBean<T> extends AbstractResult implements Serializable {
    /**
     * 数据对象
     */
    private T result;

    private ResultBean() {}
    /**
     * 构造正确返回对象
     * @param <T>
     * @return
     */
    public static <T> ResultBean<T> ok() {
        return ok(null);
    }

    public static <T> ResultBean<T> ok(T data) {
        ResultBean<T> rb = new ResultBean<>();
        rb.returncode = RESULT_OK;
        rb.message = ResponseCode.OK.getMessage();
        rb.result = data;
        return rb;
    }

    public static <T> ResultBean<T> okRequest() {
        ResultBean<T> rb = new ResultBean<>();
        rb.returncode = RESULT_OK;
        rb.message = ResponseCode.OK.getMessage();
        rb.result = null;
        return rb;
    }

    public static <T> ResultBean<T> okRequest(int code, String message) {
        ResultBean<T> rb = new ResultBean<>();
        rb.returncode = code;
        rb.message = message;
        rb.result = null;
        return rb;
    }

    public static <T> ResultBean<T> okRequest(int code, String message, T data) {
        ResultBean<T> rb = okRequest(code, message);
        rb.result = data;
        return rb;
    }

    /**
     * 构造错误返回对象
     * @param <T>
     * @return
     */
    public static <T> ResultBean<T> badRequest() {
        return ResultBean.badRequest(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    public static <T> ResultBean<T> badRequest(int code) {
        ResultBean<T> rb = new ResultBean<>();
        rb.returncode = code;
        rb.message = ResponseCode.ERROR.getMessage();
        return rb;
    }

    public static <T> ResultBean<T> badRequest(String message) {
        ResultBean<T> rb = new ResultBean<>();
        rb.returncode = RESULT_ERROR;
        rb.message = StringUtils.defaultIfEmpty(message, ResponseCode.ERROR.getMessage());
        return rb;
    }

    public static <T> ResultBean<T> badRequest(int code, String message) {
        ResultBean<T> rb = new ResultBean<>();
        rb.returncode = code;
        rb.message = StringUtils.defaultIfEmpty(message, ResponseCode.ERROR.getMessage());
        return rb;
    }

    public static <T> ResultBean<T> badRequest(String message, T data) {
        ResultBean<T> rb = badRequest(message);
        rb.result = data;
        return rb;
    }

    public static <T> ResultBean<T> badRequest(int code, String message, T data) {
        ResultBean<T> rb = badRequest(code, message);
        rb.result = data;
        return rb;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /*--------------------- 链式编程，返回类本身 -------------------*/
    public ResultBean code(int code) {
        this.setReturncode(code);
        return this;
    }

    public ResultBean message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResultBean data(T data) {
        this.setResult(data);
        return this;
    }
}
