package com.alpha.common.annotation;

import com.alpha.common.beans.enums.ModuleEnums;
import com.alpha.common.beans.enums.OperType;

import java.lang.annotation.*;

/**
 * 业务操作注解
 * Created by jiming.jing on 2020/3/23.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizOperLog {

    /**
     * 日志模块
     * @return
     */
    //ModuleEnums logModule();

    /**
     * 操作类型
     * @return
     */
    OperType operType() default OperType.Query;
    /**
     * 备注
     * @return
     */
    String remarks() default "";
}
