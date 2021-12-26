package com.alpha.elasticsearch.facade.enums;

public enum TypeEnums {

    USER("user");

    private String typeName;

    TypeEnums(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
