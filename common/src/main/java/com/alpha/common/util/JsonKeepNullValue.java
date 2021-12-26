package com.alpha.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by kids on 2019/4/23.
 */
public class JsonKeepNullValue {

    /**
     * 保留json对象中的空值
     */
    public static String keepNullValue(JSONObject jsonObject) {
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 保留json对象多个的时候避免索引引用
     */
    public static JSONObject disableCircularJsonObject(JSONObject jsonObject) {
        return JSONObject.parseObject(JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect));
    }

    /**
     * 保留json对象多个的时候避免索引引用
     */
    public static JSONArray disableCircularJsonArray(JSONArray jsonArray) {
        return JSONArray.parseArray(JSON.toJSONString(jsonArray, SerializerFeature.DisableCircularReferenceDetect));
    }
}
