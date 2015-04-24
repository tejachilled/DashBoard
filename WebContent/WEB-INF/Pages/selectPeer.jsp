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
	position: fixed;
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
		<div id="navleft"></div>
		<div id="navright">
			user: ${student.username} <a href="/PeerTool/Welcome"> <img
				src="${pageContext.request.contextPath}/resources/home.png"
				width="30">
			</a> <a href="/PeerTool/logout"> <img
				src="${pageContext.request.contextPath}/resources/logout.png"
				width="30">
			</a>
		</div>
		<br /> <br />
		<c:set var="size" value="${student.count }" />
		<c:if test="${student.count eq 0}">
			<h3 align="center">No peer submitted this assignment</h3>
		</c:if>
		<c:if test="${student.count gt 0}">
			<h3 align="center">Please select a peer to evaluate:</h3>
			<div id="section">
				<table align="center" border="1">
					<tr>
						<td></td>
						<td>Students</td>
						<td>Your evaluation</td>
						<c:forEach begin="2" end="${student.randomNumber}" varStatus="k">
							<td>Peer evaluation</td>
						</c:forEach>
						<td>Teacher evaluation</td>
					</tr>
					<c:set var="counter" value="1" />
					<c:forEach var="stu" items="${student.peerList}" varStatus="i">
						<tr>
							<td>${counter})</td>
							<td>${stu.key}</td>
							<c:if test="${ stu.value.teacherTotMarks gt 0}">
								<c:if test="${not empty stu.value.reviewCriteria}">
									<td><a href="/PeerTool/peerWork/${stu.key}" name="stud"
										id="vassignment">${stu.value.studentTotMarks[0]}</a></td>
								</c:if>
								<c:if test="${ empty stu.value.reviewCriteria}">
									<td><a href="/PeerTool/peerWork/${stu.key}" name="stud"
										id="vassignment">N/A</a></td>
								</c:if>
								<c:forEach begin="1" end="${student.randomNumber - 1}"
									varStatus="j">
									<c:if test="${not empty stu.value.studentTotMarks[j.index]}">
										<td>${stu.value.studentTotMarks[j.index]}</td>
									</c:if>
									<c:if test="${ empty stu.value.studentTotMarks[j.index]}">
										<td>N/A</td>
									</c:if>

								</c:forEach>
								<td>${ stu.value.teacherTotMarks}</td>
							</c:if>
							<c:if test="${ stu.value.teacherTotMarks eq 0}">
								<c:if test="${not empty stu.value.reviewCriteria}">
									<td><a href="/PeerTool/peerWork/${stu.key}" name="stud"
										id="vassignment">${stu.value.studentTotMarks[0]}</a></td>
								</c:if>
								<c:if test="${ empty stu.value.reviewCriteria}">
									<td><a href="/PeerTool/peerWork/${stu.key}" name="stud"
										id="vassignment">N/A</a></td>
								</c:if>
								<c:forEach begin="1" end="${student.randomNumber - 1}"
									varStatus="j">
									<td>N/A</td>
								</c:forEach>
								<td>N/A</td>
							</c:if>

						</tr>
						<c:set var="counter" value="${counter + 1}" />
					</c:forEach>

				</table>

			</div>
		</c:if>
		<div id="footer">Copyright © Arizona State University</div>
		<input type="hidden" name="peerObj" value="${peerObj}" />
	</form>
</body>
</html>