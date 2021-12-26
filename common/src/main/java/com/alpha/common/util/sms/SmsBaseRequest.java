package com.alpha.common.util.sms;

import java.io.Serializable;

/**
 * Created by jiming.jing on 2019/12/25.
 */
public class SmsBaseRequest implements Serializable {

    private String mobile;

    private String content;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
