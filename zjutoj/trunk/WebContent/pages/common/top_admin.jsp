<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.zjut.oj.entity.system.UserDetails"%>
<%@page import="com.zjut.oj.entity.system.UserInfoBean"%>
<%@page import="com.zjut.oj.common.SessionControl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="oj" uri="/WEB-INF/oj-tags.tld"%>
<%
	UserDetails userDetails = SessionControl.getCurUserDetail(request);
	if(userDetails != null) {
		request.setAttribute("flag", true);
		request.setAttribute("userInfoBean", userDetails.getUser());
	}else {
		request.setAttribute("flag", false);
	}
%>
<html>
<head>
<title>Insert title here</title> 
<script type="text/javascript">
	function searchMessage() {
		var form = $('#srarchForm');
		form.attr('action', path+'/system/messageInfoAction.do?method=list');
		form.submit();
	}
	
	function login() {
		location.href = path + '/system/loginInfoAction.do?method=loginUI';
	}
	
	function logout() {
		location.href = path + '/system/loginInfoAction.do?method=logout';
	}
</script>
</head>
<body>
<div class="top">
		<div class="top_content">
			<c:if test="${flag}">
				<ul>
					<li><a href="javascript:void(0);" onclick="logout()">注销</a></li>
					<li class="top_hello">
						<a href="javascript:void(0);">${userInfoBean.userName},欢迎您</a>
					</li>
				</ul>
			</c:if>
			<c:if test="${flag == false}">
				<ul>
<!-- 					<li><a href="#">注册</a></li> -->
					<li><a href="javascript:void(0);" onclick="login()">登录</a></li>
				</ul>
			</c:if>
		</div>
	</div> <!-- top结束 -->

	<div class="oper_content">
		<div class="logo">
			<div class="logo_left">
				<img src="<%=themePath %>/images/icpc_logo.png" />
			</div>
			<div class="logo_right">
				<!-- <img src="images/tel.jpg" />24小时服务热线：<span class="tel">123-456-7890</span> -->
			</div>
		</div><!-- logo结束 -->

		<div class="nav">
			<div class="nav_left"></div>
			<div class="nav_content">
				<div class="nav_content_left">
					<ul>
						<li><a href="<%=path%>/study/problemInfoAction.do?method=mlist">试题管理</a></li>
						<li><a href="<%=path%>/study/answerInfoAction.do?method=mlist">题解信息</a></li>
						<li><a href="<%=path%>/match/contestInfoAction.do?method=mlist">竞赛管理</a></li>
						
						<li><a href="<%=path%>/study/problemInfoAction.do?method=list">OnlineJudge</a></li>
					</ul>
				</div>
			</div>
			<div class="nav_right"></div>
		</div><!-- nav结束 -->
	</div>
</body>
</html>