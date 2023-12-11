<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>修改货物</title>

	</head>
	<body background="${pageContext.request.contextPath}/medium/3.jpg">
	<h3>欢迎${username}，在线人数${onlinecount}人</h3>
		<h1>修改货物</h1>
		<hr >
		<form action="${pageContext.request.contextPath}/good/modify" method="post">

			货品编号<input type="text" name="good_id" value="${good.good_id}" readonly/><br>
			货品名称<input type="text" name="good_name" value="${good.good_name}" /><br>
			货品来源<input type="text" name="source" value="${good.source}" /><br>
			货品目的地<input type="text" name="dest" value="${good.dest}" /><br>
			入库时间<input type="text" name="warehousing_date" value="${good.warehousing_date} "  placeholder="格式：yyyy-mm-dd"/><br>

			<input type="submit" value="修改"/><br>
		</form>
	</body>
</html>
