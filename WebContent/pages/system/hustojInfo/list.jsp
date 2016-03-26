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
		location.href = path + '/system/hustojInfoAction.do?method=mlist&pageNo=' + pageNo;
	}
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			var pageNo = '${param.pageNo}==""' ? 1 : '${param.pageNo}';
			var url = path + '/system/hustojInfoAction.do?method=mlist&pageNo='+pageNo;
			location.href = url;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function deleteHustojInfo(id) {
		jConfirm('是否确认删除?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在删除,请稍等', false);
				
				$('#matchId').val(id);
				var form = $('#hustojInfoForm');
				form.attr('action', path + '/system/hustojInfoAction.do?method=delete');
				ajaxFormSubmit('hustojInfoForm', success);
			}
		});
	}
	
	function modifyHustojInfo(id) {
		location.href = path + '/system/hustojInfoAction.do?method=modifyUI&matchId='+id;
	}
	
	function detail(id) {
		location.href = path + '/system/hustojUserAction.do?method=mlist&matchId='+id;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>
	
	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />
	
	<div class="content">
		<div class="title">
			<h2>竞赛列表</h2>
		</div>
		<div class="toolbar">
			<ul>
<%-- 				<oj:UserPrivJudgeTag menuSign="HUSTOJINFOADD"> --%>
					<li><a href="<%=path%>/system/hustojInfoAction.do?method=saveUI">新增</a></li>
<%-- 				</oj:UserPrivJudgeTag> --%>
			</ul>
		</div>
		<div class="context" id="context">
			<s:iterator value="#pageQueryResult.list" id="hustojInfoBean" status="stats">
				<ul>
					<li class="mtitle"><a href="javascript:void(0);" onclick="detail(<s:property value="#hustojInfoBean.matchId" />)"><s:property value="#hustojInfoBean.matchTitle" /></a></li>
					<li class="minfo"><span><s:date name="#hustojInfoBean.signFrom" format="yyyy-MM-dd HH:mm:ss" /> </span></li>
					<li class="minfo"><span><s:date name="#hustojInfoBean.signEnd" format="yyyy-MM-dd HH:mm:ss" /> </span></li>
					<li class="mtool">
						<oj:UserPrivJudgeTag menuSign="HUSTOJINFOMOD">
							<a href="javascript:void(0);" onclick="modifyHustojInfo(<s:property value="#hustojInfoBean.matchId" />)">修改&nbsp;</a> 
						</oj:UserPrivJudgeTag>
						<oj:UserPrivJudgeTag menuSign="HUSTOJINFODEL">
							<a href="javascript:void(0);" onclick="deleteHustojInfo(<s:property value="#hustojInfoBean.matchId" />)">&nbsp;删除</a>
						</oj:UserPrivJudgeTag>
					</li>
				</ul>
			</s:iterator>
		</div>
		<form method="post" id="hustojInfoForm" style="display: none;">
			<input name="pageNo" value="${param.pageNo}"/>
			<input name="matchId" id="matchId" value="" />
		</form>

		<%@include file="/pages/common/page.jsp"%>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>