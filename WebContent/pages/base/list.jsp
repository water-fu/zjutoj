<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/common/include.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>浙江工业大学ACM大学生程序设计竞赛网站</title>
<link rel="icon" href="<%=themePath%>/images/icpc_logo.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=themePath%>/images/icpc_logo.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/list.css" />
<script type="text/javascript">
	function getPageData(pageNo) {
		location.href = path + '/system/messageInfoAction.do?method=list&messageType=${param.messageType}&pageNo=' + pageNo;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>
	
	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />
	
	<div class="content">
		<div class="title">
			<h2><s:property value="#typeName"/></h2>
		</div>
		<div class="context">
			<s:iterator value="#pageQueryResult.list" id="messageInfoBean" status="stats">
				<ul>
					<li class="title"><a href="<%=path%>/system/messageInfoAction.do?method=detail&messageId=<s:property value="#messageInfoBean.messageId" />"><s:property value="#messageInfoBean.messageTitle" /></a></li>
					<li class="info"><span><s:property value="#messageInfoBean.userInfoBean.userName"/> </span></li>
					<li class="info"><span><s:date name="#messageInfoBean.createDate" format="yyyy-MM-dd HH:mm:ss" /> </span></li>
				</ul>
			</s:iterator>
		</div>

		<%@include file="/pages/common/page.jsp"%>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>