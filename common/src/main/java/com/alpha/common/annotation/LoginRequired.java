package com.alpha.common.annotation;

import java.lang.annotation.*;

/**
 * Created by jiming.jing on 2019/12/18.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {

    boolean required() default true;
}
