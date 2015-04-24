<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<script type="text/javascript">
	function Validate(s) {
		document.getElementById('group_id').value = s;
		document.forms["myform"].submit();
		return true;
	}
</script>
<style>
#header {
	background-color: black;
	color: white;
	text-align: center;
	width: 100%;
	padding: 5px;
}

#section {
	padding: 10px;
	background-color: #d9edf7;
}

#footer {
	background-color: black;
	color: white;
	text-align: center;
	position: fixed; bottom : 0;
	width: 100%;
	bottom: 0;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modify Criteria</title>
</head>
<body>
	<div id="header">
		<h1>Peer Review Tool</h1>
	</div>
	<h4 align="right">
		user: ${teacher.username} <a href="/PeerTool/teacherfirst"> <img
			src="${pageContext.request.contextPath}/resources/home.png"
			width="30">
		</a> <a href="/PeerTool/logout"> <img
			src="${pageContext.request.contextPath}/resources/logout.png"
			width="30">
		</a>
	</h4>
	<form action="/PeerTool/changeCriteria" method="post" name="myform">
		<div id="login-box">
			<h4 style="color: green" align="center">${customMsg}</h4>
			<table align="center">
				<tr>
					<td><h3>Select any group from below to modify:</h3></td>
				</tr>
			</table>
			<table align="center">
				<c:forEach items="${teacher.groups}" var="group">
					<tr>
						<td><a href="#" onclick="return Validate('${group}');">
								${group} </a></td>
					</tr>
				</c:forEach>
				<tr>
					<td><input type="hidden" name="group_id" id="group_id"></td>
				</tr>
			</table>
		</div>
	</form>
	<form action="/PeerTool/assign" method="post">
		<table align="center">
			<tr>
				<td><input type="submit" name="submit" id="back"
					value="Back"></td>
			</tr>
		</table>
	</form>
	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>