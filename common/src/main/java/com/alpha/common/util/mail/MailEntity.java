package com.alpha.common.util.mail;

/**
 * Created by jiming.jing on 2020/5/31.
 */
public class MailEntity {

    private String text;

    private String subject;

    private String location;

    private String to;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
