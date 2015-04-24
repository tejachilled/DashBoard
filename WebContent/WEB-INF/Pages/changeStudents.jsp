<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modify Group</title>
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
	bottom: 0;
	width: 100%;
	position: fixed;
}
#navleft {
	line-height: 30px;
	width: 100px;
	float: left;
	padding: 5px;
}

#navright {
	line-height: 30px;
	width: 100px;
	float: right;
	padding: 5px;
}
</style>
</head>
<body>
	<div id="header">
		<h1>Peer Review Tool</h1>
	</div>
	<div id="navleft"></div>
	<div id="navright">
		<a href="/PeerTool/teacherfirst"> <img
			src="${pageContext.request.contextPath}/resources/home.png"
			width="30">
		</a> <a href="/PeerTool/logout"> <img
			src="${pageContext.request.contextPath}/resources/logout.png"
			width="30">
		</a>
	</div>
	<form action="/PeerTool/modifyGroup" method="post" name="myform">
		<br />
		<div id="login-box">
			<h4 style="color: red" align="center">${customMsg}</h4>
			<div align="center"><strong>Add/modify students for group ${groupid}: </strong> </div>	
			<table align="center">
				<c:set var="group_id" value="${groupid}"></c:set>
				<c:forEach items="${teacher.allStudents}" var="student">

					<c:choose>
						<c:when test="${student.group_id eq groupid }"> 
						<tr>
							<td><input type="checkbox" value="${student.username }"
								checked="checked" name = "checkedValues"> ${student.fullName }</td>
						</tr>
						</c:when>
						<c:otherwise>   
						<tr>
							<td><input type="checkbox" value="${student.username }" name = "checkedValues">${student.fullName } </td>
						</tr>
 						</c:otherwise>
					</c:choose>

				</c:forEach>
				<tr>
					<td><input type="submit" value="Update" ></td>
				</tr>
			</table>
		</div>
		<input  type="hidden" value ="${groupid}" name="group_id">
		<input type = "hidden" value = "update" name = "update">
	</form>
	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>