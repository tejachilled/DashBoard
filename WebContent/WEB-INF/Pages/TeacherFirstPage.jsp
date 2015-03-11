<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Teacher's View</title>
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
	clear: both;
	position: absolute;
	text-align: center;
	bottom: 0;
	width: 100%;
}
</style>
</head>
<body>
	<div id="header">
		<h1>Peer Review Tool</h1>
	</div>
	<br />
	<h4 align="right">user:
		${teacher.username}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</h4>
	<input type="hidden" name ="username" value = "${teacher.username}">
<h4 style="color: green" align="center">${customMsg}</h4>
	<div id="section">	
		<table align="center">
			<tr>
				<td><h2> Please select from below : </h2></td>
			</tr>
			<tr>
				<td>
					<form action="/PeerTool/assign" method="post">
						1)&nbsp;&nbsp;<input type="submit" value="Create Evaluation Criteria">
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<form action="/PeerTool/groups" method="post">
						2)&nbsp;&nbsp;<input type="submit" value="Create/Modify Groups">
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<form action="/PeerTool/evaluateStudents" method="post">
						3)&nbsp;&nbsp;<input type="submit" value="Evaluate Students">
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<form action="/PeerTool/register" method="post">
						4)&nbsp;&nbsp;<input type="submit" value="Add Students">
					</form>
				</td>
			</tr>
		</table>
	</div>
	<div id="footer">Copyright © Arizona State University</div>

</body>
</html>