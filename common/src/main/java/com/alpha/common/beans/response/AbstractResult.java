package com.alpha.common.beans.response;

import com.alpha.common.beans.enums.ResponseCode;

/**
 * Created by jiming.jing on 2020/3/23.
 */
public abstract class AbstractResult implements IResult {
    /**
     * 返回正常，状态 0
     */
    public static final int RESULT_OK = ResponseCode.OK.getCode();
    /**
     * 返回错误，状态999
     */
    public static final int RESULT_ERROR = ResponseCode.ERROR.getCode();

    /**
     * 编码，0为正常，错误码参考ResponseCode
     */
    protected int returncode = RESULT_OK;
    /**
     * 描述
     */
    protected String message = ResponseCode.OK.getMessage();

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
