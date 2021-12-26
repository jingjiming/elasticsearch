package com.alpha.elasticsearch.facade.enums;

/**
 * ES Index 枚举
 * Created by jiming.jing on 2020/4/28.
 */
public enum IndexEnums {

    USER("user");

    private String indexName;

    IndexEnums(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
