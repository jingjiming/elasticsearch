package com.alpha.common.aop;

import com.alpha.common.beans.response.ResultBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jiming.jing on 2020/3/24.
 */
@Aspect
@Component
public class AopMethod {

    private static final Logger logger = LoggerFactory.getLogger(AopMethod.class);
    /**
     * 创建返回切点,只切返回ResultBean的方法，没有切返回String和void的方法
     */
    @Pointcut("execution(public com.alpha.common.beans.response.ResultBean *(..))")
    public void resultPointCut() {}

    @Around("resultPointCut()")
    public Object handleControllerMethods(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info(pjp.getSignature() + "请求参数:{}", pjp.getArgs());
        ResultBean resultBean;
        // 此处可做请求参数相关校验，统一异常返回处理

        resultBean = (ResultBean) pjp.proceed();
        logger.info("返回结果:{}", resultBean);
        logger.info(pjp.getSignature() + "处理耗费时间:" + (System.currentTimeMillis() - startTime) + "ms");
        return resultBean;
    }
}
