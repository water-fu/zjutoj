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
<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/detail.css" />
<script type="text/javascript">
	$(document).ready(function() {
		
	});

	function deleteMailTemp(id) {
		jConfirm('是否确认删除?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在删除,请稍等', false);
				
				$('#tempId').val(id);
				var form = $('#tempForm');
				form.attr('action', path + '/system/mailTempAction.do?method=delete');
				ajaxFormSubmit('tempForm', success);
			}
		});
	}
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
			var url = path + '/system/mailTempAction.do?method=mlist&pageNo='+pageNo;
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function modifyMailTemp(id) {
		location.href = path + '/system/mailTempAction.do?method=modifyUI&tempId='+id;
	}
	
	function back() {
		var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
		location.href = path + '/system/mailTempAction.do?method=mlist&pageNo='+pageNo;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>

	<div class="content">
		<div class="title">
			<h2><s:property value="#mailTempBean.tempTitle" /></h2>
		</div>
		<div class="toolbar">
			<ul>
				<oj:UserPrivJudgeTag menuSign="MAILTEMPMOD">
					<li><a href="javascript:void(0);" onclick="modifyMailTemp(<s:property value="#mailTempBean.tempId"/>)">修改</a></li>
				</oj:UserPrivJudgeTag>
				<oj:UserPrivJudgeTag menuSign="MAILTEMPDEL">
					<li><a href="javascript:void(0);" onclick="deleteMailTemp(<s:property value="#mailTempBean.tempId"/>)">删除</a></li>
				</oj:UserPrivJudgeTag>
				<li><a href="javascript:void(0);" onclick="back();">返回</a></li>
			</ul>
		</div>
		<div class="context not_top" id="context">
			<p><b>【邮件模版主题】</b></p>
			<p style="text-indent: 4em"><s:property value="#mailTempBean.tempTitle" escape="false" /></p>
			<p><b>【邮件模版内容】</b></p>
			<div style="text-indent: 3em"><s:property value="#mailTempBean.tempContent" escape="false" /></div>
		</div>
	</div>
	<form method="post" id="tempForm" style="display: none;">
		<input name="tempId" id="tempId" value="<s:property value="#mailTempBean.tempId"/>" />
	</form>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>