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
		$('#userSchool').select({'targetId': 'downSelect'});
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
				form.attr('action', path + '/system/hustojUserAction.do?method=save');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function validate() { 
		hideTip();
		var flag = true;
		//校验学号
		var userNo = $('#userNo').val();
		if(userNo == null || userNo == '') {
			showTip('userNo', '请输入学号');
			flag = false;
		}else if(userNo.length > 30) {
			showTip('userNo', '学号最大长度30位');
			flag = false;
		}
		
		//校验姓名
		var userName = $('#userName').val();
		if(userName == null || userName == '') {
			showTip('userName', '请输入姓名');
			flag = false;
		}else if(userName.length > 30) {
			showTip('userName', '姓名最大长度30个中文');
			flag = false;
		}
		
		//校验班级
		var userClass = $('#userClass').val();
		if(userClass == null || userClass == '') {
			showTip('userClass', '请输入班级');
			flag = false;
		}else if(userClass.length > 30) {
			showTip('userClass', '班级最大长度30个中文');
			flag = false;
		}
		
		//校验学院
		var userSchool = $('#userSchool').val();
		if(userSchool == null || userSchool == '') {
			showTip('userSchool', '请输入学院');
			flag = false;
		}else if(userSchool.length > 30) {
			showTip('userSchool', '学院最大长度30个中文');
			flag = false;
		} else {
			// 校验学院是否选择了
			var isMatch = false;
			$('#downSelect li').each(function(i) {
				if(userSchool == $(this).text()) {
					isMatch = true;
				}
			});
			if(!isMatch) {
				showTip('userSchool', '请选择选项中的学院');
				flag = false;
			}
		}
		
		//校验联系邮箱
		var userMail = $('#userMail').val();
		if(userMail == null || userMail == '') {
			showTip('userMail', '请输入联系邮箱');
			flag = false;
		}else if(userMail.length > 30) {
			showTip('userMail', '联系邮箱最大长度30位');
			flag = false;
		}else if (!checkemail(userMail)) {
			showTip('userMail', '联系邮箱格式不正确');
			flag = false;
		}
		
		//校验联系电话
		var userTel = $('#userTel').val();
		if(userTel == null || userTel == '') {
			showTip('userTel', '请输入联系电话');
			flag = false;
		}else if(userTel.length > 30) {
			showTip('userTel', '联系电话最大长度30位');
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

	function reset() {
		location.reload();
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>

	<div class="content">
		<div class="title">
<!-- 		ACM-ICPC竞赛2015春季集训 -->
			<h2><s:property value="#hustojInfoBean.matchTitle" />报名</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="save()">报名</a></li>
				<li><a href="javascript:void(0);" onclick="reset()">重置</a></li>
			</ul> 
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm">
				<input name="matchId" value="<s:property value="#hustojInfoBean.matchId"/>" type="hidden"/>
				<table>
					<tr>
						<td colspan="4">
							报名截至时间:<s:date name="#hustojInfoBean.signEnd" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<font color="red">注意：邮箱、手机号、学号是唯一的，不能填错,否则会导致报名无效</font>
						</td>
					</tr>
					<tr>
						<td width="13%"><span>学号：</span></td>
						<td><input id="userNo" name="userNo" value="<s:property value="#hustojUserBean.userNo" />" ></input></td>
						<td><span>姓名：</span></td>
						<td><input id="userName" name="userName" value="<s:property value="#hustojUserBean.userName" />" ></input></td>
					</tr>
					<tr>
						<td><span>学院：</span></td>
						<td><input id="userSchool" name="userSchool" value="<s:property value="#hustojUserBean.userSchool" />" ></input></td>
						<td><span>班级：</span></td>
						<td><input id="userClass" name="userClass" value="<s:property value="#hustojUserBean.userClass" />" ></input></td>
					</tr>
					<tr>
						<td><span>联系邮箱：</span></td>
						<td><input id="userMail" name="userMail" value="<s:property value="#hustojUserBean.userMail" />" ></input></td>
						<td><span>联系电话：</span></td>
						<td><input id="userTel" name="userTel" value="<s:property value="#hustojUserBean.usertel" />" ></input></td>
					</tr>
					<tr>
						<td valign="top"><span>获奖经历：</span></td>
						<td colspan="3" ><span><textarea id="userExp" name="userExp" maxlength="900"
						  placeholder="最多900个字符"  value="<s:property value="#hustojUserBean.userExp" />" ></textarea></span></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<div id="downSelect" class="downSelect">
		<ul>
			<s:iterator value="#schoolList" var="school">
				<li><s:property value="#school"></s:property></li>
			</s:iterator>
		</ul>
	</div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>