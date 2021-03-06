/**
 * Tianque.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.zjut.oj.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.system.UserDetails;
import com.zjut.oj.entity.system.UserInfoBean;


@SuppressWarnings("deprecation")
public class RequestUtil {

	private static HttpServletRequest request;

	@SuppressWarnings("rawtypes")
	public static HttpServletRequest getRequest() {
		request = new HttpServletRequest() {

			@Override
			public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {

			}

			@Override
			public void setAttribute(String arg0, Object arg1) {

			}

			@Override
			public void removeAttribute(String arg0) {

			}

			@Override
			public boolean isSecure() {
				return false;
			}

			@Override
			public int getServerPort() {
				return 0;
			}

			@Override
			public String getServerName() {
				return null;
			}

			@Override
			public String getScheme() {
				return null;
			}

			@Override
			public RequestDispatcher getRequestDispatcher(String arg0) {
				return null;
			}

			@Override
			public int getRemotePort() {
				return 0;
			}

			@Override
			public String getRemoteHost() {
				return null;
			}

			@Override
			public String getRemoteAddr() {
				return null;
			}

			@Override
			public String getRealPath(String arg0) {
				return null;
			}

			@Override
			public BufferedReader getReader() throws IOException {
				return null;
			}

			@Override
			public String getProtocol() {
				return null;
			}

			@Override
			public String[] getParameterValues(String arg0) {
				return null;
			}

			@Override
			public Enumeration getParameterNames() {
				return null;
			}

			@Override
			public Map getParameterMap() {
				return null;
			}

			@Override
			public String getParameter(String arg0) {
				return null;
			}

			@Override
			public Enumeration getLocales() {
				return null;
			}

			@Override
			public Locale getLocale() {
				return null;
			}

			@Override
			public int getLocalPort() {
				return 0;
			}

			@Override
			public ServletContext getServletContext() {
				return null;
			}

			@Override
			public AsyncContext startAsync() {
				return null;
			}

			@Override
			public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
				return null;
			}

			@Override
			public boolean isAsyncStarted() {
				return false;
			}

			@Override
			public boolean isAsyncSupported() {
				return false;
			}

			@Override
			public AsyncContext getAsyncContext() {
				return null;
			}

			@Override
			public DispatcherType getDispatcherType() {
				return null;
			}

			@Override
			public String getLocalName() {
				return null;
			}

			@Override
			public String getLocalAddr() {
				return null;
			}

			@Override
			public ServletInputStream getInputStream() throws IOException {
				return null;
			}

			@Override
			public String getContentType() {
				return null;
			}

			@Override
			public int getContentLength() {
				return 0;
			}

			@Override
			public String getCharacterEncoding() {
				return null;
			}

			@Override
			public Enumeration getAttributeNames() {
				return null;
			}

			@Override
			public Object getAttribute(String arg0) {
				return null;
			}

			@Override
			public boolean isUserInRole(String arg0) {
				return false;
			}

			@Override
			public boolean isRequestedSessionIdValid() {
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromUrl() {
				return false;
			}

			@Override
			public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
				return false;
			}

			@Override
			public void login(String s, String s1) throws ServletException {

			}

			@Override
			public void logout() throws ServletException {

			}

			@Override
			public Collection<Part> getParts() throws IOException, IllegalStateException, ServletException {
				return null;
			}

			@Override
			public Part getPart(String s) throws IOException, IllegalStateException, ServletException {
				return null;
			}

			@Override
			public boolean isRequestedSessionIdFromURL() {
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromCookie() {
				return false;
			}

			@Override
			public Principal getUserPrincipal() {
				return null;
			}

			@Override
			public HttpSession getSession(boolean arg0) {
				return null;
			}

			@Override
			public HttpSession getSession() {
				HttpSession session = new HttpSession() {

					@Override
					public void setMaxInactiveInterval(int arg0) {

					}

					@Override
					public void setAttribute(String arg0, Object arg1) {

					}

					@Override
					public void removeValue(String arg0) {

					}

					@Override
					public void removeAttribute(String arg0) {

					}

					@Override
					public void putValue(String arg0, Object arg1) {

					}

					@Override
					public boolean isNew() {
						return false;
					}

					@Override
					public void invalidate() {

					}

					@Override
					public String[] getValueNames() {
						return null;
					}

					@Override
					public Object getValue(String arg0) {
						return null;
					}

					@Override
					public HttpSessionContext getSessionContext() {
						return null;
					}

					@Override
					public ServletContext getServletContext() {
						return null;
					}

					@Override
					public int getMaxInactiveInterval() {
						return 0;
					}

					@Override
					public long getLastAccessedTime() {
						return 0;
					}

					@Override
					public String getId() {
						return null;
					}

					@Override
					public long getCreationTime() {
						return 0;
					}

					@Override
					public Enumeration getAttributeNames() {
						return null;
					}

					/**
					 *
					 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
					 */
					@Override
					public Object getAttribute(String name) {
						if(name == SystemConstantType.USER_DETAILS) {
							UserInfoBean userInfoBean = new UserInfoBean();
							userInfoBean.setUserId(1);

							UserDetails userDetails = new UserDetails();
							userDetails.setUser(userInfoBean);

							return userDetails;
						}
						return null;
					}
				};
				return session;
			}

			@Override
			public String getServletPath() {
				return null;
			}

			@Override
			public String getRequestedSessionId() {
				return null;
			}

			@Override
			public StringBuffer getRequestURL() {
				return null;
			}

			@Override
			public String getRequestURI() {
				return null;
			}

			@Override
			public String getRemoteUser() {
				return null;
			}

			@Override
			public String getQueryString() {
				return null;
			}

			@Override
			public String getPathTranslated() {
				return null;
			}

			@Override
			public String getPathInfo() {
				return null;
			}

			@Override
			public String getMethod() {
				return null;
			}

			@Override
			public int getIntHeader(String arg0) {
				return 0;
			}

			@Override
			public Enumeration getHeaders(String arg0) {
				return null;
			}

			@Override
			public Enumeration getHeaderNames() {
				return null;
			}

			@Override
			public String getHeader(String arg0) {
				return null;
			}

			@Override
			public long getDateHeader(String arg0) {
				return 0;
			}

			@Override
			public Cookie[] getCookies() {
				return null;
			}

			@Override
			public String getContextPath() {
				return null;
			}

			@Override
			public String getAuthType() {
				return null;
			}
		};

		return request;
	}
}
