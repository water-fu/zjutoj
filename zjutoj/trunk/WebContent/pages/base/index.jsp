<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/common/include.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>浙江工业大学ACM大学生程序设计竞赛网站</title>
<link rel="icon" href="<%=themePath%>/images/icpc_logo.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=themePath%>/images/icpc_logo.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/home.css" />
<script type="text/javascript" src="<%=jsPath %>/myfocus-plugin.js"></script>
<script type="text/javascript">
	function toDetail(messageNo) {
		location.href = path + '/system/messageInfoAction.do?method=detail&messageId=' + messageNo;
	}
</script>
</head>
<body>
	<%@include file="/pages/common/top.jsp"%>
	
	<div class="content">
		<div class="adimages" id="boxId" style="visibility:hidden">
			<div class="loading">
				<img src="<%=themePath %>/images/loading.gif" />
			</div>
			<div class="pic">
				<ul>
				<s:iterator value="#picsInfoList" id="picsInfoBean" status="stats">
					<li>  
						<a href="<s:property value="#picsInfoBean.linkUrl"/>" target="_bank"><img src="<%=path%>/system/messageInfoAction.do?method=showImage&url=<s:property value="#picsInfoBean.picUrl"/>" alt="&nbsp;"/></a>
					</li>
				</s:iterator>
				</ul>
			</div>
		</div><!-- images结束 -->

		<div class="main">
			<div class="news">
				<div class="title">
					<h2 class="title_left">通知公示</h2>
					<span class="title_right"><a href="<%=path%>/system/messageInfoAction.do?method=list&messageType=1">More&gt;&gt;</a></span>
				</div>
				<s:iterator value="#resultList" id="result" status="stats">
					<s:if test="#stats.index == 0">
<%-- 						<s:iterator value="#result.list" var="messageInfo" status="st"> --%>
<%-- 							<s:if test="#st.index == 0"> --%>
<!-- 								<div class="news_detail"> -->
<!-- 									<img src="images/news.jpg" /> -->
<%-- 									<h2><a href="javascript:void(0);" onclick="toDetail(<s:property value="#messageInfo.messageId"/>)"> <s:property value="#messageInfo.messageTitle" /> </a></h2> --%>
<%-- 									<span><s:date name="#messageInfo.operDate" format="yyyy-M-dd" /></span> --%>
<!-- 									<p> -->
<%-- 										<s:if test="#messageInfo.messageContent.length() > 80"> --%>
<%-- 											<s:property value="#messageInfo.messageContent.substring(0, 80)" />...... --%>
<%-- 										</s:if> --%>
<%-- 										<s:else> --%>
<%-- 											<s:property value="#messageInfo.messageContent" /> --%>
<%-- 										</s:else> --%>
<!-- 									</p> -->
<!-- 								</div> -->
<%-- 							</s:if> --%>
<%-- 						</s:iterator> --%>
						
						<div class="news_list">
							<ul>
								<s:iterator value="#result.list" var="messageInfo" status="st">
									<li>
										<span><s:date name="#messageInfo.createDate" format="yyyy-M-dd" /></span>
										<s:if test="#messageInfo.messageTitle.length() > 20">
											<a href="javascript:void(0);" onclick="toDetail(<s:property value="#messageInfo.messageId"/>)"><s:property value="#messageInfo.messageTitle.substring(0, 18)" />...</a>
										</s:if>
										<s:else>
											<a href="javascript:void(0);" onclick="toDetail(<s:property value="#messageInfo.messageId"/>)"><s:property value="#messageInfo.messageTitle" /></a>
										</s:else>
									</li>
								</s:iterator>
							</ul>
						</div>
					</s:if>
				</s:iterator>
			</div><!-- news结束 -->

			<div class="course">
				<div class="title">
					<h2 class="title_left">赛事简介</h2>
					<span class="title_right"><a href="<%=path%>/system/messageInfoAction.do?method=list&messageType=2">More&gt;&gt;</a></span>
				</div>
				<s:iterator value="#resultList" id="result" status="stats">
					<s:if test="#stats.index == 1">
<%-- 						<s:iterator value="#result.list" var="messageInfo" status="st"> --%>
<%-- 							<s:if test="#st.index == 0"> --%>
<!-- 								<div class="news_detail"> -->
<!-- 									<img src="images/news.jpg" /> -->
<%-- 									<h2><a href="javascrpit:void(0);" onclick="toDetail(<s:property value="#messageInfo.messageId"/>)"> <s:property value="#messageInfo.messageTitle" /> </a></h2> --%>
<!-- 									<p> -->
<%-- 										<s:if test="#messageInfo.messageContent.length() > 80"> --%>
<%-- 											<s:property value="#messageInfo.messageContent.substring(0, 80)" />...... --%>
<%-- 										</s:if> --%>
<%-- 										<s:else> --%>
<%-- 											<s:property value="#messageInfo.messageContent" /> --%>
<%-- 										</s:else> --%>
<!-- 									</p> -->
<!-- 								</div> -->
<%-- 							</s:if> --%>
<%-- 						</s:iterator> --%>
						
						<div class="news_list">
							<ul>
								<s:iterator value="#result.list" var="messageInfo" status="st">
									<li>
										<span><s:date name="#messageInfo.createDate" format="yyyy-M-dd" /></span>
										<s:if test="#messageInfo.messageTitle.length() > 20">
											<a href="javascript:void(0);" onclick="toDetail(<s:property value="#messageInfo.messageId"/>)"><s:property value="#messageInfo.messageTitle.substring(0, 18)" />...</a>
										</s:if>
										<s:else>
											<a href="javascript:void(0);" onclick="toDetail(<s:property value="#messageInfo.messageId"/>)"><s:property value="#messageInfo.messageTitle" /></a>
										</s:else>
									</li>
								</s:iterator>
							</ul>
						</div>
					</s:if>
				</s:iterator>
			</div><!-- course结束 -->

			<div class="video">
				<div class="title">
					<h2 class="title_left">最近赛事</h2>
					<span class="title_right"><a href="<%=path%>/system/messageInfoAction.do?method=list&messageType=4">More&gt;&gt;</a></span>
				</div>
	
				<s:iterator value="#resultList" id="result" status="stats">
					<s:if test="#stats.index == 3">
<%-- 						<s:iterator value="#result.list" var="messageInfo" status="st"> --%>
<%-- 							<s:if test="#st.index == 0"> --%>
<!-- 								<div class="news_detail"> -->
<!-- 									<img src="images/news.jpg" /> -->
<%-- 									<h2><a href="javascript:void(0);" onclick="toDetail(<s:property value="#messageInfo.messageId"/>)"> <s:property value="#messageInfo.messageTitle" /> </a></h2> --%>
<!-- 									<p> -->
<%-- 										<s:if test="#messageInfo.messageContent.length() > 80"> --%>
<%-- 											<s:property value="#messageInfo.messageContent.substring(0, 80)" />...... --%>
<%-- 										</s:if> --%>
<%-- 										<s:else> --%>
<%-- 											<s:property value="#messageInfo.messageContent" /> --%>
<%-- 										</s:else> --%>
<!-- 									</p> -->
<!-- 								</div> -->
<%-- 							</s:if> --%>
<%-- 						</s:iterator> --%>
						
						<div class="news_list">
							<ul>
								<s:iterator value="#result.list" var="messageInfo" status="st">
									<li>
										<span><s:date name="#messageInfo.createDate" format="yyyy-M-dd" /></span>
										<s:if test="#messageInfo.messageTitle.length() > 20">
											<a href="javascript:void(0);" onclick="toDetail(<s:property value="#messageInfo.messageId"/>)"><s:property value="#messageInfo.messageTitle.substring(0, 18)" />...</a>
										</s:if>
										<s:else>
											<a href="javascript:void(0);" onclick="toDetail(<s:property value="#messageInfo.messageId"/>)"><s:property value="#messageInfo.messageTitle" /></a>
										</s:else>
									</li>
								</s:iterator>
							</ul>
						</div>
					</s:if>
				</s:iterator>
			</div><!-- video结束 -->
		</div>
	</div><!-- content结束 -->

	<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>