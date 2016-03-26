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
		var form = $('#searchForm');
		form.attr('action', path + '/study/commentInfoAction.do?method=list&pageNo='+pageNo);
		form.submit();
	}
	
	function detail(id) {
		location.href = path + '/study/commentInfoAction.do?method=detail&commentId='+id;
	}
	
	function append() {
		location.href = path + '/study/commentInfoAction.do?method=saveUI';
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_judge.jsp"%>
	
	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />
	
	<div class="content">
		<div class="title">
			<h2>评论主题列表</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0)" onclick="append()">新增</a></li>
			</ul>
		</div>
		<div class="context" id="context">
				<ul>
					<li class="info_4_1 list_bg"><span>题目编号</span></li>
					<li class="title_4_1 list_bg"><span>主题</span></li>
					<li class="info_4_1 list_bg"><span>作者</span></li>
					<li class="date_4_1 list_bg"><span>日期</span></li>
				</ul>
			<s:iterator value="#pageQueryResult.list" id="commentInfoBean" status="stats">
				<ul>
					<li class="info_4_1"><span><s:property value="#commentInfoBean.problemInfoBean.problemId"/></span></li>
					<li class="title_4_1"><a href="javascript:void(0);" onclick="detail(<s:property value="#commentInfoBean.commentId" />)"><span><s:property value="#commentInfoBean.commentDesc" /></span></a></li>
					<li class="info_4_1"><span><s:property value="#commentInfoBean.userInfoBean.userName"/> </span></li>
					<li class="date_4_1"><span><s:date name="#commentInfoBean.operDate" /></span></li>
				</ul>
			</s:iterator>
		</div>
		
		<form method="post" id="searchForm" style="display: none;">
			<input name="problemId" id="problemId" value="" />
		</form>

		<%@include file="/pages/common/page.jsp"%>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>