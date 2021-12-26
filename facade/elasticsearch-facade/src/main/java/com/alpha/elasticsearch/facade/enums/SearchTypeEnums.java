package com.alpha.elasticsearch.facade.enums;

/**
 * Created by jiming.jing on 2020/4/29.
 */
public enum SearchTypeEnums {

    ALL(0, "全部"),
    USER(1, "用户");

    private int code;

    private String description;

    SearchTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static SearchTypeEnums getEnums(int code) {
        for (SearchTypeEnums enums : SearchTypeEnums.values()) {
            if(code == enums.code) {
                return enums;
            }
        }
        return null;
    }
}
