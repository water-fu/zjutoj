<%@page import="java.util.Date"%>
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
<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/table.css" />
<script type="text/javascript">
	function back() {
		var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
		location.href = path+'/match/contestInfoAction.do?method=mlist&pageNo='+pageNo;
	}
	
	function modifyContest() {
		var id = $('#contestId').val();
		location.href = path + '/match/contestInfoAction.do?method=modifyUI&contestId='+id;
	}
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
			var url = path + '/match/contestInfoAction.do?method=mlist&pageNo='+pageNo;
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function deleteContest() {
		jConfirm('是否确认删除?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在删除,请稍等', false);
				
				var form = $('#saveForm');
				form.attr('action', path + '/match/contestInfoAction.do?method=delete');
			    ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function statusContest(flag) {
		var msg = '是否启用竞赛?';
		if(flag == 0) {
			msg = '是否禁用竞赛?';
		}
		jConfirm(msg, '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在修改,请稍等', false);
				
				var form = $('#saveForm');
				form.attr('action', path + '/match/contestInfoAction.do?method=status');
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
	
	$().ready(function() {
		getCurrentTime();
		setInterval("getCurrentTime()",1000);
	});
	
	function getCurrentTime() {
		var url = path + '/match/contestInfoAction.do?method=currentTime';
		$.get(url, function(data) {
			if(data.code == 1) {
				$('#currentTime').html(data.obj);
			}else {
				hideMask();
				hideTip();
				errorTip('context', data.msg, false);
			}
		});
	}
	
	function selectProblem() {
		var id = $('#contestId').val();
		var url = path + '/match/contestInfoAction.do?method=problemList&selected=0&contestId='+id;
		location.href = url;
	}
	
	function selectUser() {
		var id = $('#contestId').val();
		var url = path + '/match/contestInfoAction.do?method=userList&selected=0&contestId='+id;
		location.href = url;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_admin.jsp"%>

	<div class="content">
		<div class="title">
			<h2><s:property value="#contestInfoBean.contestTitle" /></h2>
		</div>
		<div class="toolbar"> 
			<ul>
				<li><a href="javascript:void(0);" onclick="selectProblem();">试题配置</a></li>
				<li><a href="javascript:void(0);" onclick="selectUser();">用户配置</a></li>
				<c:choose>
					<c:when test="${contestInfoBean.contestStatus == 1}">
						<li><a href="javascript:void(0);" onclick="statusContest(0);">禁用</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="javascript:void(0);" onclick="statusContest(1);">启用</a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="javascript:void(0);" onclick="modifyContest();">修改</a></li>
				<li><a href="javascript:void(0);" onclick="deleteContest();">删除</a></li>
				<li><a href="javascript:void(0);" onclick="back();">返回</a></li>
			</ul>
		</div>
		<div class="context not_font" id="context">
			<p class="contest_status">Start Time: <span style="color: green;"><s:date name="#contestInfoBean.startTime" format="yyyy-MM-dd HH:mm:ss" /></span>&nbsp;&nbsp;&nbsp;End Time: <span style="color: red;"><s:date name="#contestInfoBean.endTime" format="yyyy-MM-dd HH:mm:ss" /></span></p>
			<br/>
			<p class="contest_status">Current Time: <span id="currentTime"></span></p>
			<br/>
			<p class="contest_status">Status: <span style="color: red;"><s:property value="#contestInfoBean.statusDesc" /></span>&nbsp;&nbsp;&nbsp;<s:property value="%{#contestInfoBean.contestLevel == 0 ? 'Private' : 'Public'}" /></p>
			<br/>
			<p class="contest_status"><a href="javascript:void(0)">[Status]</a> <a href="javascript:void(0)">[Standing]</a> <a href="javascript:void(0)">[Statistics]</a></p>
			
			<br />
			<div class="table">
				<ul>
					<li class="info_5_1 list_bg"><span>Problem ID</span></li>
					<li class="title_5_1 list_bg"><span>Title</span></li>
					<li class="info_5_1 list_bg"><span>Source</span></li>
					<li class="info_5_1 list_bg"><span>AC</span></li>
					<li class="info_5_1 list_bg"><span>Commit</span></li>
				</ul>
				<s:iterator value="#contestInfoBean.problemInfos" id="contestProblemBean" status="stats">
					<ul>
						<li class="info_5_1"><span><s:property value="#contestProblemBean.problemInfoBean.problemId"/></span></li>
						<li class="title_5_1"><s:property value="#contestProblemBean.problemInfoBean.problemTitle" /></li>
						<li class="info_5_1"><span><s:property value="#contestProblemBean.problemInfoBean.userInfoBean.userName"/></span></li>
						<li class="info_5_1"><span>1</span></li>
						<li class="info_5_1"><span>11</span></li>
					</ul>
				</s:iterator>
			</div>
		</div>
	</div>
	<form method="post" id="saveForm" style="display: none;">
		<input name="contestId" id="contestId" value="${contestInfoBean.contestId}" />
	</form>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>