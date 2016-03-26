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
			<h2>2015省赛报名结束</h2>
		</div>
		<div class="context" id="context">
			<p>2015省赛报名申请已关闭，不再接收报名</p>
		</div>
	</div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>