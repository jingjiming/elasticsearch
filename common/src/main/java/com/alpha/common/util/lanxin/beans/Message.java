package com.alpha.common.util.lanxin.beans;

import java.io.Serializable;

/**
 * Created by jiming.jing on 2020/7/18.
 */
public abstract class Message implements Serializable {

    private String touser;
    private String msgtype;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}
