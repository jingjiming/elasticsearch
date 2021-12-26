package com.alpha.common.beans.enums;

import com.alpha.common.beans.error.IErrCode;

/**
 * Created by kids on 2019/2/26.
 */
public enum ResponseCode implements IErrCode {

    OK(0, "ok"),
    ERROR(999, "服务器异常"),
    SESSION_TIMEOUT(911, "session timeout."),
    DATA_ACCESS_ERROR(912, "数据访问异常"),
    PARAM_VALID_ERROR(40000, "参数校验错误"),
    PARAM_VALID_NULL(40001, "空的请求参数"),
    PARAM_VALID_INVALID(40002, "无效的请求参数"),

    USER_NOT_LOGIN(-1, "用户登录信息失效，请退出重新登录");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ResponseCode toEnum(String name) {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.toString().equalsIgnoreCase(name)) {
                return responseCode;
            }
        }
        return null;
    }
}
