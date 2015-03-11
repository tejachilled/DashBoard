<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<style type="text/css">
#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
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
	clear: both;
	position: absolute;
	text-align: center;
	bottom: 0;
	width: 100%;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Page</title>
</head>

<body>
<div id="header">
		<h1>${headermsg}</h1>
	</div>
	<form action="/PeerTool/saveUserInfo" method="post">
		<br />
		<div id="login-box">
			<strong style="color: #FF0000">${customMsg}</strong>
			<table>
				<tr>
					<td>User Id*</td>
					<td>:<input type="text" name="user_id">
					</td>
				</tr>
				<tr>
					<td>Password*</td>
					<td>:<input type="text" name="password">
					</td>
				</tr>
				<tr>
					<td>Name*</td>
					<td>:<input type="text" name="uname">
					</td>
				</tr>
				<tr>
					<td>ASU Email ID*</td>
					<td>:<input type="text" name="emailId">
					</td>
				</tr>
				<tr>
					<td>Group*</td>
					<td>:<select name="groupid" id="groupid">
							<c:forEach items="${teacher.groups}" var="group">
								<option value="${group}">${group}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td />
					<td><input type="submit" value="Submit"></td>
				</tr>

			</table>
		</div>
	</form>
	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>