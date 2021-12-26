package com.alpha.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Copyright:  
 * Author: jiming.jing
 * Date: 2017年11月23日
 * Description:
 */
public class ServletRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestPath = request.getServletPath();
		// 所有请求带有后缀"."的直接去404,不接受这样的请求
		if (requestPath.lastIndexOf(".") != -1) {
			//request.getRequestDispatcher("/error/404.html").forward(request, response);
			response.sendRedirect("/error/404.html");
		} else {
			filterChain.doFilter(request, response);
		}
	}
	
}
