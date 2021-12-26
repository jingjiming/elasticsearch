package com.alpha.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright: 
 * Author: jiming.jing 
 * Date: 2017年8月2日 
 * Description:
 */
public class SessionFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 登录入口
	 */
	private static final String LOGIN_PAGE = "/portal/index";

	private static final Map<String, String> IGNORE_PAGE = new HashMap<String, String>() {

		private static final long serialVersionUID = -7882277393281739530L;

		{
			put("/portal/index", "首页");
			put("/portal/isLogin", "判断是否登录");
			put("/portal/getAuthCode", "获取验证码");
			put("/portal/login", "登录");
			put("/portal/reginit", "注册页跳转");
			put("/portal/doRegister", "注册");
			
		}
	};

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String requestURI = request.getServletPath();

		if (this.logger.isDebugEnabled()) {
			this.logger.info(requestURI);
		}

		if ((requestURI != null && IGNORE_PAGE.containsKey(requestURI)) || isExclusion(requestURI)) {
			chain.doFilter(request, response);
			return;
		}
		if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
			if (this.logger.isDebugEnabled()) {
				this.logger.info("Request failed to verify by SESSION:" + requestURI);
			}
			// 如果是ajax请求响应 x-requested-with
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				response.setStatus(911);// 表示session timeout
				response.setHeader("sessionStatus", "timeout");
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(LOGIN_PAGE);
				dispatcher.forward(request, response);
			}
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	public boolean isExclusion(String requestURI) {
		if (requestURI.startsWith("/script/") || requestURI.startsWith("/style/") || requestURI.startsWith("/images/") || requestURI.startsWith("/error/")) {
			return true;
		}
		return false;
	}
}
