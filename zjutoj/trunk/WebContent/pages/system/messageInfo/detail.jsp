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
<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/zoom.css" />
<script type="text/javascript" src="<%=jsPath %>/jquery.zoom.js"></script>
<script type="text/javascript">
	function deleteMessage(id) {
		jConfirm('是否确认删除?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在删除,请稍等', false);
				
				$('#messageId').val(id);
				var form = $('#messageForm');
				form.attr('action', path + '/system/messageInfoAction.do?method=delete');
				ajaxFormSubmit('messageForm', success);
			}
		});
	}
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
			var url = path + '/system/messageInfoAction.do?method=mlist&messageType=${param.messageType}&pageNo='+pageNo;
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function modifyMessage(id) {
		location.href = path + '/system/messageInfoAction.do?method=modifyUI&messageType=<s:property value="#messageInfoBean.messageType" />&messageId='+id;
	}
	
	function back() {
		var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
		location.href = path + '/system/messageInfoAction.do?method=mlist&messageType=${param.messageType}&pageNo='+pageNo;
	}
	
	function downFile(el) {
		window.open(path+'/system/messageInfoAction.do?method=downFile&id='+$(el).attr('url'));
	}
	
	$(document).ready(function() {
		$('.large').fancyZoom({closeOnClick: true});
	});
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>

	<div class="content">
		<div class="title">
			<h2><s:property value="#messageInfoBean.messageTitle" /></h2>
		</div>
		<div class="stas">
			<ul>
				<li>Publisher : <s:property value="#messageInfoBean.userInfoBean.userName" /></li>
				<li>Time : <s:date name="#messageInfoBean.operDate" format="yyyy-MM-dd HH:mm:ss" /></li>
				<li>PV : <s:property value="#messageInfoBean.viewCount" /></li>
			</ul>
		</div>
		<div class="toolbar">
			<ul>
				<oj:UserPrivJudgeTag menuSign="${moduleSign}MOD">
					<li><a href="javascript:void(0);" onclick="modifyMessage(<s:property value="#messageInfoBean.messageId"/>)">修改</a></li>
				</oj:UserPrivJudgeTag>
				<oj:UserPrivJudgeTag menuSign="${moduleSign}DEL">
					<li><a href="javascript:void(0);" onclick="deleteMessage(<s:property value="#messageInfoBean.messageId"/>)">删除</a></li>
				</oj:UserPrivJudgeTag>
				<li><a href="javascript:void(0);" onclick="back();">返回</a></li>
			</ul>
		</div>
		<div class="context" id="context">
			<s:property value="#messageInfoBean.messageContent" escape="false" />
			<s:iterator value="#messageInfoBean.messageAttchs" id="messageAttachBean" status="stats">
				<s:if test="#messageAttachBean.attachType == 1">
					<a href="#img_<s:property value="#stats.index" />" class="large">
						<img src="<%=path %>/system/messageInfoAction.do?method=showImage&url=<s:property value="#messageAttachBean.attachUrl" />" alt="<s:property value="#messageAttachBean.attachName" />" />
					</a>
				</s:if>
			</s:iterator>
			<s:iterator value="#messageInfoBean.messageAttchs" id="messageAttachBean" status="stats">
				<s:if test="#messageAttachBean.attachType == 2">
					<p>
						<a href="javascript:void(0)" onclick="downFile(this)" url="<s:property value="#messageAttachBean.attachId" />">
							<s:property value="#messageAttachBean.attachName" />
						</a>
					</p>
				</s:if>
			</s:iterator>
		</div>
	</div>
	<form method="post" id="messageForm" style="display: none;">
		<input name="messageType" value="${param.messageType}"/>
		<input name="messageId" id="messageId" value="${param.messageId}" />
	</form>
	<s:iterator value="#messageInfoBean.messageAttchs" id="messageAttachBean" status="stats">
		<s:if test="#messageAttachBean.attachType == 1">
			<div id="img_<s:property value="#stats.index" />" class="realImg">
				<img src="<%=path %>/system/messageInfoAction.do?method=showImage&url=<s:property value="#messageAttachBean.attachUrl" />" alt="<s:property value="#messageAttachBean.attachName" />" />
			</div>
		</s:if>
	</s:iterator>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>