package com.alpha.elasticsearch.util;

import com.alpha.common.util.StringUtils;
import com.alpha.elasticsearch.facade.model.SearchUserModel;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by jiming.jing on 2020/4/30.
 */
public class BoolQueryBuilderUtil {

    /**
     * 匹配查询
     * @param searchUserModel
     */
    public static BoolQueryBuilder termQueryUser(SearchUserModel searchUserModel, boolean containsOwner) throws Exception{
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //step 1  模糊查询匹配
        if(searchUserModel.getSource() != null){
            queryBuilder.must(QueryBuilders.termQuery("source", searchUserModel.getSource()));
        }
        if(searchUserModel.getSourceId() != null){
            queryBuilder.must(QueryBuilders.termQuery("sourceId", searchUserModel.getSourceId()));
        }
        if(searchUserModel.getUserId() != null && containsOwner){
            queryBuilder.mustNot(QueryBuilders.termQuery("sourceId", searchUserModel.getSourceId()));
        }
        if(StringUtils.isNotEmpty(searchUserModel.getNickname())){
            queryBuilder.must(QueryBuilders.wildcardQuery("nickname", "*" + searchUserModel.getNickname() + "*"));
        }
        if(StringUtils.isNotEmpty(searchUserModel.getDescription())){
            queryBuilder.must(QueryBuilders.wildcardQuery("description", "*" + searchUserModel.getDescription() + "*"));
        }
        return queryBuilder;
    }

    /**
     * 处理需要返回的字段
     * @param searchRequestBuilder
     */
    public static void needResultFiled(SearchRequestBuilder searchRequestBuilder, String fileds){
        if(StringUtils.isNotEmpty(fileds)) {
            searchRequestBuilder.setFetchSource(fileds.split(","), null);
        }
    }
}
