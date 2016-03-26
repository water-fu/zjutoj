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
<link rel="stylesheet" type="text/css" href="<%=path %>/theme/datetimepicker/jquery.datetimepicker.css" />
<script type="text/javascript" src="<%=path%>/theme/datetimepicker/jquery.datetimepicker.js"></script>

<script type="text/javascript">
	$().ready(function() {
		$('#signFrom').datetimepicker();
        $('#signEnd').datetimepicker();
	});
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path+'/system/hustojUserAction.do?method=mlist&matchId='+data.obj;
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
				form.attr('action', path + '/system/hustojInfoAction.do?method=save');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function validate() { 
		hideTip();
		var flag = true;
		//校验标题
		var title = $('#matchTitle').val();
		if(title == null || title == '') {
			showTip('matchTitle', '请输入竞赛标题');
			flag = false;
		}else if(title.length > 40) {
			showTip('mathchTitle', '竞赛标题最大长度128个中文');
			flag = false;
		}
		
		var startTime = $('#signFrom').val(), endTime = $('#signEnd').val();
		if(startTime == null || startTime == '') {
			showTip('signFrom', '请输入开始时间');
			flag = false;
		}
		if(endTime == null || endTime == '') {
			showTip('signEnd', '请输入开始时间');
			flag = false;
		}
		else if(startTime > endTime) {
			showTip('signEnd', '结束时间必须大于开始时间');
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
	<%@include file="/pages/common/top.jsp"%>

	<div class="content">
		<div class="title">
			<h2>竞赛信息新增</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="save()">保存</a></li>
				<li><a href="javascript:void(0);" onclick="reset()">重置</a></li>
			</ul> 
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm">
				<input name="matchId" value="<s:property value="#hustojInfoBean.matchId"/>" type="hidden"/>
				<table>
					<tr>
						<td width="13%"><span>竞赛标题：</span></td>
						<td colspan="3"><input id="matchTitle" name="matchTitle" value="<s:property value="#hustojInfoBean.matchTitle" />" ></input></td>
					</tr>
					<tr>
						<td><span>报名开始时间：</span></td>
						<td>
							<input id="signFrom" name="signFromIn" type="text" readonly="readonly" value="<s:date name="#hustojInfoBean.signFrom" format="yyyy/MM/dd HH:mm" />"></input>
						</td>
						<td><span>报名结束时间：</span></td>
						<td>
							<input id="signEnd" name="signEndIn" type="text" readonly="readonly" value="<s:date name="#hustojInfoBean.signEnd" format="yyyy/MM/dd HH:mm" />"></input>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>