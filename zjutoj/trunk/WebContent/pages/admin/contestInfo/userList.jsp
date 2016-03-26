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
	
	function selectUser(id) {
		jConfirm('是否确认添加?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在提交,请稍等', false);
				
				$('#userId').val(id);
				var form = $('#saveForm');
				form.attr('action', path + '/match/contestUserAction.do?method=save');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function deleteUser(id) {
		jConfirm('是否确认删除?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在提交,请稍等', false);
				
				$('#userId').val(id);
				var form = $('#saveForm');
				form.attr('action', path + '/match/contestUserAction.do?method=delete');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}

	function getPageData(pageNo) {
		$('#userSearchSign').val($('#search_sign').val());
		$('#selected').val($('#is_selected').val());
		var form = $('#searchForm');
		form.attr('action', path + '/match/contestInfoAction.do?method=userList&pageNo='+pageNo);
		form.submit();
	}
	
	function searchList() {
		$('#userSearchSign').val($('#search_sign').val());
		$('#selected').val($('#is_selected').val());
		var form = $('#searchForm');
		form.attr('action', path + '/match/contestInfoAction.do?method=userList');
		form.submit();
	}
	
	function importUser() {
		location.href = path + '/match/contestInfoAction.do?method=importUser&contestId=${contestInfoBean.contestId}';
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
				<li>登录名:<input type="text" id="search_sign" value="${searchBean.loginSign}"></input></li>
				<li>
					是否选择:
					<select id="is_selected">
						<option <s:property value="%{(#selected == 1) ? 'selected' : '' }" /> value="1">已选择</option>
						<option <s:property value="%{(#selected == 0) ? 'selected' : '' }" /> value="0">未选择</option>
					</select>
				</li>
				<li><a href="javascript:void(0);" onclick="searchList()">搜索</a></li>
				<li><a href="javascript:void(0);" onclick="importUser()">导入</a></li>
			</ul>
		</div>
		<div class="context" id="context">
				<ul>
					<li class="info_5_1 list_bg"><span>用户编号</span></li>
					<li class="title_5_1 list_bg"><span>登录名</span></li>
					<li class="info_5_1 list_bg"><span>用户姓名</span></li>
					<li class="info_5_1 list_bg"><span>用户邮箱</span></li>
					<li class="info_5_1 list_bg"><span>操作</span></li>
				</ul>
			<s:iterator value="#pageQueryResult.list" id="userInfoBean" status="stats">
				<ul id="ul_<s:property value="#userInfoBean.userId" />">
					<li class="info_5_1"><span><s:property value="#userInfoBean.userId"/></span></li>
					<li class="title_5_1"><span><s:property value="#userInfoBean.loginSign"/></span></li>
					<li class="info_5_1"><span><s:property value="#userInfoBean.userName"/></span></li>
					<li class="info_5_1"><span><s:property value="#userInfoBean.userMail"/></span></li>
					<li class="info_5_1"><span>
						<s:if test="%{#selected == 0}">
							<a href="javascript:void(0);" onclick="selectUser(<s:property value="#userInfoBean.userId" />)">添加</a>
						</s:if>
						<s:elseif test="%{#selected == 1}">
							<a href="javascript:void(0);" onclick="deleteUser(<s:property value="#userInfoBean.userId" />)">删除</a>
						</s:elseif>
					</span></li>
				</ul>
			</s:iterator>
		</div>
		<form method="post" id="saveForm" style="display: none;">
			<input name="pageNo" value="<s:property value="#pageNo"/>"/>
			<input name="userId" id="userId" value="" />
			<input name="contestId" id="contestId" value="${contestInfoBean.contestId}"/>
		</form>
		
		<form method="post" id="searchForm" style="display: none;">
			<input name="userInfoBean.userSign" id="userSearchSign" value="" />
			<input name="selected" id="selected" value="" />
			<input name="contestId" value="${contestInfoBean.contestId}"/>
		</form>
		
		<%@include file="/pages/common/page.jsp"%>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>