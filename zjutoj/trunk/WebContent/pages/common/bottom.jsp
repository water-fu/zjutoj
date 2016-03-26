<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%
	Calendar now = Calendar.getInstance();
%>
<html>
<head>
</head>
<body>
	<div class="bottom">
		<div class="bottom_content">
			© Copyright <%=now.get(Calendar.YEAR)  %> - 浙江工业大学教务处、 计算机科学与技术学院、ACM-ICPC竞赛团队
		</div>	
	</div><!-- bottom结束 -->
</body>
</html>