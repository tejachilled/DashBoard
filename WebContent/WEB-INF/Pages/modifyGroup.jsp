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
#login-box {
	width: 300px;
	padding: 20px;
	background: #fff;
	border: 1px solid #000;
}
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
<title>Modify Group</title>
</head>
<body>
	<div id="header">
		<h1>Peer Review Tool</h1>
	</div>
	<h4 align="right">
		user: ${teacher.username} <a href="/PeerTool/teacherfirst"> <img
			src="${pageContext.request.contextPath}/resources/home.png"
			width="30">
		</a><a href="/PeerTool/logout"> <img
			src="${pageContext.request.contextPath}/resources/logout.png"
			width="30">
		</a>
	</h4>
	<center>
		<h4 align="center">Modify multiple students:</h4>
		<form action="/PeerTool/createmulgrpstuds" method="post"
			enctype="multipart/form-data">
			<h4 style="color: green;" align="center">${customMsgGrp}</h4>
			<div id="login-box" align="left">
				<span style="color: graytext;">Fields: UserId,Existing group name</span>
				<table>
					<tr>
						<td>Select File:</td>
						<td><input type="file" name="filetoupload" align="middle">
						</td>
					</tr>
					<tr align="center"> 
						<td><input align="middle" type="submit" value="Upload File">
						</td>
					</tr>
				</table>
			</div>
		</form>
		<h4 align="center">Select any one group from below to modify:</h4>
	<form action="/PeerTool/changeGroup" method="post" name="myform">
		<div id="login-box">
			<h4 style="color: green" align="center">${customMsg}</h4>
			<table align="center">
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
	<form action="/PeerTool/groups" method="post">
		<table align="center">
			<tr>
				<td><input type="submit" name="submit" id="back"
					value="Back"></td>
			</tr>
		</table>
	</form>
	</center>
	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>