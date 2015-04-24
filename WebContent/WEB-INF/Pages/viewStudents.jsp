<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

<script type="text/javascript">

function Validate(){
	var faid = '${fAid}';
	if(faid != null){
		document.getElementById('aid').value =faid;
		document.getElementById('table').style.display = '';		
	}
	

}
function Validate2(){
	var aid = document.getElementById('aid').value;
	document.getElementById('finalaid').value = aid;
	document.getElementById('table');
}
</script>
<body bgcolor="#d9edf7" onload="return Validate();">
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
	<form action="/PeerTool/evaluateStudents2" method="POST">
		<h2 align="center">Group : ${teacher.groupid }</h2>

		<table align="center">
			<tr>
				<td>Select any assignment :
				<td><select name="aid" id="aid">
						<c:forEach items="${teacher.assignids}" var="assid">
							<option value="${assid}">${assid}</option>
						</c:forEach>
				</select></td>
				<td><input type="hidden" id="finalaid" name="finalaid">
					<input type="submit" value="Go" name="submit2" id="submit2"
					onclick="return Validate2()"></td>
			</tr>
		</table>
		<div id="table" style="display: none;">
			<h3 align="center" id="select">Please select a student to
				evaluate:</h3>
			<table align="center" border="1">
				<tr>
					<td></td>
					<td>Students</td>
					<c:forEach begin="1" end="${teacher.randomNumber }" varStatus="k">
						<td>Peer ${k.index}</td>
					</c:forEach>
					<td>Teacher</td>
					<td>Average</td>
				</tr>
				<c:set var="counter" value="1" />

				<c:set var="bool" value="0"></c:set>
				<c:forEach var="student" items="${teacher.studentList}"
					varStatus="i">
					<fmt:parseNumber var="counter2" type="number" value="1" />
					<tr>
						<td>${counter})</td>
						<td>${student.key}</td> 
						<c:if test="${not empty student.value.studentTotMarks}">
							<c:forEach items="${student.value.studentTotMarks}" var="mark">
								<td>${mark}</td>
								<fmt:parseNumber var="counter2" type="number"
									value="${counter2 +1 }" />
							</c:forEach>
						</c:if>

						<c:if test="${ empty student.value.studentTotMarks}">
							<c:forEach begin="1" end="${teacher.randomNumber}">
								<td>N/A</td>
								<fmt:parseNumber var="counter2" type="number"
									value="${counter2 +1 }" />
							</c:forEach>
						</c:if>
						<c:if test="${student.value.teacherTotMarks eq 0}">
							<c:forEach begin="${counter2}" end="${teacher.randomNumber}">
								<td>N/A
								</td>
							</c:forEach>
							<td><a href="/PeerTool/reviewWork/${student.key}"
								name="stud" id="vassignment">N/A</a>
						</c:if>
						<c:if test="${student.value.teacherTotMarks gt 0}">
							<c:forEach begin="${counter2}" end="${teacher.randomNumber}">
								<td>N/A</td>  
							</c:forEach>
							<td><a href="/PeerTool/reviewWork/${student.key}"
								name="stud" id="vassignment">${student.value.teacherTotMarks}</a></td>
						</c:if>
						<td>${student.value.average }/ ${student.value.totMarks }</td>
					</tr>
					<c:set var="counter" value="${counter + 1}" />
				</c:forEach>

			</table>
		</div>

		<div id="footer">Copyright © Arizona State University</div>

	</form>
</body>
</html>