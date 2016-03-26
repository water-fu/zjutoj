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
<script type="text/javascript" src="<%=path%>/theme/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="<%=path%>/theme/datetimepicker/jquery.datetimepicker.js"></script>

<script type="text/javascript">
	$().ready(function() {
		var oFCKeditor = new FCKeditor('contestDesc') ;
        oFCKeditor.BasePath = path+'/theme/fckeditor/';
        oFCKeditor.Width='98%';
        oFCKeditor.Height='300';
        //默认是default
        oFCKeditor.ToolbarSet='ZJUTOJ';
        oFCKeditor.ReplaceTextarea() ;
        
        $('#startTime').datetimepicker();
        $('#endTime').datetimepicker();
        
        //修改设置level
        var level = '${contestInfoBean.contestLevel}';
        if(level == null || level == '') {
        	level = 0;
        }
        $('#contestLevel').val(level);
        
        var language = '${contestInfoBean.languageInfo}';
        var selArr = [];
        if(language != '') {
        	var lanArr = language.split(',');
        	for(var i = 0; i < lanArr.length; i++) {
        		var lan = lanArr[i];
        		if(lan != '') {
	        		selArr.push(lan);
        		}
        	}
        }
        $('#language').val(selArr);
	});
	
	function saveSucc(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path+'/match/contestInfoAction.do?method=mdetail&contestId='+data.obj;
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
				form.attr('action', path + '/match/contestInfoAction.do?method=save');
				ajaxFormSubmit('saveForm', saveSucc);
			}
		});
	}
	
	function validate() { 
		hideTip();
		var flag = true;
		//校验标题
		var title = $('#title').val();
		if(title == null || title == '') {
			showTip('title', '请输入竞赛标题');
			flag = false;
		}else if(title.length > 40) {
			showTip('title', '竞赛标题最大长度40个中文');
			flag = false;
		}
		
		var startTime = $('#startTime').val(), endTime = $('#endTime').val();
		if(startTime == null || startTime == '') {
			showTip('startTime', '请输入开始时间');
			flag = false;
		}
		if(endTime == null || endTime == '') {
			showTip('endTime', '请输入开始时间');
			flag = false;
		}
		else if(startTime > endTime) {
			showTip('endTime', '结束时间必须大于开始时间');
			flag = false;
		}
		
		var language = $('#language').val();
		if(language == null || language == '') {
			showTip('language', '请选择竞赛语言');
			flag = false;
		}
		$('#contestLanguage').val(language);
		
		//校验消息内容
		var content = FCKeditorAPI.GetInstance('contestDesc').GetXHTML(true);
        if(content == null || content == ''){
			showTip('contestDiv', '请输入竞赛描述');
	    	flag = false;
	  	}
        $('#contestDesc').val(content);
        
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
			<h2><s:property value="#titleName"></s:property></h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="save()">保存</a></li>
				<li><a href="javascript:void(0);" onclick="reset()">重置</a></li>
			</ul> 
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm">
				<input name="contestId" value="<s:property value="#contestInfoBean.contestId"/>" type="hidden"/>
				<table>
					<tr>
						<td width="13%"><span>竞赛标题：</span></td>
						<td colspan="3"><input id="title" name="contestTitle" value="<s:property value="#contestInfoBean.contestTitle" />" ></input></td>
					</tr>
					<tr>
						<td width="13%"><span>开始时间：</span></td>
						<td><input id="startTime" name="startTimeIn" type="text" readonly="readonly" value="<s:date name="#contestInfoBean.startTime" format="yyyy/MM/dd HH:mm" />"></input></td>
						<td width="13%"><span>结束时间：</span></td>
						<td><input id="endTime" name="endTimeIn" type="text" readonly="readonly" value="<s:date name="#contestInfoBean.endTime" format="yyyy/MM/dd HH:mm" />"></input></td>
					</tr>
					<tr>
						<td width="13%"><span>竞赛权限：</span></td>
						<td>
							<select name="contestLevel" id="contestLevel">
								<option value="0">Private</option>
								<option value="1">Public</option>
							</select>
						</td>
						<td width="13%"><span>竞赛语言：</span></td>
						<td>
							<select name="language" id="language" multiple="multiple" class="multiple">
								<option value="C">C</option>
								<option value="C++">C++</option>
								<option value="JAVA">JAVA</option>
								<option value="PHP">PHP</option>
								<option value="PYTHON">PYTHON</option>
							</select>
							<input name="contestLanguage" id="contestLanguage" type="hidden"></input>
						</td>
					</tr>
					<tr>
						<td><span>竞赛描述：</span></td>
						<td id="contestDiv" colspan="3">
							<textarea name="contestDesc" id="contestDesc" ><s:property value="#contestInfoBean.contestDesc" /></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>