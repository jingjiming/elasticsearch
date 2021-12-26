package com.alpha.elasticsearch.util;

import com.alpha.elasticsearch.facade.enums.TypeEnums;
import com.alpha.elasticsearch.facade.model.CommonModel;
import com.alpha.elasticsearch.facade.model.UserModel;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiming.jing on 2020/4/29.
 */
public class XContentBuilderUtil {

    private static final Logger logger = LoggerFactory.getLogger(XContentBuilderUtil.class);

    public static XContentBuilder buildXContentBuilder(TypeEnums typeEnums) throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject("properties");
        switch (typeEnums) {
            case USER:
                buildUserModelContentType(builder);
                break;
        }
        builder.endObject().endObject();
        return builder;
    }

    public static XContentBuilder buildXContentBuilder(TypeEnums typeEnums, CommonModel commonModel) throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        switch (typeEnums) {
            case USER:
                buildUserModel(builder, (UserModel) commonModel);
        }
        return builder;
    }

    /**
     * 由于各类型受业务影响而定，所以无法采用反射遍历的方式组装数据
     *
     * @param builder
     * @throws Exception
     */
    private static void buildUserModelContentType(XContentBuilder builder) throws Exception {
        builder.startObject("source").field("type", "integer").endObject()
                .startObject("sourceId").field("type", "integer").endObject()
                .startObject("createTime").field("type", "long").endObject()
                .startObject("updateTime").field("type", "long").endObject()
                .startObject("userId").field("type", "integer").endObject();
    }

    /**
     * 拼装添加数据的参数
     *
     * @param builder
     * @throws Exception
     */
    private static void buildUserModel(XContentBuilder builder, UserModel userModel) throws Exception {
        List<Field> fieldList = getAllFieldByClass(userModel, null);
        builder.startObject();
        for (Field field : fieldList) {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), userModel.getClass());
            String name = field.getName();
            Method method = propertyDescriptor.getReadMethod();
            Object values = method.invoke(userModel);
            if (values != null) {
                builder.field(name, values);
            }
        }
        builder.endObject();
    }

    /**
     * 获取一个类的所有属性 包括父类
     *
     * @return
     */
    public static List<Field> getAllFieldByClass(Object object, List<Field> fieldList) throws Exception {
        if (fieldList == null) {
            fieldList = new ArrayList<Field>();
        }
        if (object.getClass().toString().equals(Object.class.toString())) {
            return fieldList;
        } else {
            //System.out.println(object.getClass());
            Collections.addAll(fieldList, object.getClass().getDeclaredFields());
            fieldList = getAllFieldByClass(object.getClass().getSuperclass().newInstance(), fieldList);
        }
        return fieldList;
    }

    public static void main(String[] args) throws Exception {
        UserModel userModel = new UserModel();
        List<Field> fieldList = getAllFieldByClass(userModel, new ArrayList<>());
        System.out.println(fieldList.size());
    }

}
