package com.alpha.elasticsearch.facade.service;

import com.alpha.common.beans.response.ResultBean;
import com.alpha.elasticsearch.facade.model.CommonModel;

/**
 * ES 接口
 * Created by jiming.jing on 2020/4/28.
 */
public interface ElasticsearchService {

    /**
     * 创建索引
     * @param shards   分片数
     * @param replicas  副本数
     * @return
     */
    boolean createIndex(int shards, int replicas);

    /**
     * 删除索引
     * @return
     */
    boolean deleteIndex();

    /**
     * 添加映射
     * @return
     */
    boolean addMapping();

    /**
     * 追加映射
     * @return
     */
    boolean appendMapping();

    /**
     * 添加数据
     * @return
     */
    ResultBean add(CommonModel commonModel);

    /**
     * 修改数据
     * @return
     */
    ResultBean update(CommonModel commonModel);

    /**
     * 删除数据
     * @return
     */
    ResultBean delete(CommonModel commonModel);

    /**
     * 查找数据
     * @return
     */
    ResultBean search(CommonModel commonModel);
}
