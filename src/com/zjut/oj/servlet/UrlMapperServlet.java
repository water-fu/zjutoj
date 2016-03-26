package com.zjut.oj.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 总体说明
 * 	访问地址映射转换类
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: UrlMapperServlet.java,v 0.1 2015-3-10 下午1:53:16 Exp $
 */
public class UrlMapperServlet extends HttpServlet {

	/**  */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String urlPath = request.getServletPath();
//		String matchId = request.getParameter("id");
//		if(urlPath.contains("matchReg")) {
//			response.sendRedirect(request.getContextPath() + "/system/hustojUserAction.do?method=saveUI&matchId=" + matchId);
//		}
	}

}
