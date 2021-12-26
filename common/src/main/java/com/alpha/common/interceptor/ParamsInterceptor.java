package com.alpha.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 接口请求参数拦截器/生产环境不引入
 * Created by jiming.jing on 2019/12/16.
 */
public class ParamsInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ParamsInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        StringBuilder sb = new StringBuilder("\n\n");
        sb.append("-----------------------------------requestStart---------------------------------------------\n");
        //HandlerMethod h = (HandlerMethod) handler;

        sb.append("RequestURL:").append(request.getRequestURL()).append("\n");
        //请求方式  post\put\get
        sb.append("RequestMethod:").append(request.getMethod()).append("\n");
        //所有的请求参数
        try {
            sb.append("Parameters:").append("\n");
            Enumeration<String> paramNames = request.getParameterNames();
            boolean hasParam = false;
            while (paramNames.hasMoreElements()) {
                hasParam = true;
                String paramName = paramNames.nextElement();
                String value = request.getParameter(paramName);
                sb.append("\t").append(paramName).append(" - ").append(value).append("\n");
            }
            if (!hasParam) {
                sb.append("\tno param ~\n");
            }
        } catch (Exception e) {
            logger.error("-----> Request Params Interceptor error.");
        }
        sb.append("------------------------------------requestEnd----------------------------------------------\n");
        logger.info(sb.toString());

        /*String language = request.getHeader("Accept-Language");
        if ("en".equals(language)) {
            SystemMessage.language.set(Locale.ENGLISH);
        } else {
            SystemMessage.language.set(Locale.CHINESE);
        }*/
        return super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //SystemMessage.language.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
