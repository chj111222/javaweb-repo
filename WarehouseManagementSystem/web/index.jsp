<%@page contentType="text/html;charset=UTF-8"%>
<%--访问jsp的时候不生成session对象。--%>
<%@page session="false" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>欢迎使用仓储管理系统（Warehouse management system）</title>
	</head>
	<body background="${pageContext.request.contextPath}/medium/2.jpg">

        <h1>LOGIN PAGE</h1>
        <hr>
        <%--前端页面发送请求的时候，请求路径以“/”开始，带项目名。--%>
        <form action="${pageContext.request.contextPath}/admin/login" method="post">
            username: <input type="text" name="username" ><br>
            password: <input type="password" name="password"><br>
            <input type="checkbox" name="f" value="1">十天内免登录<br>
            <input type="submit" value="login">
        </form>
	</body>
</html>
