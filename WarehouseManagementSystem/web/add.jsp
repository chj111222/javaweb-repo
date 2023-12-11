<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>新增货物</title>

	</head>
	<body background="medium/4.jpg">
	<h3>欢迎${username}，在线人数${onlinecount}人</h3>
		<h1>新增货物</h1>

		<form action="${pageContext.request.contextPath}/good/save" method="post">
			货品编号<input type="text" name="good_id"/><br>
			货品名称<input type="text" name="good_name"/><br>
			货品来源<input type="text" name="source"/><br>
			货品目的地<input type="text" name="dest"/><br>
			入库时间<input type="text" name="warehousing_date" placeholder="格式：yyyy-mm-dd"/><br>
			<input type="submit" value="保存"/><br>
		</form>
	</body>
</html>
