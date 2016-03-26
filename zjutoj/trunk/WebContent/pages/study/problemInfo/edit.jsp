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
		var oFCKeditor = new FCKeditor('messageContent') ;
        oFCKeditor.BasePath = path+'/theme/fckeditor/';
        oFCKeditor.Width='90%';
        oFCKeditor.Height='300';
        //默认是default
        oFCKeditor.ToolbarSet='ZJUTOJ';
        oFCKeditor.ReplaceTextarea() ;
        
        $('.fileUpload a').bind('click', function() {
        	appendFile(this);
        });
	});
	
	function saveSucc(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path+'/system/messageInfoAction.do?method=mdetail&messageType=${param.messageType}&messageId='+data.obj;
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
				
				var deleteAttachIds = '';
				$('.fileLink li').each(function(i) {
					if($(this).hasClass('deleted')) {
						deleteAttachIds += $(this).attr('id')+',';
					}
				});
				$('#deletedAttachIds').val(deleteAttachIds);
				var form = $('#saveForm');
				form.attr('action', path + '/system/messageInfoAction.do?method=save');
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
			showTip('title', '请输入消息标题');
			flag = false;
		}else if(title.length > 40) {
			showTip('title', '消息内容最大长度40个中文');
			flag = false;
		}
		
		//校验消息内容
		var content = FCKeditorAPI.GetInstance('messageContent').GetXHTML(true);
        if(content == null || content == ''){
			showTip('messageDiv', '请输入消息内容');
	    	flag = false;
	  	}
        $('#messageContent').val(content);
        
        $('input[name="image"]').each(function(i) {
        	if(!$(this).fileExtendVal()) {
        		showTip($(this), '请上传PNG、GIF、JPG、JPEG格式的图片');
        		flag = false;
        	}else {
	        	var option = {
	        			size : 1
	        	};
	        	if(!$(this).fileSizeVal(option)) {
	        		showTip($(this), '请上传小于1M的图片');
	        		flag = false;
	        	};
        	}
        });
        
        $('input[name="file"]').each(function(i) {
        	var option = {
    				extArr: ['.DOC', '.DOCX', '.XLS', '.XLSX', '.PDF']
    			};
        	if(!$(this).fileExtendVal(option)) {
        		showTip($(this), '请上传DOC、DOCX、XLS、XLSX、PDF格式的附件');
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
	
	function reset() {
		location.reload();
	}
	
	function showAttach(el, mode) {
		if(mode == 1) {
			window.open(path+'/system/messageInfoAction.do?method=showImage&url='+$(el).attr('url'));
		}else {
			window.open(path+'/system/messageInfoAction.do?method=downFile&id='+$(el).attr('url'));
		}
	}
	
	function deleteAttach(el, mode) {
		var msg = '是否确认删除图片?';
		if(mode == 2) {
			msg = '是否确认删除附件?';
		}
		jConfirm(msg, '提示信息', function(r) {
			if(r == true) {
				$('#'+$(el).attr('val')).css('display', 'none');
				$('#'+$(el).attr('val')).addClass('deleted');
			}
		});
	}
	
	function appendFile(el) {
		var mode = $(el).attr('val');
		
		var td = $($(el).parent().parent()[0]);
		var p = $('<p>'), input = $('<input>'), a = $('<a>');
		input.attr('type', 'file');
		if(mode == 1) {
			input.attr('name', 'image');
		}else {
			input.attr('name', 'file');
		}
		a.attr('href', 'javascript:void(0);');
		a.attr('val', mode);
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
	<%@include file="/pages/common/top.jsp"%>

	<div class="content">
		<div class="title">
			<h2><s:property value="#typeName"></s:property></h2>
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
						<td width="13%"><span>标题：</span></td>
						<td><input id="title" name="messageTitle" value="<s:property value="#messageInfoBean.messageTitle" />" ></input></td>
					</tr>
					<tr>
						<td><span>内容：</span></td>
						<td id="messageDiv">
							<textarea name="messageContent" id="messageContent" ><s:property value="#messageInfoBean.messageContent" /></textarea>
						</td>
					</tr>
					<tr>
						<td><span>已上传图片：</span></td>
						<td class="fileLink">
							<ul>
								<s:iterator value="#messageInfoBean.messageAttchs" id="messageAttachBean" status="stats">
									<s:if test="#messageAttachBean.attachType == 1">
										<li id="<s:property value="#messageAttachBean.attachId" />">
											<a href="javascript:void(0);" onclick="showAttach(this, 1)" url="<s:property value="#messageAttachBean.attachUrl" />">
												<s:property value="#messageAttachBean.attachName" />
											</a>
											<img src="<%=path %>/theme/default/images/cancel.png" alt="删除" title="删除" onclick="deleteAttach(this, 1)" val="<s:property value="#messageAttachBean.attachId" />" />
										</li>
									</s:if>
								</s:iterator>
							</ul>
						</td>
					</tr>
					<tr>
						<td><span>图片：</span></td>
						<td class="fileUpload">
							<p>
								<input type="file" name="image" />
								<a href="javascript:void(0);" val="1">新增</a>
							</p>
						</td>
					</tr>
					<tr>
						<td><span>已上传附件：</span></td>
						<td class="fileLink">
							<ul>
								<s:iterator value="#messageInfoBean.messageAttchs" id="messageAttachBean" status="stats">
									<s:if test="#messageAttachBean.attachType == 2">
										<li id="<s:property value="#messageAttachBean.attachId" />">
											<a href="javascript:void(0);" onclick="showAttach(this, 2)" url="<s:property value="#messageAttachBean.attachId" />">
												<s:property value="#messageAttachBean.attachName" />
											</a>
											<img src="<%=path %>/theme/default/images/cancel.png" alt="删除" title="删除" onclick="deleteAttach(this, 2)" val="<s:property value="#messageAttachBean.attachId" />" />
										</li>
									</s:if>
								</s:iterator>
							</ul>
						</td>
					</tr>
					<tr>
						<td><span>附件：</span></td>
						<td class="fileUpload">
							<p>
								<input type="file" name="file" />
								<a href="javascript:void(0);" val="2">新增</a>
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