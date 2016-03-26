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
<script type="text/javascript">
	function getPageData(pageNo) {
		location.href = path + '/study/commentInfoAction.do?method=detail&commentId=${param.commentId}&pageNo='+pageNo;
	}

	function back(id) {
		location.href = path+'/study/problemInfoAction.do?method=detail&problemId='+id;
	}
	
	function showComment(id, _this) {
		var subLi = $('#sub_li_'+id);
		if($('#sub_li_' + id + ':visible').length > 0) {
			$(_this).html('打开');
			subLi.hide();
		}else {
			$(_this).html('收起');
			subLi.show();
		}
	}
	
	function doPublish() {
		hideTip();
		var desc = $('#text_commentDesc').val();
		if(desc == null || desc == '') {
			showTip('text_commentDesc', '请填写评论');
			return;
		}
		
		jConfirm('是否确认评论?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('text_commentDesc', '正在提交,请稍等', false);
				
				$('#commentDesc').val(desc);
				var form = $('#saveForm');
				form.attr('action', path + '/study/commentInfoAction.do?method=save');
				ajaxFormSubmit('saveForm', saveSucc);
			}
		});
	}
	
	function saveSucc(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.reload();
		} else if(data.code == 2) {
			location.href = path + data.obj;
		} else {
			hideMask();
			hideTip();
			errorTip('text_commentDesc', data.msg, false);
		}
	}
	
	function subPublish(id) {
		hideTip();
		var desc = $('#input_commentDesc_'+id).val();
		if(desc == null || desc == '') {
			showTip('input_commentDesc_'+id, '请填写评论');
			return;
		}
		
		jConfirm('是否确认评论?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('input_commentDesc_'+id, '正在提交,请稍等', false);
				
				$('#commentDesc').val(desc);
				$('#commentId').val(id);
				var form = $('#saveForm');
				form.attr('action', path + '/study/commentInfoAction.do?method=save');
				ajaxFormSubmit('saveForm', saveSucc);
			}
		});
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top_judge.jsp"%>

	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />

	<div class="content not_font">
		<div class="title">
			<h2>【<s:property value="#commentInfoBean.problemInfoBean.problemId" />】<s:property value="#commentInfoBean.problemInfoBean.problemTitle" /></h2>
		</div>
		<div class="toolbar">
			<ul>
				<li><a href="javascript:void(0)" onclick="back(<s:property value="#commentInfoBean.problemInfoBean.problemId" />)">返回题目</a></li>
			</ul>
		</div>
		<div class="context not_font" id="context">
			<ul class="comment">
				<li>【楼主】</li>
				<li>发表人:<s:property value="#commentInfoBean.userInfoBean.userName"/></li>
				<li class="comment_content"><s:property value="#commentInfoBean.commentDesc" /></li>
				<li class="statBar"><s:date name="#commentInfoBean.operDate" /></li>
			</ul>
		
			<s:iterator value="#pageQueryResult.list" var="commentInfo">
				<ul class="comment">
					<li>发表人:<s:property value="#commentInfo.userInfoBean.userName"/></li>
					<li class="comment_content"><s:property value="#commentInfo.commentDesc" /></li>
					<li class="statBar"><s:date name="#commentInfo.operDate" /> <a href="javascript:void(0);" onclick="showComment(<s:property value="#commentInfo.commentId" />, this)">打开</a></li>
					<li class="sub_li" id="sub_li_<s:property value="#commentInfo.commentId" />">
						<s:iterator value="#commentInfo.childCommentInfo" var="childCommentInfo">
							<ul class="sub_comment">
								<li><s:property value="#childCommentInfo.userInfoBean.userName" />：<s:property value="#childCommentInfo.commentDesc" /></li>
								<li class="statBar"><s:date name="#childCommentInfo.operDate" />
								</li>
							</ul>
						</s:iterator>
						<ul class="sub_comment">
							<li><input id="input_commentDesc_<s:property value="#commentInfo.commentId" />"></input><a href="javascript:void(0);" onclick="subPublish(<s:property value="#commentInfo.commentId" />);" class="publish">发布</a></li>
						</ul>
					</li>
				</ul>
			</s:iterator>
			<ul class="comment">
				<li><textarea id="text_commentDesc" rows="5" cols="130"></textarea><a href="javascript:void(0);" onclick="doPublish();" class="publish">发布</a></li>
			</ul>
		</div>
		<%@include file="/pages/common/page.jsp"%>
	</div>
	
	<form method="post" id="saveForm" style="display: none;">
		<input name="commentId" id="commentId" value="${commentInfoBean.commentId}" />
		<input name="commentDesc" id="commentDesc"></input>
	</form>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>