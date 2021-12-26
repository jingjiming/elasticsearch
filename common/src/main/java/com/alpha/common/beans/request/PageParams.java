package com.alpha.common.beans.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * Created by jiming.jing on 2019/12/17.
 */
@ApiModel(value = "PageParams", description = "分页参数")
public class PageParams {

    @ApiParam(name = "pageNum", value = "当前页数", defaultValue = "1")
    @ApiModelProperty(value = "当前页数")
    private int pageNum = 1;

    @ApiParam(name = "pageSize", value = "每页显示纪录数", defaultValue = "15")
    @ApiModelProperty(value = "每页显示纪录数")
    private int pageSize = 15;

    private int version;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
