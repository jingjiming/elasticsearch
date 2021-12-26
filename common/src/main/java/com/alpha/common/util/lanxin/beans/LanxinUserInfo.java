package com.alpha.common.util.lanxin.beans;

import java.io.Serializable;

/**
 * Created by jiming.jing on 2020/7/16.
 */
public class LanxinUserInfo implements Serializable {

    private int id;
    private String name;
    private String email;
    private String mobile;
    // 成员在组织中的位置
    private String path;
    // 成员所在部门ID
    private int parentId;
    // 成员所在组织名称
    private String orgName;
    // 成员所在公司名称，对应蓝信中的“单位”
    private String company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
