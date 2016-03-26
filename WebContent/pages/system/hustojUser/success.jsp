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
<script type="text/javascript" src="<%=path%>/theme/fckeditor/fckeditor.js"></script>

<script type="text/javascript">
	$().ready(function() {
		$('#saveForm').hide();
		$('#userMailTemp').hide();
		$('#cancelButton').hide();
		$('#saveButton').hide();
		
		$('#transMail').hide();
		$('#modifyButton').hide();
	});
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path+'/system/hustojUserAction.do?method=success&userId='+data.obj;
		} else if(data.code == 2) {
			location.href = path + data.obj;
		} else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function save() {
		if(!validate()) {
			return false;
		}
		jConfirm('是否确认保存?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在提交,请稍等', false);
				
				var form = $('#saveForm');
				form.attr('action', path + '/system/hustojUserAction.do?method=update');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function validate() { 
		hideTip();
		var flag = true;
		//校验联系邮箱
		var userMail = $('#userMailTemp').val();
		if(userMail == null || userMail == '') {
			showTip('userMailTemp', '请输入联系邮箱');
			flag = false;
		}else if(userMail.length > 30) {
			showTip('userMailTemp', '联系邮箱最大长度30位');
			flag = false;
		}else if(!checkemail(userMail)) {
			showTip('userMail', '联系邮箱格式不正确');
			flag = false;
		}
		
		return flag;
	}
	
	function checkemail(email){
		var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		ismail = reg.test(email);
		if (!ismail) {
			return false;
		}
		return true;
	}
	
	function modifyMail() {
		$('#userMailTemp').show();
		$('#modifyButton').hide();
		$('#cancelButton').show();
		$('#saveButton').show();
	}
	
	function cancelModify() {
		$('#userMailTemp').hide();
		$('#cancelButton').hide();
		$('#saveButton').hide();
		$('#modifyButton').show();
		$('#userMailTemp').val($('#userMail').val());
	}
	
	function saveModify() {
		$('#userMail').val($('#userMailTemp').val());
		save();
	}
	
	function transMail() {
		jConfirm('是否确认发送?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在发送,请稍等', false);
				
				var form = $('#saveForm');
				form.attr('action', path + '/system/hustojUserAction.do?method=transMail');
				ajaxFormSubmit('saveForm', successMail);
			}
		});
	}
	
	function successMail(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			hideMask();
			hideTip();
			errorTip('context', '邮件发送成功', true);
		} else if(data.code == 2) {
			location.href = path + data.obj;
		} else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>

	<div class="content">
		<div class="title">
			<h2><s:property value="#hustojInfoBean.matchTitle" /> 报名</h2>
		</div>
		<div class="context" id="context">
			<p align="left" style="text-indent: 2em"><s:property value="#hustojUserBean.userName"/>，您好：</p>
			<p align="left" style="text-indent: 4em">报名信息确认邮件已经发送至您的邮箱<font color="red" style="font-size: 20px"><s:property value="#hustojUserBean.userMail"/></font>，竞赛的账号密码在报名截至后会发送至您的邮箱。</p>
			<p align="left" style="text-indent: 4em">
			<button id="transMail" onclick="transMail()">&nbsp;&nbsp;重新发送&nbsp;&nbsp;</button>&nbsp;&nbsp;
			
			<input id="userMailTemp" value="<s:property value="#hustojUserBean.userMail" />"></input>
			<button id="modifyButton" onclick="modifyMail()">&nbsp;&nbsp;修改邮箱&nbsp;&nbsp;</button>
			
			<button id="saveButton" onclick="saveModify()">&nbsp;&nbsp;保存修改&nbsp;&nbsp;</button>
			<button id="cancelButton" onclick="cancelModify()">&nbsp;&nbsp;取消修改&nbsp;&nbsp;</button></p>
			
			<form method="post" id="saveForm">
				<input name="userId" value="<s:property value="#hustojUserBean.userId"/>" type="hidden"/>
				<table>
					<tr>
						<td><input id="userMail" name="userMail" value="<s:property value="#hustojUserBean.userMail" />" ></input></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>