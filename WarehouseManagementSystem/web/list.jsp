<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>部门列表页面</title>
		<%--设置整个网页的基础路径是：http://localhost:8080/oa/ --%>
		<%--<base href="http://localhost:8080/oa/">--%>
		<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
	</head>
	<body background="medium/2.jpg">

	<h3>欢迎${username}，在线人数${onlinecount}人</h3>
	<a href="admin/exit">[退出系统]</a>


<script type="text/javascript">
	function del(goodId){
		var ok = window.confirm("亲，删了不可恢复哦！");
		if(ok){
			/*注意html的base标签可能对JS代码不起作用。所以JS代码最好前面写上"/oa" */
			document.location.href = "${pageContext.request.contextPath}/good/delete?goodId=" + goodId;
		}
	}
</script>

		<h1 align="center">货品列表</h1>

		<table border="1px" align="center" width="50%">
			<tr>
				<th>序号</th>
				<th>货品编号</th>
				<th>货品名称</th>
				<th>入库时间</th>
				<th>操作</th>
			</tr>

			<c:forEach items="${goodList}" varStatus="goodStatus" var="good">
				<tr>
					<td>${goodStatus.count}</td>
					<td>${good.good_id}</td>
					<td>${good.good_name}</td>
					<td>${good.warehousing_date}</td>
					<td>
						<a href="javascript:void(0)" onclick="del(${good.good_id})">删除</a>
						<a href="good/detail?f=edit&gid=${good.good_id}">修改</a>
						<a href="good/detail?f=detail&gid=${good.good_id}">详情</a>
					</td>
				</tr>
			</c:forEach>

		</table>
		

		<a href="add.jsp">新增货物</a>
		
	</body>
</html>
