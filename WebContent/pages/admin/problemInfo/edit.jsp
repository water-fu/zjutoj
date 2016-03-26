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
		var oFCKeditor = new FCKeditor('problemDesc');
        oFCKeditor.BasePath = path+'/theme/fckeditor/';
        oFCKeditor.Width='100%';
        oFCKeditor.Height='300';
        //默认是default
        oFCKeditor.ToolbarSet='ZJUTOJ';
        oFCKeditor.ReplaceTextarea() ;
        
        var oFCKeditor = new FCKeditor('problemInput');
        oFCKeditor.BasePath = path+'/theme/fckeditor/';
        oFCKeditor.Width='100%';
        oFCKeditor.Height='300';
        //默认是default
        oFCKeditor.ToolbarSet='ZJUTOJ';
        oFCKeditor.ReplaceTextarea() ;
        
        var oFCKeditor = new FCKeditor('problemOutput');
        oFCKeditor.BasePath = path+'/theme/fckeditor/';
        oFCKeditor.Width='100%';
        oFCKeditor.Height='300';
        //默认是default
        oFCKeditor.ToolbarSet='ZJUTOJ';
        oFCKeditor.ReplaceTextarea() ;
        
        var oFCKeditor = new FCKeditor('exampleInput');
        oFCKeditor.BasePath = path+'/theme/fckeditor/';
        oFCKeditor.Width='100%';
        oFCKeditor.Height='300';
        //默认是default
        oFCKeditor.ToolbarSet='ZJUTOJ';
        oFCKeditor.ReplaceTextarea() ;
        
        var oFCKeditor = new FCKeditor('exampleOutput');
        oFCKeditor.BasePath = path+'/theme/fckeditor/';
        oFCKeditor.Width='100%';
        oFCKeditor.Height='300';
        //默认是default
        oFCKeditor.ToolbarSet='ZJUTOJ';
        oFCKeditor.ReplaceTextarea() ;

        $('#type').select({'targetId': 'downSelect'});
	});
	
	function saveSucc(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path+'/study/problemInfoAction.do?method=mdetail&problemId='+data.obj;
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
				form.attr('action', path + '/study/problemInfoAction.do?method=save');
// 				form.submit();
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
			showTip('title', '标题不能为空');
			flag = false;
		}else if(title.length > 40) {
			showTip('title', '标题长度不能大于40');
			flag = false;
		}
		
		//时间限制
		var timeLimit = $('#timeLimit').val();
		if(timeLimit == null || timeLimit == '') {
			showTip('timeLimit', '时间限制不能为空');
			flag = false;
		}else if(!isJavaInteger(timeLimit)) {
			showTip('timeLimit', '请输入正整数,且不能超过2147483647');
			flag = false;
		}
		
		//内存限制
		var memLimit = $('#memLimit').val();
		if(memLimit == null || memLimit == '') {
			showTip('memLimit', '内存限制不能为空');
			flag = false;
		}else if(!isJavaInteger(memLimit)) {
			showTip('memLimit', '请输入正整数,且不能超过2147483647');
			flag = false;
		}
		
		//内存限制
		var type = $('#type').val();
		if(type == null || type == '') {
			showTip('type', '试题类型不能为空');
			flag = false;
		}else if(type.length > 40) {
			showTip('type', '试题类型不能超过40个字');
			flag = false;
		}
		
		//校验描述信息
		var desc = FCKeditorAPI.GetInstance('problemDesc').GetXHTML(true);
        if(desc == null || desc == ''){
			showTip('descDiv', '请输入描述内容');
	    	flag = false;
	  	}
        $('#problemDesc').val(desc);
        
      	//校验输入信息
		var input = FCKeditorAPI.GetInstance('problemInput').GetXHTML(true);
        if(input == null || input == ''){
			showTip('inputDiv', '请填写输入内容');
	    	flag = false;
	  	}
        $('#problemInput').val(input);
        
      	//校验输出信息
		var output = FCKeditorAPI.GetInstance('problemOutput').GetXHTML(true);
        if(output == null || output == ''){
			showTip('outDiv', '请填写输出内容');
	    	flag = false;
	  	}
        $('#problemOutput').val(output);
        
      	//校验输入示例信息
		var exinput = FCKeditorAPI.GetInstance('exampleInput').GetXHTML(true);
        if(exinput == null || exinput == ''){
			showTip('exInputDiv', '请输入示例输入内容');
	    	flag = false;
	  	}
        $('#exampleInput').val(exinput);
        
      	//校验输出示例信息
		var exoutput = FCKeditorAPI.GetInstance('exampleOutput').GetXHTML(true);
        if(exoutput == null || exoutput == ''){
			showTip('exOutputDiv', '请输入示例输出内容');
	    	flag = false;
	  	}
        $('#exampleOutput').val(exoutput);
		
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
			<form method="post" id="saveForm" enctype="multipart/form-data">
				<input name="problemId" value="<s:property value="#problemInfoBean.problemId"/>" type="hidden"/>
				<table>
					<tr>
						<td width="13%"><span>标题：</span></td>
						<td colspan="3"><input id="title" title="试题标题" name="problemTitle" value="<s:property value="#problemInfoBean.problemTitle" />" ></input></td>
					</tr>
					<tr>
						<td width="13%"><span>时间(MS)：</span></td> 
						<td><input id="timeLimit" title="执行时间限制,单位MS" name="timeLimit" value="<s:property value="#problemInfoBean.timeLimit" />" ></input></td>
						<td width="13%"><span>内存(KByte)：</span></td>
						<td><input id="memLimit" title="执行内存限制,单位KByte" name="memLimit" value="<s:property value="#problemInfoBean.memLimit" />" ></input></td>
					</tr>
					<tr>
						<td width="13%"><span>试题类型：</span></td>
						<td><input id="type" title="试题类型" name="problemType" value="<s:property value="#problemInfoBean.problemType" />" ></input></td>
					</tr>
					<tr>
						<td><span>描述：</span></td>
						<td id="descDiv" colspan="3">
							<textarea name="problemContentBean.problemDesc" id="problemDesc" ><s:property value="#problemInfoBean.problemContentBean.problemDesc" /></textarea>
						</td>
					</tr>
					<tr>
						<td><span>输入：</span></td>
						<td id="inputDiv" colspan="3">
							<textarea name="problemContentBean.problemInput" id="problemInput" ><s:property value="#problemInfoBean.problemContentBean.problemInput" /></textarea>
						</td>
					</tr>
					<tr>
						<td><span>输出：</span></td>
						<td id="outputDiv" colspan="3">
							<textarea name="problemContentBean.problemOutput" id="problemOutput" ><s:property value="#problemInfoBean.problemContentBean.problemOutput" /></textarea>
						</td>
					</tr>
					<tr>
						<td><span>示例输入：</span></td>
						<td id="exInputDiv" colspan="3">
							<textarea name="problemContentBean.exampleInput" id="exampleInput" ><s:property value="#problemInfoBean.problemContentBean.exampleInput" /></textarea>
						</td>
					</tr>
					<tr>
						<td><span>示例输出：</span></td>
						<td id="exOutputDiv" colspan="3">
							<textarea name="problemContentBean.exampleOutput" id="exampleOutput" ><s:property value="#problemInfoBean.problemContentBean.exampleOutput" /></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<div id="downSelect" class="downSelect">
		<ul>
			<s:iterator value="#typeList" var="typeName">
				<li><s:property value="#typeName"></s:property></li>
			</s:iterator>
		</ul>
	</div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>