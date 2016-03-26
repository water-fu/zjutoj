package com.zjut.oj.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysContext {
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return requestLocal.get();
	}

	public static HttpServletResponse getResponse() {
		return responseLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}

	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}
}
