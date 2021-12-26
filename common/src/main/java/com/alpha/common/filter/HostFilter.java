package com.alpha.common.filter;

import com.alpha.common.util.ServerWhiteListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by JIMING on 2018/9/22.
 */
public class HostFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /**
         * Web安全漏洞 之 X-Frame-Options响应头配置
         * http X-Frame-Options 响应头有三个可选的值
         * DENY：页面不能被嵌入到任何iframe或frame中
         * SAMEORIGIN：页面只能被本站页面嵌入到iframe或者frame中
         * ALLOW-FROM：页面允许frame或frame加载
         */
        //response.setHeader("x-frame-options", "SAMEORIGIN");
        // 头攻击检测
        String host = request.getHeader("host");
        if (host != null && !ServerWhiteListUtils.isWhite(host)) {
            response.setStatus(403);
            logger.error("---->主机地址[{}]未通过服务器白名单校验...", host);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
