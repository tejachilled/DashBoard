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
	bottom: 0;
	width: 100%;
	position: fixed;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Page</title>
<script type="text/javascript">
function validate(){
	
	var uid = document.getElementById("user_id").value;
	var pwd = document.getElementById("password").value;
	var uname = document.getElementById("uname").value;
	var email = document.getElementById("emailId").value;
	if(uid.length==0){
		alert('Please enter user id');
		return false;
	}else if(pwd.length==0){
		alert('Please enter password');
		return false;
	}else if(uname.length==0){
		alert('Please enter user name');
		return false;
	}else if(email.length==0){
		alert('Please enter email address');
		return false;
	}
}
</script>
</head>

<body>
	<div id="header">
		<h1>${headermsg}</h1>
	</div>
	<h4 align="right">
		<a href="/PeerTool/teacherfirst"> <img
			src="${pageContext.request.contextPath}/resources/home.png"
			width="30">
		</a> <a href="/PeerTool/logout"> <img
			src="${pageContext.request.contextPath}/resources/logout.png"
			width="30">
		</a>
	</h4>
	<center>
		<h4 align="center">Create multiple students:</h4>
		<form action="/PeerTool/createStudents" method="post"
			enctype="multipart/form-data">
			<div id="login-box" align="left">
			<span style="color: graytext;">Fields: UserId,Password,Name,Asu Email id, Group name</span>
				<table>
					<tr>
						<td>Select File :</td>
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
		<h4 align="center">Create student:</h4>
		<form action="/PeerTool/saveUserInfo" method="post">
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
						<td>Group</td>
						<td>:<select name="group" id="group">
								<option value=""></option>
								<c:forEach items="${teacher.groups}" var="groupId">
									<option value="${groupId}">${groupId}</option>
								</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td />
						<td><input type="submit" value="Submit" onclick="return validate();"></td>
					</tr>

				</table>
			</div>
		</form>
	</center>
	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>