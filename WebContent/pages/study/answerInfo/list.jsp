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
		var searchType = '${param.searchType}==1' ? 1 : -1;
		location.href = path + '/study/answerInfoAction.do?method=list&pageNo='+pageNo+'&searchType='+searchType;
	}
	
	function detail(id) {
		location.href = path + '/study/answerInfoAction.do?method=detail&answerId='+id;
	}
	
	function save() {
		location.href = path + '/study/answerInfoAction.do?method=saveUI';
	}
	
	function searchList() {
		var type = $('#search_type').val();
		location.href = path + '/study/answerInfoAction.do?method=list&searchType='+type;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_judge.jsp"%>
	
	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />
	
	<div class="content">
		<div class="title">
			<h2>题解列表</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li>
					创建人:
					<select id="search_type">
						<option value="-1" <s:property value="%{(#searchType == -1) ? 'selected' : ''}" />>全部</option>
						<option value="1" <s:property value="%{(#searchType == 1) ? 'selected' : ''}" />>自己</option>
					</select>
				</li>
				<li><a href="javascript:void(0);" onclick="searchList()">搜索</a></li>
				<li><a href="javascript:void(0)" onclick="save()">新增</a></li>
			</ul>
		</div>
		<div class="context" id="context">
				<ul>
					<li class="info_4_1 list_bg"><span>题目编号</span></li>
					<li class="title_4_1 list_bg"><span>题目标题</span></li>
					<li class="info_4_1 list_bg"><span>是否审核</span></li>
					<li class="date_4_1 list_bg"><span>上传日期</span></li>
				</ul>
			<s:iterator value="#pageQueryResult.list" id="answerInfo" status="stats">
				<ul>
					<li class="info_4_1"><span><s:property value="#answerInfo.problemInfoBean.problemId"/></span></li>
					<li class="title_4_1"><a href="javascript:void(0);" onclick="detail(<s:property value="#answerInfo.answerId" />)"><s:property value="#answerInfo.problemInfoBean.problemTitle" /></a></li>
					<li class="info_4_1"><span><s:property value="%{#answerInfo.isShow == 0 ? '未审核' : '通过'}" /></span></li>
					<li class="date_4_1"><span><s:date name="#answerInfo.operDate" /></span></li>
				</ul>
			</s:iterator>
		</div>
		<form method="post" id="saveForm" style="display: none;">
			<input name="pageNo" value="<s:property value="#pageNo"/>"/>
			<input name="problemId" id="problemId" value="" />
		</form>

		<%@include file="/pages/common/page.jsp"%>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>