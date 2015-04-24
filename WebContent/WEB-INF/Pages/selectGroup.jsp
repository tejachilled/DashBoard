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
function Validate(s) {
	document.getElementById('group_id').value = s;
	document.forms["myform"].submit();
	return true;
}
</script>
<body bgcolor="#d9edf7">
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
	<form action="/PeerTool/evaluateStudents2" method="POST" id="myform">
<br/><br/><br/>
		<h3 align="center">Please select a group from below :</h3>
		<div id="section">
			<table align="center">
				<c:forEach items="${teacher.groups}" var="group">
					<tr>
						<td><a href="#" onclick="return Validate('${group}');">
								${group} </a></td>
					</tr>
				</c:forEach>
				<tr>
					<td><input type="hidden" name="group_id" id="group_id"></td>
				</tr>
			</table>
		</div>
		<div id="footer">Copyright © Arizona State University</div>

	</form>
</body>
</html>