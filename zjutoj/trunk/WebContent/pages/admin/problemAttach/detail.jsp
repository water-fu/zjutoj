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
		location.href = path+'/study/problemInfoAction.do?method=mlist&pageNo='+pageNo;
	}
	
	function modifyProblem() {
		var id = $('#problemId').val();
		location.href = path + '/study/problemInfoAction.do?method=modifyUI&problemId='+id;
	}
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
			var url = path + '/study/problemInfoAction.do?method=mlist&pageNo='+pageNo;
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function deleteProblem() {
		jConfirm('是否确认删除?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在删除,请稍等', false);
				
				var form = $('#saveForm');
				form.attr('action', path + '/study/problemInfoAction.do?method=delete');
			    ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function statusProblem(flag) {
		var msg = '是否确认审核通过?';
		if(flag == 0) {
			msg = '是否确认审核不通过?';
		}
		jConfirm(msg, '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在修改,请稍等', false);
				
				var form = $('#saveForm');
				form.attr('action', path + '/study/problemInfoAction.do?method=status&flag='+flag);
				ajaxFormSubmit('saveForm', statusSucc);
			}
		});
	}
	
	function statusSucc(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.reload();
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
</script>
</head>
<body>
	<%@include file="/pages/common/top_admin.jsp"%>

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
				<c:choose>
					<c:when test="${problemInfoBean.isShow == 1}">
						<li><a href="javascript:void(0);" onclick="statusProblem(0);">拒绝</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="javascript:void(0);" onclick="statusProblem(1);">通过</a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="javascript:void(0);" onclick="modifyProblem();">修改</a></li>
				<li><a href="javascript:void(0);" onclick="deleteProblem();">删除</a></li>
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