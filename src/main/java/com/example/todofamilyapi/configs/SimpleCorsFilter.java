package com.example.todofamilyapi.configs;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
	private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	private static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	private static final String ORIGIN = "origin";
	private static final String METHOD_OPTIONS = "OPTIONS";
	private static final String STATUS_OK = "OK";
	private static final int MAX_AGE = 3600;

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		final String origin = request.getHeader(ORIGIN);
		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, StringUtils.isEmpty(origin) ? "*" : origin);
		response.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
		response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE, PUT, PATCH");
		response.setHeader(ACCESS_CONTROL_MAX_AGE, Integer.toString(MAX_AGE));
		response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "X-Requested-With, Content-Type, Authorization, x-csrf-token, X-XSRF-TOKEN");
		response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

		if (METHOD_OPTIONS.equalsIgnoreCase(request.getMethod())) {
			response.getWriter().print(STATUS_OK);
			response.getWriter().flush();
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}
	}
}
