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
		var oFCKeditor = new FCKeditor('answerDesc');
        oFCKeditor.BasePath = path+'/theme/fckeditor/';
        oFCKeditor.Width='100%';
        oFCKeditor.Height='400';
        //默认是default
        oFCKeditor.ToolbarSet='ZJUTOJ';
        oFCKeditor.ReplaceTextarea() ;
        
   		$('#selectId').select({'targetId': 'downSelect'});
   		
   		var method = '${param.method}';
   		if(method == 'saveUI') {
   			$('#selectId').val('');
   		}
	});
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path+'/study/answerInfoAction.do?method=mdetail&answerId='+data.obj;
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
				form.attr('action', path + '/study/answerInfoAction.do?method=save');
				ajaxFormSubmit('saveForm', success);
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
			var id = problemId.substring(startIndex, endIndex).trim();
			if(id == '') {
				show('selectId', '选择的题目格式不正确');
				flag = false;
			}else {
				$('#problemId').val(id);
			}
		}
		
		//校验消息内容
		var content = FCKeditorAPI.GetInstance('answerDesc').GetXHTML(true);
        if(content == null || content == ''){
			showTip('answerDescDiv', '请输入消息内容');
	    	flag = false;
	  	}
        $('#answerDesc').val(content);
		
		return flag;
	}
	
	function reset() {
		location.reload();
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_admin.jsp"%>

	<div class="content">
		<div class="title">
			<h2>题解信息修改</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="save()">保存</a></li>
				<li><a href="javascript:void(0);" onclick="reset()">重置</a></li>
			</ul> 
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm" enctype="multipart/form-data">
				<input name="answerId" value="<s:property value="#answerInfoBean.answerId"/>" type="hidden"/>
				<input name="problemInfoBean.problemId" id="problemId" type="hidden" />
				<table>
					<tr>
						<td width="13%"><span>题目信息：</span></td>
						<td><input id="selectId" readonly="readonly" value="【<s:property value="#answerInfoBean.problemInfoBean.problemId" />】<s:property value="#answerInfoBean.problemInfoBean.problemTitle" />" ></input></td>
					</tr>
					<tr>
						<td><span>题解内容：</span></td>
						<td id="answerDescDiv">
							<textarea name="answerDesc" id="answerDesc"><s:property value="#answerInfoBean.answerDesc" /></textarea>
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