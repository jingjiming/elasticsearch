package com.alpha.elasticsearch.facade.model;

import java.io.Serializable;

/**
 * ElasticSearch 公共Model
 * Created by jiming.jing on 2020/4/28.
 */
public class CommonModel implements Serializable {

    /**
     * @see com.alpha.elasticsearch.facade.enums.SearchTypeEnums
     */
    private Integer source;

    private Integer sourceId;

    private Long createTime;

    private Long updateTime;

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
