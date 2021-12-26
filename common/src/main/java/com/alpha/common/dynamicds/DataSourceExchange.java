package com.alpha.common.dynamicds;

import com.alpha.common.annotation.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class DataSourceExchange implements MethodBeforeAdvice, AfterReturningAdvice {

    private static final Logger log = LoggerFactory.getLogger("[数据源切换器]");

    /**
     * 方法结束后
     */
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) {
        DynamicDataSourceHolder.removeRouteKey();
        log.debug("[数据源已移除]");
    }

    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     */
    @Override
    public void before(Method method, Object[] objects, Object o) {
        if (method.isAnnotationPresent(DataSource.class)) {
            DataSource dataSource = method.getAnnotation(DataSource.class);
            DynamicDataSourceHolder.setRouteKey(dataSource.value());
            log.debug("数据源切换至 > {}", DynamicDataSourceHolder.getRouteKey());
        }
    }
}
