<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/common/include.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>浙江工业大学OJ-系统提示</title>
<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/edit.css" />
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>

	<div class="content">
		<div class="title">
			系统提示
		</div>
		<div class="context" id="context">
			<p>${responseMessage.msg}</p>
		</div>
	</div>
    
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>