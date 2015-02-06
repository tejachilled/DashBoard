<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Students</title>
</head>
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
	text-align: center;
	position: absolute;
	bottom: 0;
	width: 100%;
}

#navleft {
	line-height: 30px;
	background-color: #eeeeee;
	height: 500px;
	width: 100px;
	float: left;
	padding: 5px;
}

#navright {
	line-height: 30px;
	background-color: #eeeeee;
	height: 500px;
	width: 100px;
	float: right;
	padding: 5px;
}
</style>
<body bgcolor="#d9edf7">
	<form action="/PeerTool/reviewWork" method="get">
		<div id="header">
			<h1>Peer Review Tool</h1>
		</div>
		
		<div id="navleft">
			
		</div>
		<div id="navright">
			user: ${teacher.username}
		</div><br/><br/>
	<h3 align="center">Please select a student to evaluate:</h3>
		<div id="section">
			<table align="center">
				<c:set var="counter" value="1"/>
				<c:forEach var="student" items="${teacher.studentsList}" varStatus="i" >
				<tr>				
					<td>${counter})</td>
					<td><a href="/PeerTool/reviewWork/${student.username}" name="stud" id="vassignment" >${student.username}</a></td>
				</tr>
				<c:set var="counter" value="${counter + 1}"/>
				</c:forEach>
				
				<tr>
					<td></td>
					<td><input type="submit" value="submit"
						onclick=" return validate();" /></td>
				</tr>
			</table>

		</div>

		<div id="footer">Copyright © Arizona State University</div>

	</form>
</body>
</html>