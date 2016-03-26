<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/common/include.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>浙江工业大学ACM大学生程序设计竞赛网站</title>
<link rel="icon" href="<%=themePath%>/images/icpc_logo.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=themePath%>/images/icpc_logo.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/edit.css" />

<script type="text/javascript">
	var isMultiply = false;
	
	function saveSucc(data) {
		var method = '${param.method}';
		if(data.code == 1) {
			if(method == 'loginUI') {
				location.href = path+'/system/messageInfoAction.do?method=initIndexPage';
			}else {
				location.reload();
			}
		}else {
			errorTip('context', data.msg);
		}
	}
	
	function save() {
		if(!validate()) {
			return false;
		}
		var form = $('#saveForm');
		form.attr('action', path + '/system/loginInfoAction.do?method=login');
		ajaxFormSubmit('saveForm', saveSucc);
	}
	
	function validate() { 
		hideTip();
		var flag = true;
		
		var userName = $('#loginSign').val();
		if(userName == null || userName == '') {
			showTip('loginSign', '用户名不能为空');
			flag = false;
		}
		var userPass = $('#loginPass').val();
		if(userPass == null || userPass == '') {
			showTip('loginPass', '密码不能为空');
			flag = false;
		}
		else if(userPass.length < 8 || userPass.length > 18) {
			showTip('loginPass', '密码为8-18位');
			flag = false;
		}
		
		return flag;
	}
	
	function reset() {
		location.reload();
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>

	<div class="content">
		<div class="title">
			<h2>系统登录</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="save()">登录</a></li>
				<li><a href="javascript:void(0);" onclick="reset()">重置</a></li>
			</ul> 
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm">
				<input name="userType" value="1" type="hidden"/>
				<table>
					<tr>
						<td width="13%"><span>用户名:</span></td>
						<td>
							<input type="text" name="loginSign" id="loginSign" />
						</td>
					</tr>
					<tr>
						<td><span>密码：</span></td>
						<td>
							<input type="password" name="loginPass" id="loginPass" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
    
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>