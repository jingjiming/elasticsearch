package com.alpha.common.exceptions;

import com.alpha.common.beans.error.IErrCode;

/**
 * Created by jiming.jing on 2020/3/25.
 */
public class BizException extends RuntimeException {

    private int errorCode;

    public BizException() {
    }

    public BizException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(IErrCode e) {
        super(e.getMessage());
        this.errorCode = e.getCode();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
