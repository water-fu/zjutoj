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
<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/edit.css" />
<script type="text/javascript">
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			var url = path + '/study/problemInfoAction.do?method=data&problemId='+data.obj;
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function save() {
		if(!validate()) {
			return false;
		}
		
		var msg = '是否确认保存?';
		jConfirm(msg, '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在修改,请稍等', false);
				
				var form = $('#saveForm');
				form.attr('action', path + '/study/problemAttachAction.do?method=save');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function validate() {
		hideTip();
		var flag = true;
		
		return flag;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_admin.jsp"%>

	<div class="content">
		<div class="title">
			<h2>试题文件修改</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="save();">保存</a></li>
			</ul>
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm">
				<input name="attachId" value="${param.attachId}" type="hidden"/>
				<table>
					<tr>
						<td width="13%"><span>文件内容：</span></td>
						<td>
							<p>
								<textarea rows="10" cols="10" name="fileData"><s:property value="#fileData" /></textarea>
							</p>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>