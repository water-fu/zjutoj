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
		location.href = path + '/system/hustojUserAction.do?method=mlist&matchId=${hustojInfoBean.matchId}&pageNo=' + pageNo;
	}
	
	function success(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.reload();
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
	
	function detail(id) {
		location.href = path + '/system/hustojUserAction.do?method=mdetail&userId='+id+'&pageNo=${param.pageNo}';
	}
	
	function importData() {
		if(!validate()) {
			return false;
		}
		
		jConfirm('确定需要导入?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在导入,请稍等', false);
				
				$('#tableName').val('system_hustoj_user');
				$('#dataRow').val(1);
				
				var form = $('#saveForm');
				form.attr('action', path + '/common/importExcelData.do?method=importData');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function importFinalData(){
		if(!validate()) {
			return false;
		}
		
		jConfirm('确定需要导入?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在导入,请稍等', false);
				
				$('#tableName').val('final_hustoj_user');
				$('#dataRow').val(0);
				
				var form = $('#saveForm');
				form.attr('action', path + '/common/importExcelData.do?method=importData');
				ajaxFormSubmit('saveForm', success);
			}
		});
	}
	
	function validate() { 
		hideTip();
		var flag = true;
		
		var filePath = $('#file').val();
		if(filePath == null || filePath == '') {
			showTip('file', '请选择文件', true);
			flag = false;
		}
		
		if(!$('#file').fileExtendVal({extArr: ['.XLS']})) {
    		showTip('file', '请上传xls格式的文件');
    		flag = false;
    	}
		
		return flag;
	}
	
	function back() {
		location.href = path + '/system/hustojInfoAction.do?method=mlist';
	}
	
	//导出
	function exportData() {
		jConfirm('确定需要导出?', '提示信息', function(r) {
			if(r == true) {
				var form = $('#saveForm');
				form.attr('action', path + '/system/hustojUserAction.do?method=exportData');
				form.submit();
			}
		});
	}
	
	// 生成报名链接
	function genSignLink() {
		jConfirm('确定需要生成报名链接?', '提示信息', function(r) {
			if(r == true) {
				showMask();
				errorTip('context', '正在生成,请稍等', false);
				
				var form = $('#saveForm');
				form.attr('action', path + '/system/hustojUserAction.do?method=genSignLink');
				ajaxFormSubmit('saveForm', signLinkSuccess);
			}
		});
	}
	
	// 生成链接成功
	function signLinkSuccess(data) {
		if(typeof data === 'string') {
			data = eval('(' + data + ')');
		}
		if(data.code == 1) {
			location.href = path + data.obj;
		}else if(data.code == 2) {
			location.href = path + data.obj;
		}else {
			hideMask();
			hideTip();
			errorTip('context', data.msg, false);
		}
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>
	
	<s:set name="pageNo" value="#pageQueryResult.pageNo" />
	<s:set name="totalPage" value="#pageQueryResult.totalPage" />
	<s:set name="firstRowNo" value="%{(#pageNo - 1) * #pageQueryResult.pageSize + 1}" />
	
	<div class="content">
		<div class="title">
			<h2><s:property value="#hustojInfoBean.matchTitle" />报名申请列表</h2>
		</div>
		<div class="stas2">
			<ul>
				<li>报名开始时间 : <s:date name="#hustojInfoBean.signFrom" format="yyyy-MM-dd HH:mm:ss" /></li>
				<li>报名结束时间 : <s:date name="#hustojInfoBean.signEnd" format="yyyy-MM-dd HH:mm:ss" /></li>
			</ul>
		</div>
		<div class="toolbar" style="height: 55px !important;">
			<form method="post" id="saveForm">
				<input id="tableName" name="tableName" value="system_hustoj_user" type="hidden" />
				<input name="sheetAt" value="0" type="hidden" />
				<input id="dataRow" name="dataRow" value="1" type="hidden" />
				<input id="matchId" name="matchId" value="<s:property value="#hustojInfoBean.matchId" />" type="hidden" />
				<ul>
					<oj:UserPrivJudgeTag menuSign="HUSTOJUSERIMPORT">
						<li><input type="file" id="file" name="excelFile" /></li>
						<li><a href="<%=path%>/pages/template/system_hustoj_user.xls">Excel模版下载</a></li>
						<li><a href="javascript:void(0)" onclick="importData()">导入账号</a></li>
						<li><a href="javascript:void(0)" onclick="importFinalData()">导入决赛用户</a></li>
					</oj:UserPrivJudgeTag>
					<oj:UserPrivJudgeTag menuSign="HUSTOJUSEREXPORT">
						<li><a href="javascript:void(0);" onclick="exportData()">导出用户</a></li>
					</oj:UserPrivJudgeTag>
					<li><a href="javascript:void(0);" onclick="genSignLink()">生成报名链接</a></li>
					<li><a href="javascript:void(0);" onclick="back()">返回</a></li>
				</ul>
			</form>
		</div>
		<div class="context" id="context">
			<ul>
				<li class="info_1 list_bg"><span>序号</span></li>
				<li class="info_5_1 list_bg"><span>学号</span></li>
				<li class="info_5_1 list_bg"><span>姓名</span></li>
				<li class="info_5_1 list_bg"><span>学院</span></li>
				<li class="info_5_1 list_bg"><span>班级</span></li>
				<li class="date_4_1 list_bg"><span>邮箱</span></li>
				<li class="date_4_1 list_bg"><span>联系电话</span></li>
			</ul>
			<s:iterator value="#pageQueryResult.list" id="userojUserBean" status="stats">
				<ul>
					<li class="info_1"><s:property value="%{#firstRowNo + #stats.index}" /> </li>
					<li class="info_5_1"><a href="javascript:void(0);"><s:property value="#userojUserBean.userNo" /></a></li>
					<li class="info_5_1"><span><s:property value="#userojUserBean.userName"/> </span></li>
					<li class="info_5_1"><span><s:property value="#userojUserBean.userSchool"/> </span></li>
					<li class="info_5_1"><span><s:property value="#userojUserBean.userClass"/> </span></li>
					<li class="date_4_1"><span><s:property value="#userojUserBean.userMail"/> </span></li>
					<li class="date_4_1"><span><s:property value="#userojUserBean.userTel"/> </span></li>
				</ul>
			</s:iterator>
		</div>

		<%@include file="/pages/common/page.jsp"%>
	</div>
	<div style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>
	
	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>