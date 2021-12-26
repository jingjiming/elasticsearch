package com.alpha.common.beans.response;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiming.jing on 2019/12/17.
 */
public class PageResult<T> implements Serializable {

    /** 数据列表 */
    private List<T> list;
    /** 当前页 */
    private int pageNum = 1;
    /** 每页条数 */
    private int pageSize = 10;
    /** 总条数 */
    private long total;
    /** 总页数 */
    private int pages;

    /**
     * 获取 数据列表
     *
     * @return list 数据列表
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置 数据列表
     *
     * @param list 数据列表
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 获取 当前页
     *
     * @return pageNo 当前页
     */
    public int getPageNum() {
        return this.pageNum;
    }

    /**
     * 设置 当前页
     *
     * @param pageNum 当前页
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 获取 每页条数
     *
     * @return pageSize 每页条数
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置 每页条数
     *
     * @param pageSize 每页条数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取总条数
     *
     * @return total 总条数
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置总条数
     *
     * @param total 总条数
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 获取 总页数
     *
     * @return totalPage 总页数
     */
    public int getPages() {
        return this.pages;
    }

    /**
     * 设置 总页数
     *
     * @param pages 总页数
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * 一共多少页
     */
    public void setTotalPage() {
        if (this.total == 0) {
            this.pages = 0;
        } else {
            this.pages = (int) ((this.total % this.pageSize > 0) ? (this.total / this.pageSize + 1)
                    : this.total / this.pageSize);
        }
    }

    /**
     * pageHelper插件 PageInfo对象转PageResult对象
     * @param pageInfo
     * @return 返回转换完之后的PageResult对象
     */
    public static PageResult convert2PageResult(PageInfo pageInfo){
        PageResult pageResult = new PageResult();
        pageResult.setList(pageInfo.getList());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPages(pageInfo.getPages());
        return pageResult;
    }
}
