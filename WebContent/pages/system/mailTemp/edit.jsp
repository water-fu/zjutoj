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
		var oFCKeditor = new FCKeditor('tempContent') ;
        oFCKeditor.BasePath = path+'/theme/fckeditor/';
        oFCKeditor.Width='100%';
        oFCKeditor.Height='300';
        //默认是default
        oFCKeditor.ToolbarSet='ZJUTOJ';
        oFCKeditor.ReplaceTextarea() ;
        
        $('#tempType').val('${mailTempBean.tempType}');
	});
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path+'/system/mailTempAction.do?method=mdetail&tempId='+data.obj;
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
				form.attr('action', path + '/system/mailTempAction.do?method=save');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function validate() { 
		hideTip();
		var flag = true;
		//校验标题
		var title = $('#tempTitle').val();
		if(title == null || title == '') {
			showTip('tempTitle', '请输入邮件模版主题');
			flag = false;
		}else if(title.length > 40) {
			showTip('tempTitle', '邮件模版主题最大长度128个中文');
			flag = false;
		}
		
		//校验消息内容
		var content = FCKeditorAPI.GetInstance('tempContent').GetXHTML(true);
        if(content == null || content == ''){
			showTip('tempDiv', '请输入邮件模版内容');
	    	flag = false;
	  	}
        $('#tempContent').val(content);
        
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
			<h2>邮件模版新增</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="save()">保存</a></li>
				<li><a href="javascript:void(0);" onclick="reset()">重置</a></li>
			</ul> 
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm">
				<input name="tempId" value="<s:property value="#mailTempBean.tempId"/>" type="hidden"/>
				<table>
					<tr>
						<td colspan="2">
							<font color="red">注意：邮件主题内容可以使用\${userName}和\${mail}代替用户名和邮箱名,\${matchAccount}和\${matchPassword}替换账号密码</font>
						</td>
					</tr>
					<tr>
						<td width="13%"><span>邮件类型：</span></td>
						<td>
							<select name="tempType" id="tempType">
								<option value="1">申请成功邮件</option>
								<option value="2">密码发送邮件</option>
								<option value="3">决赛用户邮件</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="13%"><span>模版主题：</span></td>
						<td><input id="tempTitle" name="tempTitle" value="<s:property value="#mailTempBean.tempTitle" />" ></input></td>
					</tr>
					<tr>
						<td><span>模版内容：</span></td>
						<td id="tempDiv">
							<textarea name="tempContent" id="tempContent" ><s:property value="#mailTempBean.tempContent" /></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>