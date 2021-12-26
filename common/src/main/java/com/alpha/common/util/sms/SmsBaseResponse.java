package com.alpha.common.util.sms;

import java.io.Serializable;

/**
 * Created by jiming.jing on 2019/12/25.
 */
public class SmsBaseResponse implements Serializable {

    private Integer result;
    private Long messageId;
    private String errorDescription;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
