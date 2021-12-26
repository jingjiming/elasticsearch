package com.alpha.common.beans.enums;

/**
 * 日志模块
 * Created by jiming.jing on 2019/12/18.
 */
public enum ModuleEnums {

    LOGIN("LOGIN"), //登录模块
    USER("USER"); //用户管理模块

    private String value;

    ModuleEnums(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
