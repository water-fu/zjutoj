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
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			hideMask();
			hideTip();
			console.log($('#ul_'+data.obj));
			$('#ul_'+data.obj).hide();
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function selectProblem(id) {
		jConfirm('是否确认添加?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在提交,请稍等', false);
				
				$('#problemId').val(id);
				var form = $('#saveForm');
				form.attr('action', path + '/match/contestProblemAction.do?method=save');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function deleteProblem(id) {
		jConfirm('是否确认删除?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在提交,请稍等', false);
				
				$('#problemId').val(id);
				var form = $('#saveForm');
				form.attr('action', path + '/match/contestProblemAction.do?method=delete');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}

	function getPageData(pageNo) {
		$('#problemSearchId').val($('#search_id').val());
		$('#problemSearchTitle').val($('#search_title').val());
		$('#problemType').val($('#search_type').val());
		$('#selected').val($('#is_selected').val());
		var form = $('#searchForm');
		form.attr('action', path + '/match/contestInfoAction.do?method=problemList&pageNo='+pageNo);
		form.submit();
	}
	
	function searchList() {
		$('#problemSearchId').val($('#search_id').val());
		$('#problemSearchTitle').val($('#search_title').val());
		$('#problemType').val($('#search_type').val());
		$('#selected').val($('#is_selected').val());
		var form = $('#searchForm');
		form.attr('action', path + '/match/contestInfoAction.do?method=problemList');
		form.submit();
	}
	
	function detail(id) {
		//location.href = path + '/study/problemInfoAction.do?method=cpDetail&isSelect=${param.selected}&contestId=${contestInfoBean.contestId}&problemId='+id;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_admin.jsp"%>
	
	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />
	
	<div class="content">
		<div class="title">
			<h2><s:property value="titleName" /></h2>
		</div>
		<div class="toolbar">
			<ul>
				<li>试题编号:<input type="text" id="search_id" value="${searchBean.problemId==0?'':searchBean.problemId}"></input></li>
				<li>试题标题:<input type="text" id="search_title" value="${searchBean.problemTitle}"></input></li>
				<li>
					试题类型:
					<select id="search_type">
						<option value="">请选择</option>
						<s:iterator value="#typeList" var="typeVal">
							<option <s:property value="%{(#searchBean.problemType == #typeVal) ? 'selected' : ''}" />><s:property value="#typeVal" /></option>
						</s:iterator>
					</select>
				</li>
				<li>
					是否选择:
					<select id="is_selected">
						<option <s:property value="%{(#selected == 1) ? 'selected' : '' }" /> value="1">已选择</option>
						<option <s:property value="%{(#selected == 0) ? 'selected' : '' }" /> value="0">未选择</option>
					</select>
				</li>
				<li><a href="javascript:void(0);" onclick="searchList()">搜索</a></li>
			</ul>
		</div>
		<div class="context" id="context">
				<ul>
					<li class="info_5_1 list_bg"><span>PID</span></li>
					<li class="title_5_1 list_bg"><span>Title</span></li>
					<li class="info_5_1 list_bg"><span>Source</span></li>
					<li class="info_5_1 list_bg"><span>Status</span></li>
					<li class="info_5_1 list_bg"><span>Operate</span></li>
				</ul>
			<s:iterator value="#pageQueryResult.list" id="problemInfoBean" status="stats">
				<ul id="ul_<s:property value="#problemInfoBean.problemId" />">
					<li class="info_5_1"><span><s:property value="#problemInfoBean.problemId"/></span></li>
					<li class="title_5_1"><a href="javascript:void(0);" onclick="detail(<s:property value="#problemInfoBean.problemId" />)"><s:property value="#problemInfoBean.problemTitle" /></a></li>
					<li class="info_5_1"><span><s:property value="#problemInfoBean.problemUserBean.userName"/> </span></li>
					<li class="info_5_1"><span><s:property value="%{#problemInfoBean.isShow == 0 ? '未审核' : '通过'}"/></span></li>
					<li class="info_5_1"><span>
						<s:if test="%{#selected == 0}">
							<a href="javascript:void(0);" onclick="selectProblem(<s:property value="#problemInfoBean.problemId" />)">添加</a>
						</s:if>
						<s:elseif test="%{#selected == 1}">
							<a href="javascript:void(0);" onclick="deleteProblem(<s:property value="#problemInfoBean.problemId" />)">删除</a>
						</s:elseif>
					</span></li>
				</ul>
			</s:iterator>
		</div>
		<form method="post" id="saveForm" style="display: none;">
			<input name="pageNo" value="<s:property value="#pageNo"/>"/>
			<input name="problemId" id="problemId" value="" />
			<input name="contestId" id="contestId" value="${contestInfoBean.contestId}"/>
		</form>
		
		<form method="post" id="searchForm" style="display: none;">
			<input name="problemInfoBean.problemId" id="problemSearchId" value="" />
			<input name="problemInfoBean.problemTitle" id="problemSearchTitle" value="" />
			<input name="problemInfoBean.problemType" id="problemType" value="" />
			<input name="selected" id="selected" value="" />
			<input name="contestId" value="${contestInfoBean.contestId}"/>
		</form>
		
		<%@include file="/pages/common/page.jsp"%>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>