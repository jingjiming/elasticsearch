package com.alpha.elasticsearch.util;

import com.alpha.elasticsearch.facade.enums.IndexEnums;
import com.alpha.elasticsearch.facade.enums.TypeEnums;
import com.alpha.elasticsearch.facade.model.CommonModel;

/**
 * Created by jiming.jing on 2020/4/30.
 */
public class ParamsUtil {

    /**
     * 构建主键id
     * @param indexEnums
     * @param typeEnums
     * @param commonModel
     * @return
     */
    public static String buildId(IndexEnums indexEnums, TypeEnums typeEnums, CommonModel commonModel){
        return indexEnums.getIndexName().concat("_").concat(typeEnums.getTypeName()).concat("_").
                concat(commonModel.getSource() + "_" + commonModel.getSourceId());
    }
}
