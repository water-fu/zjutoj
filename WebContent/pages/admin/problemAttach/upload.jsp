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
	$().ready(function() {
		$('.fileUpload a').bind('click', function() {
        	appendFile(this);
        });
	});
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			var url = path + '/study/problemInfoAction.do?method=data&problemId=${param.problemId}';
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function upload() {
		if(!validate()) {
			return false;
		}
		
		var msg = '是否确认保存文件?';
		jConfirm(msg, '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在修改,请稍等', false);
				
				var form = $('#saveForm');
				form.attr('action', path + '/study/problemAttachAction.do?method=upload');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function validate() {
		hideTip();
		var flag = true;
		
		$('input[name="file"]').each(function(i) {
        	var option = {
    				extArr: ['.TXT']
    			};
        	if(!$(this).fileExtendVal(option)) {
        		showTip($(this), '请上传TXT格式的附件');
        		flag = false;
        	}else {
	        	if(!$(this).fileSizeVal()) {
	        		showTip($(this), '请上传小于2M的附件');
	        		flag = false;
	        	};
        	}
        });
		
		return flag;
	}
	
	function appendFile(el) {
		var td = $($(el).parent().parent()[0]);
		var p = $('<p>'), input = $('<input>'), a = $('<a>');
		input.attr('type', 'file');
		input.attr('name', 'file');
		a.attr('href', 'javascript:void(0);');
		a.html('新增');
		p.append(input, a);
		td.append(p);
		a.bind('click', function(){
			appendFile(this);
		});
		
		$(el).html('删除');
		$(el).unbind('click');
		$(el).bind('click', function(){
			$(el).parent().remove();
		});
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_admin.jsp"%>

	<div class="content">
		<div class="title">
			<h2>试题文件上传</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="upload();">上传</a></li>
			</ul>
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm" enctype="multipart/form-data">
				<input name="problemId" value="${param.problemId}" type="hidden"/>
				<table>
					<tr>
						<td width="13%"><span>附件：</span></td>
						<td class="fileUpload">
							<p>
								<input type="file" name="file" />
								<a href="javascript:void(0);">新增</a>
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