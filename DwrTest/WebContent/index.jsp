<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	// 用于获取项目根目录
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 需要引入的dwr的三个重要js 路径要和web.xml server-mapping内配置的路径一致-->
<script type="text/javascript" src="<%=path%>/js/dwr/util.js"></script>
<script type="text/javascript" src="<%=path%>/js/dwr/engine.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/dwr/interface/DwrPush.js"></script>

<script type="text/javascript" src="<%=path%>/js/jquery-3.2.1.min.js"></script>

<title>Clannad聊天室</title>
</head>
<body>
	<ul id="ul" style="color: red; font-size: 15px;"></ul>
	<input type="text" name="msg" id="msg" size="30">
	<input type="button" name="button" id="send" value="发送">
</body>
<script type="text/javascript">
	/* 开启ajax反推技术 并给按钮绑定推送方法 */
	$(document).ready(function() {
		dwr.engine.setActiveReverseAjax(true);
		$('#send').click(function() {
			DwrPush.push($('#msg').val());
		});
	});
	// 回调方法
	function callback(msg) {
		$('#ul').html($('#ul').html() + "</br>" + msg);
	}
</script>
</html>