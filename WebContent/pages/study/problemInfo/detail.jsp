<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/common/include.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="oj" uri="/WEB-INF/oj-tags.tld"%>
<html>
<head>
<title>浙江工业大学ACM大学生程序设计竞赛网站</title>
<link rel="icon" href="<%=themePath%>/images/icpc_logo.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=themePath%>/images/icpc_logo.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/detail.css" />
<script type="text/javascript">
	function back() {
		var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
		location.href = path+'/study/problemInfoAction.do?method=list&pageNo='+pageNo;
	}
	
	function comment(id) {
		location.href = path + '/study/commentInfoAction.do?method=list&problemId='+id;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_judge.jsp"%>

	<div class="content">
		<div class="title">
			<h2><s:property value="#problemInfoBean.problemTitle" /></h2>
		</div>
		<div class="stas4">
			<ul>
				<li>Time Limit: <s:property value="#problemInfoBean.timeLimit" />MS</li>
				<li>Memory Limit: <s:property value="#problemInfoBean.memLimit" />KByte</li>
				<li>Submit: 500</li>
				<li>Solved: 112</li>
			</ul>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="comment(<s:property value="#problemInfoBean.problemId" />);">论坛</a></li>
				<li><a href="javascript:void(0);" onclick="back();">返回</a></li>
			</ul>
		</div>
		<div class="context" id="context">
			<p class="content_h2">Description</p> 
			<div class="content_p"><s:property value="#problemInfoBean.problemContentBean.problemDesc" escape="false" /></div>
			<p class="content_h2">Input</p> 
			<div class="content_p"><s:property value="#problemInfoBean.problemContentBean.problemInput" escape="false" /></div>
			<p class="content_h2">Output</p> 
			<div class="content_p"><s:property value="#problemInfoBean.problemContentBean.problemOutput" escape="false" /></div>
			<p class="content_h2">Simple Input</p> 
			<div class="content_p"><s:property value="#problemInfoBean.problemContentBean.exampleInput" escape="false" /></div>
			<p class="content_h2">Simple Output</p> 
			<div class="content_p"><s:property value="#problemInfoBean.problemContentBean.exampleOutput" escape="false" /></div>
			<p class="content_h2">Hint</p> 
			<div class="content_p"><s:property value="#problemInfoBean.problemContentBean.problemHint" escape="false" /></div>
			<p class="content_h2">Source</p>
			<div class="content_p"><s:property value="#problemInfoBean.problemUserBean.userName" escape="false" /></div>
			<p class="content_h2">Type</p>
			<div class="content_p"><s:property value="#problemInfoBean.problemType" escape="false" /></div>
		</div>
	</div>
	<form method="post" id="saveForm" style="display: none;">
		<input name="problemId" id="problemId" value="${problemInfoBean.problemId}" />
	</form>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>