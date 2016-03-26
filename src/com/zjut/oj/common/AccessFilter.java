package com.zjut.oj.common;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zjut.oj.entity.system.UserDetails;

public class AccessFilter implements Filter{

	private Set<String> ingoreUrl = null;

	private static Log log = LogFactory.getLog(AccessFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletrequest;
		HttpServletResponse response = (HttpServletResponse)servletresponse;

		//把request和response存放在threadlocal变量中
		SysContext.setRequest(request);
		SysContext.setResponse(response);

		String accessUrl = this.calcReqFunction(request);
		if(isAccess(accessUrl)){

			//会话超时退出
			if(!this.isSessionOut(request)){
				if(log.isInfoEnabled())
					log.info("会话超时，系统退出");
//            	if(request.getHeader("x-requested-with") == null) {
//        			response.sendRedirect(request.getContextPath() + "/system/loginInfoAction.do?method=loginUI");
//        		}else {
//        			JSONObject jsonObject = new JSONObject();
//        			jsonObject.element("code", ResponseMessage.NOT_LOGIN);
//        			jsonObject.element("url", "/system/loginInfoAction.do?method=loginUI");
//        			response.getWriter().write(jsonObject.toString());
//        		}
//            	return;
			}
		}

		filterchain.doFilter(servletrequest, servletresponse);
	}

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		ingoreUrl = new HashSet<String>();
		String ingores = filterconfig.getInitParameter("ingore");
		if(ingores != null && !ingores.equals("")){
			String[] urlAry = ingores.split(",");
			for(int i=0;i<urlAry.length;i++){
				ingoreUrl.add(urlAry[i].trim());
			}
		}
	}

	/**
	 * 判断是否需要权限认证的请求
	 * @param path
	 * @return
	 */
	private boolean isAccess(String path){
		return ingoreUrl.contains(path);
	}

	/**
	 * 判断会话是否超时
	 * @param request
	 * @return
	 */
	private boolean isSessionOut(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session==null){
			return false;
		}
		UserDetails details = (UserDetails) session.getAttribute(SystemConstantType.USER_DETAILS);
		if(details==null){
			return false;
		}
		return true;
	}

	private String calcReqFunction(HttpServletRequest httpservletrequest)
	{
		String method = httpservletrequest.getParameter("method");
		String servletPath = httpservletrequest.getServletPath();
		StringBuffer stringbuffer = new StringBuffer(servletPath);
		if(method != null)
			stringbuffer.append("?method=").append(method);
		return stringbuffer.toString().trim();
	}
}
