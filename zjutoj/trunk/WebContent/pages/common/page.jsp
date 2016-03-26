<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript">
	function doPage(num) {
		var currentPageNo = <s:property value="#pageQueryResult.pageNo" />,
		totalPage = <s:property value="#pageQueryResult.totalPage" />;
		var pageNo = 1;
		//首页
		if(num == 0) {
			pageNo = 1;
		}
		//上一页
		else if(num == -1) {
			if(currentPageNo - 1 > 0) {
				pageNo = currentPageNo - 1;
			}else {
				pageNo = currentPageNo;
			}
		}
		//下一页
		else if(num == -2) {
			if(currentPageNo + 1 < totalPage) {
				pageNo = currentPageNo + 1;
			}else {
				pageNo = totalPage;
			}
		}
		//末页
		else if(num == -3) {
			pageNo = totalPage;
		}
		else {
			pageNo = num;
		}
		getPageData(pageNo);
	}
</script>
</head>
<body>
	<div class="page">
		<ul>
			<li><a href="javascript:void(0);" onclick="doPage(0)">首页</a></li>
			<li><a href="javascript:void(0);" onclick="doPage(-1)">上一页</a></li>
			
			<s:bean name="org.apache.struts2.util.Counter" id="counter">
				<s:param name="first" value="%{(#pageNo - 2) > 0 ? (#pageNo - 2) : 1}" />
				<s:param name="last" value="%{(#pageNo + 2) lt #totalPage ? (#pageNo + 2) : #totalPage}" />
				<s:iterator>
					<s:if test="%{(current-1) == #pageNo}">
						<li class="selected"><a href="javascript:void(0);"><s:property value="current-1"/></a></li>
					</s:if>
					<s:else>
						<li><a href="javascript:void(0);" onclick="doPage(<s:property value="current-1"/>)"><s:property value="current-1"/></a></li>
					</s:else>
				</s:iterator>
			</s:bean>
			
			<li><a href="javascript:void(0);" onclick="doPage(-2)">下一页</a></li>
			<li><a href="javascript:void(0);" onclick="doPage(-3)">尾页</a></li>
		</ul>
	</div>
</body>
</html>