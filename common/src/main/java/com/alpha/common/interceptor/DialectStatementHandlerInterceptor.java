package com.alpha.common.interceptor;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by jiming.jing on 2020/3/25.
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare",
                args = { Connection.class, Integer.class }) })
public class DialectStatementHandlerInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(DialectStatementHandlerInterceptor.class);

    /** 是否开启debug模式*/
    private String debug;

    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler statement = (RoutingStatementHandler) invocation
                .getTarget();
        if ("true".equals(debug)) {
            logger.info("Executing SQL: {}", statement.getBoundSql().getSql().replaceAll("\\s+", " "));
            logger.info("\twith params: {}", statement.getBoundSql().getParameterObject());
        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        this.debug = properties.getProperty("debug");
    }
}
