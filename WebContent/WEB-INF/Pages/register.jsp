<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Page</title>
</head>

<body>
	<form action="/PeerTool/saveUserInfo" method="post" >
		<h1 align="center">${headermsg}</h1><br/>
		<div id="login-box">
		<strong style="color: #FF0000">${customMsg}</strong>		
		<table>
			<tr><td>User Id* </td><td> :<input type="text" name="user_id"> </td></tr>
			<tr><td>Password* </td><td> :<input type="text" name="password"> </td></tr>
			<tr><td>Name* </td><td> :<input type="text" name="uname"> </td></tr>
			<tr><td>ASU Email ID* </td><td> :<input type="text" name="emailId"> </td></tr>
			<tr><td>Group*</td><td> :
			<select name="group">
				<option value="spring15">Spring'15</option>
				<option value="summer15">Summer'15</option>
				<option value="fall15">Fall'15</option>
				</select>
			</td></tr>
			<tr><td/> <td><input type="submit" value="Submit"> </td></tr>
			
		</table>
		</div>
	</form>
</body>
</html>