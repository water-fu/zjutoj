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
			var url = path + '/study/problemInfoAction.do?method=data&problemId='+${problemInfoBean.problemId};
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function deleteProblem(id) {
		jConfirm('是否确认删除?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在删除,请稍等', false);
				
				$('#attachId').val(id);
				var form = $('#saveForm');
				form.attr('action', path + '/study/problemAttachAction.do?method=delete');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function modifyProblem(id) {
		location.href = path + '/study/problemAttachAction.do?method=modifyUI&attachId='+id;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_admin.jsp"%>
	
	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />
	
	<div class="content">
		<div class="title">
			<h2>题目附件列表</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="<%=path%>/study/problemAttachAction.do?method=uploadUI&problemId=<s:property value="#problemInfoBean.problemId"/>">上传</a></li>
			</ul>
		</div>
		<div class="context" id="context">
				<ul>
					<li class="info_4 list_bg"><span>试题编号</span></li>
					<li class="title_4 list_bg"><span>附件名称</span></li>
					<li class="info_4 list_bg"><span>上传时间</span></li>
					<li class="info_4 list_bg"><span>操作</span></li>
				</ul>
			<s:iterator value="#problemInfoBean.problemAttachs" id="problemAttach" status="stats">
				<ul>
					<li class="info_4"><span><s:property value="#problemInfoBean.problemId"/></span></li>
					<li class="title_4"><span><s:property value="#problemAttach.attachName"/></span></li>
					<li class="info_4"><span><s:date name="#problemAttach.operDate" format="yyyy-MM-dd" /></span></li>
					<li class="info_4"><span>
						<a href="javascript:void(0);" onclick="modifyProblem(<s:property value="#problemAttach.attachId" />)">修改&nbsp;</a>
						<a href="javascript:void(0);" onclick="deleteProblem(<s:property value="#problemAttach.attachId" />)">删除&nbsp;</a>
						</span></li>
				</ul>
			</s:iterator>
		</div>
		<form method="post" id="saveForm" style="display: none;">
			<input name="attachId" id="attachId" value="" />
		</form>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>