package com.zjut.oj.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UtilAction extends ActionSupport {

	/**
	 * Action工具类
	 */
	private static final long serialVersionUID = 8074186552697996457L;

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request;
	}

	public HttpServletResponse getResponse() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		return response;
	}

	public void pushValueStack(Object obj) {
		ActionContext.getContext().getValueStack().push(obj);
	}

	public void putValueStack(String name, Object obj) {
		ActionContext.getContext().put(name ,obj);
	}
}
