<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>货品详情</title>

	</head>
	<body background="${pageContext.request.contextPath}/medium/4.jpg">
	<h3>欢迎${username}，在线人数${onlinecount}人</h3>
		<h1>货品详情</h1>
		<hr >
		货品编号：${good.good_id} <br>
		货品名称：${good.good_name}<br>
		货品来源：${good.source}<br>
		货品目的地：${good.dest}<br>
		入库时间：${good.warehousing_date}<br>
		<input type="button" value="后退" onclick="window.history.back()"/>
	</body>
</html>
