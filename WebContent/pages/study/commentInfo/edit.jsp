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
	$().ready(function() {
		$('#selectId').select({'targetId': 'downSelect'});
	});

	function saveSucc(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path+'/study/commentInfoAction.do?method=detail&commentId='+data.obj;
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
				form.attr('action', path + '/study/commentInfoAction.do?method=save');
				ajaxFormSubmit('saveForm', saveSucc);
			}
		});
	}
	
	function validate() { 
		hideTip();
		var flag = true;
        
		var problemId = $('#selectId').val();
		if(problemId == null || problemId == '') {
			showTip('selectId', '题目不能为空');
			flag = false;
		}else {
			var startIndex = problemId.indexOf('【') + 1;
			var endIndex = problemId.indexOf('】');
			var id = problemId.substring(startIndex, endIndex);
			if(id == '') {
				show('selectId', '选择的题目格式不正确');
				flag = false;
			}else {
				$('#problemId').val(id);
			}
		}
		
		var desc = $('#commentDesc').val();
		if(desc == null || desc == '') {
			showTip('commentDesc', '主题不能为空');
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
	<%@include file="/pages/common/top_judge.jsp"%>

	<div class="content">
		<div class="title">
			<h2>评论主题新增</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="save()">保存</a></li>
				<li><a href="javascript:void(0);" onclick="reset()">重置</a></li>
			</ul> 
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm">
				<input type="hidden" name="problemInfoBean.problemId" id="problemId" />
				<input style="display: none;"/>
				<table>
					<tr>
						<td width="13%"><span>题目:</span></td>
						<td><input id="selectId" readonly="readonly"></input></td>
					</tr>
					<tr>
						<td><span>内容：</span></td>
						<td>
							<textarea name="commentDesc" id="commentDesc" ></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="downSelect" class="downSelect">
		<ul>
			<s:iterator value="#problemIdList" var="problemId">
				<li><s:property value="#problemId"></s:property></li>
			</s:iterator>
		</ul>
	</div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>