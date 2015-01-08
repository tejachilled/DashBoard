<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Peer Review Tool</title>
<style>
#error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
}

#msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}

body {
background-color: #d9edf7;
	background-size: cover;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	margin: 0;
	padding: 0;
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>
	<h1 align="center">${headermsg}</h1>
	<div id="error">
		<form:errors path="student.*"></form:errors>
	</div>
	<div id="login-box">

		<h3>Login with Username and Password</h3>

		<form name='loginForm' action="/PeerTool/WelcomePage" method="post">
			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='username'></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2' align="center"><input type="submit"
						value="submit" /></td>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>