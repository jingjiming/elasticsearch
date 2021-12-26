package com.alpha.elasticsearch.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alpha.common.beans.response.ResultBean;
import com.alpha.elasticsearch.facade.enums.IndexEnums;
import com.alpha.elasticsearch.facade.enums.TypeEnums;
import com.alpha.elasticsearch.facade.model.CommonModel;
import com.alpha.elasticsearch.facade.model.SearchUserModel;
import com.alpha.elasticsearch.facade.model.UserModel;
import com.alpha.elasticsearch.facade.service.UserService;
import com.alpha.elasticsearch.util.BoolQueryBuilderUtil;
import com.alpha.elasticsearch.util.ElasticsearchClient;
import com.alpha.elasticsearch.util.ParamsUtil;
import com.alpha.elasticsearch.util.XContentBuilderUtil;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiming.jing on 2020/4/28.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String INDEX = IndexEnums.USER.getIndexName();

    private static final String TYPE = TypeEnums.USER.getTypeName();

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public boolean createIndex(int shards, int replicas) {
        CreateIndexResponse createIndexResponse = elasticsearchClient.getAdminClient().prepareCreate(INDEX)
                .setSettings(Settings.EMPTY)
                .execute().actionGet();
        return createIndexResponse.isAcknowledged() ? true : false;
    }

    @Override
    public boolean deleteIndex() {
        DeleteIndexResponse deleteIndexResponse = elasticsearchClient.getAdminClient().prepareDelete(INDEX)
                .execute().actionGet();
        return deleteIndexResponse.isAcknowledged() ? true : false;
    }

    @Override
    public boolean addMapping() {
        try {
            //初始化用户mapping
            XContentBuilder xContentBuilder = XContentBuilderUtil.buildXContentBuilder(TypeEnums.USER);
            PutMappingRequest mapping = Requests.putMappingRequest(INDEX).type(TYPE).source(xContentBuilder);
            elasticsearchClient.getAdminClient().putMapping(mapping).actionGet();
            return true;
        }catch (Exception e){
            logger.error("user addMapping error:{}", e);
        }
        return false;
    }

    @Override
    public boolean appendMapping() {
        return false;
    }

    @Override
    public ResultBean add(CommonModel commonModel) {
        UserModel userModel = (UserModel) commonModel;
        try {
            XContentBuilder builder = XContentBuilderUtil.buildXContentBuilder(TypeEnums.USER, userModel);
            elasticsearchClient.client().prepareIndex(INDEX, TYPE)
                    .setId(ParamsUtil.buildId(IndexEnums.USER, TypeEnums.USER, userModel)).setSource(builder).get();
            return ResultBean.ok();
        } catch (Exception e) {
            logger.error("user add index error:{}", e);
        }
        return ResultBean.badRequest();
    }

    @Override
    public ResultBean update(CommonModel commonModel) {
        UserModel userModel = (UserModel) commonModel;
        try {
            UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, ParamsUtil.buildId(IndexEnums.USER, TypeEnums.USER, userModel));
            XContentBuilder builder = XContentBuilderUtil.buildXContentBuilder(TypeEnums.USER, userModel);
            updateRequest.doc(builder);
            elasticsearchClient.client().update(updateRequest).get();
            return ResultBean.ok();
        } catch (Exception e) {
            logger.error("user update index error:{}", e);
        }
        return ResultBean.badRequest();
    }

    @Override
    public ResultBean delete(CommonModel commonModel) {
        UserModel userModel = (UserModel) commonModel;
        try {
            elasticsearchClient.client().prepareDelete(INDEX, TYPE,
                    ParamsUtil.buildId(IndexEnums.USER, TypeEnums.USER, commonModel)).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).execute().actionGet();
            return ResultBean.ok();
        } catch (Exception e) {
            logger.error("user update index error:{}", e);
        }
        return ResultBean.badRequest();
    }

    @Override
    public ResultBean search(CommonModel commonModel) {
        SearchUserModel searchUserModel = (SearchUserModel) commonModel;
        try {
            return ResultBean.ok();
        } catch (Exception e) {
            logger.error("检索用户失败:{}", e);
        }
        return ResultBean.badRequest();
    }



    /**
     * 获取搜索结果
     * @param searchUserModel
     * @return JSONObject
     */
    private JSONObject returnJsonObjectForSearch(SearchUserModel searchUserModel, boolean containsOwner){
        try {
            BoolQueryBuilder queryBuilder = BoolQueryBuilderUtil.termQueryUser(searchUserModel, containsOwner);
            SearchRequestBuilder searchRequestBuilder = elasticsearchClient.client().prepareSearch(INDEX).setTypes(TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
            BoolQueryBuilderUtil.needResultFiled(searchRequestBuilder, searchUserModel.getFields());
            //BoolQueryBuilderUtil.handleOrder(searchRequestBuilder, searchDyqContentEntity.getSortFileds(), searchDyqContentEntity.getSortType());
            searchRequestBuilder.setQuery(queryBuilder).setFrom(searchUserModel.getPageNum() * searchUserModel.getPageSize())
                    .setSize(searchUserModel.getPageSize());
            return buildSearchResult(searchRequestBuilder.get());
        } catch (Exception e) {
            logger.error("检索内容失败:{}", e);
        }
        return new JSONObject();
    }

    /**
     * 构建搜索结果返回值
     * @param response
     * @return
     */
    private JSONObject buildSearchResult(SearchResponse response){
        JSONObject resultJson = new JSONObject();
        SearchHits searchHits = response.getHits();
        JSONArray resultArray = new JSONArray();
        for(SearchHit searchHit : searchHits) {
            JSONObject jsonObject = JSONObject.parseObject(searchHit.getSourceAsString());
            resultArray.add(jsonObject);
        }
        resultJson.put("count", response.getHits().totalHits);
        resultJson.put("searchInfo", resultArray);
        return resultJson;
    }


}
