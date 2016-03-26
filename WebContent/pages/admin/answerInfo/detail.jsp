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
	function modify(id) {
		location.href = path + '/study/answerInfoAction.do?method=mmodifyUI&answerId='+id;
	}
	
	function deleteAnswer(id) {
		jConfirm('是否确认删除?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在删除,请稍等', false);
				
				$('#answerId').val(id);
				var form = $('#answerForm');
				form.attr('action', path + '/study/answerInfoAction.do?method=delete');
				ajaxFormSubmit('answerForm', success);
			}
		});
	}
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
			var url = path + '/study/answerInfoAction.do?method=mlist&pageNo='+pageNo;
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function show(id, flag) {
		jConfirm('是否确认显示?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在处理,请稍等', false);
				
				$('#answerId').val(id);
				$('#isShow').val(flag);
				var form = $('#answerForm');
				form.attr('action', path + '/study/answerInfoAction.do?method=show');
				ajaxFormSubmit('answerForm', showSucc);
			}
		});
	}
	
	function showSucc(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.reload();
		}else if(data.code == 2) {
			location.href = path + data.url;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_admin.jsp"%>

	<div class="content">
		<div class="title">
			<h2>【<s:property value="#answerInfoBean.problemInfoBean.problemId" />】<s:property value="#answerInfoBean.problemInfoBean.problemTitle" /></h2>
		</div>
		<div class="toolbar">
			<ul>
				<s:if test="#answerInfoBean.isShow == 0">
					<li><a href="javascript:void(0);" onclick="show(<s:property value="#answerInfoBean.answerId" />, 1);">审核通过</a></li>
				</s:if>
				<s:else>
					<li><a href="javascript:void(0);" onclick="show(<s:property value="#answerInfoBean.answerId" />, 0);">审核不通过</a></li>
				</s:else>
				<li><a href="javascript:void(0);" onclick="modify(<s:property value="#answerInfoBean.answerId" />);">修改</a></li>
				<li><a href="javascript:void(0);" onclick="deleteAnswer(<s:property value="#answerInfoBean.answerId" />);">删除</a></li>
			</ul>
		</div>
		<div class="context not_top" id="context">
			<s:property value="#answerInfoBean.answerDesc" escape="false" />
		</div>
	</div>
	<form method="post" id="answerForm" style="display: none;">
		<input name="answerId" id="answerId" value="${answerInfoBean.answerId}" />
		<input name="isShow" id="isShow" />
	</form>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>