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
        $('.fileUpload a').bind('click', function() {
        	appendFile(this);
        });
	});
	
	function saveSucc(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path+'/system/messageInfoAction.do?method=initIndexPage';
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg);
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
				
				var deleteAttachIds = '';
				$('.fileLink li').each(function(i) {
					if($(this).hasClass('deleted')) {
						deleteAttachIds += $(this).attr('id')+',';
					}
				});
				$('#deletedAttachIds').val(deleteAttachIds);
				var form = $('#saveForm');
				form.attr('action', path + '/system/picsInfoAction.do?method=save');
				ajaxFormSubmit('saveForm', saveSucc);
			}
		});
	}
	
	function validate() { 
		hideTip();
		var flag = true;
		
		$('input[name="image"]').each(function(i) {
        	// 如果图片为空，则提示删除
        	if($(this).val() == '') {
        		if($('input[name="image"]').length > 1) {
	        		showTip($(this), '图片未选择，选择图片或删除');
	        		flag = false;
        		}
        	}
        	else if(!$(this).fileExtendVal()) {
        		showTip($(this), '请上传PNG、GIF、JPG、JPEG格式的图片');
        		flag = false;
        	} else {
	        	var option = {
	        			size : 1
	        	};
	        	if(!$(this).fileSizeVal(option)) {
	        		showTip($(this), '请上传小于1M的图片');
	        		flag = false;
	        	};
        	}
        	
        });
		
		return flag;
	}
	
	function reset() {
		location.reload();
	}
	
	function deleteAttach(el) {
		var msg = '是否确认删除图片?';
		jConfirm(msg, '提示信息', function(r) {
			if(r == true) {
				$('#'+$(el).attr('val')).css('display', 'none');
				$('#'+$(el).attr('val')).addClass('deleted');
			}
		});
	}
	
	function showAttach(el) {
		window.open(path+'/system/messageInfoAction.do?method=showImage&url='+$(el).attr('url'));
	}
	
	function appendFile(el) {
		var td = $($(el).parent().parent()[0]);
		var p = $('<p>'), input = $('<input>'), a = $('<a>'), link = $('<input>');
		input.attr('type', 'file');
		input.attr('name', 'image');
		input.css('cssText', 'width: 40% !important');
		
		link.attr('type', 'text');
		link.attr('name', 'linkUrl');
		link.attr('placeholder', '图片链接');
		link.css('cssText', 'width: 50% !important');
		
		a.attr('href', 'javascript:void(0);');
		a.html('新增');
		p.append(input, link, a);
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
	<%@include file="/pages/common/top.jsp"%>

	<div class="content">
		<div class="title">
			<h2>系统首页图片设置</h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0);" onclick="save()">保存</a></li>
				<li><a href="javascript:void(0);" onclick="reset()">重置</a></li>
			</ul> 
		</div>
		<div class="context" id="context">
			<form method="post" id="saveForm" enctype="multipart/form-data">
				<input name="messageId" value="<s:property value="#messageInfoBean.messageId"/>" type="hidden"/>
				<input name="messageType" value="${param.messageType}" type="hidden"/>
				<input name="deletedAttachIds" id="deletedAttachIds" type="hidden"></input>
				<table>
					<tr>
						<td width="13%"><span>已上传图片：</span></td>
						<td class="fileLink">
							<ul>
								<s:iterator value="#picsInfoList" id="picsInfoBean" status="stats">
									<li id="<s:property value="#picsInfoBean.picId" />">
										<a href="javascript:void(0);" onclick="showAttach(this)" url="<s:property value="#picsInfoBean.picUrl" />">
											<s:property value="#picsInfoBean.picTitle" />
										</a>
										<img src="<%=path %>/theme/default/images/cancel.png" alt="删除" title="删除" onclick="deleteAttach(this)" val="<s:property value="#picsInfoBean.picId" />" />
									</li>
								</s:iterator>
							</ul>
						</td>
					</tr>
					<tr>
						<td><span>图片：</span></td>
						<td class="fileUpload">
							<p>
								<input type="file" name="image" style="width: 40% !important" />
								<input type="text" name="linkUrl" placeholder="图片链接" style="width: 50% !important" />
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