<%
	String path = request.getContextPath();
	String themePath = path + "/theme/default";
	String jsPath = path + "/theme/javascript";
%>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet" type="text/css" href="<%=themePath %>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/theme/poshytip/tip-yellowsimple/tip-yellowsimple.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/theme/alert/jquery.alerts.css" />
<script type="text/javascript" src="<%=jsPath %>/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=jsPath %>/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsPath %>/widget/jquery.filevalidate.js"></script>
<script type="text/javascript" src="<%=jsPath %>/widget/jquery.select.js"></script>
<script type="text/javascript" src="<%=path %>/theme/poshytip/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/theme/alert/jquery.alerts.js"></script>
<script type="text/javascript" src="<%=path %>/theme/alert/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="<%=jsPath %>/common.js"></script>
<script type="text/javascript" src="<%=jsPath %>/util.js"></script>
<script type="text/javascript" src="<%=jsPath %>/myfocus-2.0.1.min.js"></script>
<script type="text/javascript">
	var path = '<%=path%>';
	var themePath = '<%=themePath%>';
	var jsPath = '<%=jsPath%>';
</script>