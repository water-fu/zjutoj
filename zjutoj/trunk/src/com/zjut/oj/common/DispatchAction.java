package com.zjut.oj.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zjut.oj.entity.system.UserDetails;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.ResponseMessage;

@SuppressWarnings("rawtypes")
public class DispatchAction extends UtilAction {

	/**
	 * Action调度类
	 */
	private static final long serialVersionUID = -5842966961572612983L;
	private final Log logger = LogFactory.getLog(DispatchAction.class);

	private String methodName;
	private Class clazz;
	private Class[] types;
	private ResponseMessage responseMessage;
	private String strutsResult = "error";

	public DispatchAction() {
		this.methodName = "";
		this.clazz = this.getClass();
		this.types = new Class[] { HttpServletRequest.class,
				HttpServletResponse.class };
		responseMessage = new ResponseMessage();
	}

	/**
	 * 方法执行之前
	 *
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void beforeExecute(HttpServletRequest request,
							   HttpServletResponse response) {
		Enumeration<String> enumeration = request.getParameterNames();
		if(request.getQueryString() != null && request.getQueryString().length() > 0) {
			logger.debug("request: " + request.getServletPath() + "?" + request.getQueryString());
		}else {
			logger.debug("request: " + request.getServletPath());
		}
		while (enumeration.hasMoreElements()) {
			String paramName = enumeration.nextElement();
			logger.debug(paramName + "  " + request.getParameter(paramName));
			if (paramName.equals("method")) {
				this.methodName = request.getParameter(paramName);
			}
		}

		logger.debug("request请求模式：" + (request.getHeader("x-requested-with") == null ? "普通同步请求" : "AJAX异步请求"));
		if(request.getHeader("x-requested-with") == null) {
			strutsResult = "syserror";
		}
	}

	/**
	 * 方法执行之后
	 *
	 * @param request
	 * @param response
	 */
	private void afterExecute(HttpServletRequest request,
							  HttpServletResponse response) {

	}

	public String execute() {
		try {
			HttpServletRequest request = getRequest();
			HttpServletResponse response = getResponse();
			//调用action方法之前执行
			beforeExecute(request, response);
			Method method = getMethod();

			//执行操作前判断是否需要登录
			if(hasLogin(request, method)) {
				//如果需要登录，但是没有登录的话，则跳转登录界面

				// AJAX请求
				if(request.getHeader("x-requested-with") != null) {
					responseMessage.setCode(ResponseMessage.NOT_LOGIN);
					responseMessage.setObj("/system/loginInfoAction.do?method=loginUI");
				} else {
					strutsResult = "loginUI";
				}
				return strutsResult;
			}

			try {
				Object[] args = { request, response };
				//使用反射机制调用action方法
				String resultStr = (String) method.invoke(this, args);
				//调用action方法之后执行
				afterExecute(request, response);
				return resultStr;
			} catch (ClassCastException e) {
				//返回值类型无法转换成String抛此异常
				logger.error("", e);
				responseMessage.setCode(ResponseMessage.FAILE);
				responseMessage.setMsg("调用的Action方法返回值无法转换为String:" + e.getMessage());
			} catch (IllegalAccessException e) {
				//调用的action方法为private抛异常
				logger.error("", e);
				responseMessage.setCode(ResponseMessage.FAILE);
				responseMessage.setMsg("调用的Action方法为private:" + e.getMessage());
			} catch (InvocationTargetException e) {
				//捕获异常
				if(e.getCause() instanceof ApplicationException) {
					ApplicationException ae = (ApplicationException) e.getCause();
					logger.error("", ae);
					responseMessage.setCode(ResponseMessage.FAILE);
					responseMessage.setMsg(ae.getMessage());
				}else {
					throw new Exception(e);
				}
			} catch (Exception e) {
				throw new Exception(e);
			}
			return strutsResult;
		} catch (Exception e) {
			if(e instanceof NoSuchMethodException) {
				logger.error("", e);
				responseMessage.setCode(ResponseMessage.FAILE);
				responseMessage.setMsg("未找到此Action方法名[" + methodName + "]的方法");
			}
			else if(e instanceof ApplicationException) {
				logger.error("", e);
				responseMessage.setCode(ResponseMessage.FAILE);
				responseMessage.setMsg(e.getMessage());
			}
			else {
				logger.error("", e);
				responseMessage.setCode(ResponseMessage.FAILE);
				responseMessage.setMsg("系统异常,请联系系统管理员");
			}
			return strutsResult;
		}
	}

	@SuppressWarnings("unchecked")
	private Method getMethod() throws NoSuchMethodException, ApplicationException {
		if(methodName.equals("")) {
			throw new ApplicationException("请求地址不正确");
		}
		Method method = this.clazz.getMethod(methodName, this.types);
		return method;
	}

	//根据方法名判断是否需要登录
	private Boolean hasLogin(HttpServletRequest request, Method method) {
		//getAnnotations包括继承的
		//得到的是当前成员所有的注释，不包括继承的
		Annotation[] annotations = method.getDeclaredAnnotations();
		if (annotations == null || annotations.length == 0) {
			return false;
		}
		for (int i = 0; i < annotations.length; i++) {
			if (annotations[i].annotationType().getName().equals(LoginFilter.class.getName())) {
				LoginFilter annotation = method.getAnnotation(LoginFilter.class);
				boolean haveLogin = annotation.needLogin();
				if(haveLogin) {
					HttpSession session = request.getSession(false);
					if(session == null){
						return true;
					}
					UserDetails details = (UserDetails) session.getAttribute(SystemConstantType.USER_DETAILS);
					if(details == null){
						return true;
					}
					return false;
				}
			}
		}
		return false;
	}

	public String responseResult(String result) {
		return result;
	}

	public String responseResult(String result, Object obj) {
		this.responseMessage.setObj(obj);
		return result;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}
}
