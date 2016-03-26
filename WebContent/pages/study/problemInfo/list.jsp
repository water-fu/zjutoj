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
		$('#problemSearchId').val($('#search_id').val());
		$('#problemSearchTitle').val($('#search_title').val());
		var form = $('#searchForm');
		form.attr('action', path + '/study/problemInfoAction.do?method=list&pageNo='+pageNo);
		form.submit();
	}
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			var url = path + '/system/messageInfoAction.do?method=list&pageNo=${param.pageNo}';
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function detail(id) {
		location.href = path + '/study/problemInfoAction.do?method=detail&problemId='+id;
	}
	
	function searchList() {
		$('#problemSearchId').val($('#search_id').val());
		$('#problemSearchTitle').val($('#search_title').val());
		$('#problemType').val($('#search_type').val());
		var form = $('#searchForm');
		form.attr('action', path + '/study/problemInfoAction.do?method=list');
		form.submit();
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_judge.jsp"%>
	
	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />
	
	<div class="content">
		<div class="title">
			<h2>题目列表</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li>PID:<input type="text" id="search_id" value="${searchBean.problemId==0?'':searchBean.problemId}"></input></li>
				<li>Title:<input type="text" id="search_title" value="${searchBean.problemTitle}"></input></li>
				<li>
					Type:
					<select id=search_type>
						<option value="">请选择</option>
						<s:iterator value="#typeList" var="typeVal">
							<option <s:property value="%{(#searchBean.problemType == #typeVal) ? 'selected' : ''}" />><s:property value="#typeVal" /></option>
						</s:iterator>
					</select>
				</li>
				<li><a href="javascript:void(0)" onclick="searchList()">搜索</a></li>
			</ul>
		</div>
		<div class="context" id="context">
				<ul>
					<li class="info_5 list_bg"><span>Problem ID</span></li>
					<li class="title_5 list_bg"><span>Title</span></li>
					<li class="info_5 list_bg"><span>Source</span></li>
					<li class="info_5 list_bg"><span>AC</span></li>
					<li class="info_5 list_bg"><span>Submit</span></li>
				</ul>
			<s:iterator value="#pageQueryResult.list" id="problemInfoBean" status="stats">
				<ul>
					<li class="info_5"><span><s:property value="#problemInfoBean.problemId"/></span></li>
					<li class="title_5"><a href="javascript:void(0);" onclick="detail(<s:property value="#problemInfoBean.problemId" />)"><s:property value="#problemInfoBean.problemTitle" /></a></li>
					<li class="info_5"><span><s:property value="#problemInfoBean.problemUserBean.userName"/> </span></li>
					<li class="info_5"><span>12</span></li>
					<li class="info_5"><span>120</span></li>
				</ul>
			</s:iterator>
		</div>
		<form method="post" id="saveForm" style="display: none;">
			<input name="pageNo" value="<s:property value="#pageNo"/>"/>
			<input name="problemId" id="problemId" value="" />
		</form>
		
		<form method="post" id="searchForm" style="display: none;">
			<input name="problemId" id="problemSearchId" value="" />
			<input name="problemTitle" id="problemSearchTitle" value="" />
			<input name="problemType" id="problemType" value="" />
		</form>

		<%@include file="/pages/common/page.jsp"%>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>