<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Peers</title>
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
			user: ${student.username}
		</div><br/><br/>
	<h3 align="center">Please select a peer to evaluate:</h3>
		<div id="section">
			<table align="center" border="1">
				<tr>
					<td></td>
					<td>Students</td>
					<td>Peer1</td>
					<td>Peer2</td>
					<td>Teacher</td>
				</tr>
				<c:set var="counter" value="1" />
				<fmt:parseNumber var="counter2" value="0" />
				<c:set var="bool" value="0"></c:set>
				<c:forEach var="student" items="${student.peerList}"
					varStatus="i">
					<tr>
						<td>${counter})</td>
						<td><a href="/PeerTool/peerWork/${student.key}" name="stud"
							id="vassignment">${student.key}</a></td>
						<c:if test="${not empty student.value.marks}">
							<c:choose>
								<c:when test="${fn:length(student.value.marks) eq 3}">
									<c:forEach var="list" items="${student.value.marks }">
										<td><input type="radio" checked="checked"></td>
									</c:forEach>
								</c:when>
								<c:when test="${fn:length(student.value.marks) eq 1}">
									<c:forEach var="list" items="${student.value.marks }">
										<c:if test="${list.teacher_evaluation == true }">
											<td><input type="radio" readonly="readonly" disabled></td>
											<td><input type="radio" readonly="readonly" disabled></td>
											<td><input type="radio" checked="checked"></td>
										</c:if>
										<c:if test="${list.teacher_evaluation != true }">
											<td><input type="radio" checked="checked"></td>
											<td><input type="radio" readonly="readonly" disabled></td>
											<td><input type="radio" readonly="readonly" disabled></td>
										</c:if>
									</c:forEach>
								</c:when>
								<c:when test="${fn:length(student.value.marks) eq 2}">
									<c:forEach var="list" items="${student.value.marks }">
										<c:if test="${list.teacher_evaluation == false }">
											<td><input type="radio" checked="checked"></td>
										</c:if>
										<c:if test="${list.teacher_evaluation == true }">
											<c:set var="bool" value="1"></c:set>
										</c:if>
									</c:forEach>
									<c:if test="${bool == 1 }">
										<td><input type="radio" readonly="readonly" disabled></td>
										<td><input type="radio" checked="checked"></td>
									</c:if>
								</c:when>
							</c:choose>


						</c:if>
						<c:if test="${ empty student.value.marks}">
							<td><input type="radio" readonly="readonly" disabled>
							</td>
							<td><input type="radio" readonly="readonly" disabled></td>
							<td><input type="radio" readonly="readonly" disabled>
							</td>
						</c:if>
					</tr>
					<c:set var="counter" value="${counter + 1}" />
				</c:forEach>

			</table>

		</div>

		<div id="footer">Copyright © Arizona State University</div>
<input type="hidden" name = "peerObj" value="${peerObj}" />
	</form>
</body>
</html>