package com.alpha.common.beans.enums;

/**
 * 操作类型枚举
 * Created by jiming.jing on 2019/12/18.
 */
public enum OperType {

    Query("sel"), //查询，默认项
    ADD("add"), //新增操作
    DELETE("del"), //删除操作
    UPDATE("upd"), //更新操作
    UPLOAD("uploadFile"), //上传文件
    DOWNLOAD("downloadFile"); //下载文件

    private String value;

    OperType(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
