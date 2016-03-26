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
	function getPageData(pageNo) {
		location.href = path + '/system/messageInfoAction.do?method=mlist&messageType=${param.messageType}&pageNo=' + pageNo;
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
	
	function modifyMessage(id) {
		location.href = path + '/system/messageInfoAction.do?method=modifyUI&messageType=${param.messageType}&messageId='+id;
	}
	
	function detail(id) {
		location.href = path + '/system/messageInfoAction.do?method=mdetail&messageType=${param.messageType}&messageId='+id+'&pageNo=${param.pageNo}';
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>
	
	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />
	
	<div class="content">
		<div class="title">
			<h2><s:property value="#typeName"/></h2>
		</div>
		<div class="toolbar">
			<ul>
				<oj:UserPrivJudgeTag menuSign="${moduleSign}ADD">
					<li><a href="<%=path%>/system/messageInfoAction.do?method=saveUI&messageType=${param.messageType}">新增</a></li>
				</oj:UserPrivJudgeTag>
			</ul>
		</div>
		<div class="context" id="context">
			<s:iterator value="#pageQueryResult.list" id="messageInfoBean" status="stats">
				<ul>
					<li class="mtitle"><a href="javascript:void(0);" onclick="detail(<s:property value="#messageInfoBean.messageId" />)"><s:property value="#messageInfoBean.messageTitle" /></a></li>
					<li class="minfo"><span><s:property value="#messageInfoBean.userInfoBean.userName"/> </span></li>
					<li class="minfo"><span><s:date name="#messageInfoBean.operDate" format="yyyy-MM-dd HH:mm:ss" /> </span></li>
					<li class="mtool">
						<oj:UserPrivJudgeTag menuSign="${moduleSign}MOD">
							<a href="javascript:void(0);" onclick="modifyMessage(<s:property value="#messageInfoBean.messageId" />)">修改&nbsp;</a> 
						</oj:UserPrivJudgeTag>
						<oj:UserPrivJudgeTag menuSign="${moduleSign}DEL">
							<a href="javascript:void(0);" onclick="deleteMessage(<s:property value="#messageInfoBean.messageId" />)">&nbsp;删除</a>
						</oj:UserPrivJudgeTag>
					</li>
				</ul>
			</s:iterator>
		</div>
		<form method="post" id="messageForm" style="display: none;">
			<input name="pageNo" value="${param.pageNo}"/>
			<input name="messageType" value="${param.messageType}"/>
			<input name="messageId" id="messageId" value="" />
		</form>

		<%@include file="/pages/common/page.jsp"%>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>