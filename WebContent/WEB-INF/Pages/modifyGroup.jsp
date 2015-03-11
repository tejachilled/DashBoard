<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<script type="text/javascript">
 function Validate(){
	 
	 var group = document.getElementById('groupid').value;
	 alert(group); 
	 if(group.length == 0) {
		 alert('Please select any group from dropdown');
		 return false;
	 }
	 
	 return true;
 }

</script>
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
	position: absolute;
	bottom: 0;
	width: 100%;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modify Group</title>
</head>
<body>
	<div id="header">
		<h1>Peer Review Tool</h1>
	</div>
	<form action="/PeerTool/changeGroup" method="post">
		<br />
		<div id="login-box">
			<h4 style="color: red" align="center">${customMsg}</h4>
			<table align="center">
				<tr>
					<td>Select any group from :</td>
					<td><select name="groupid" id="groupid">
							<c:forEach items="${teacher.groups}" var="group">
								<option value="${group}">${group}</option>
							</c:forEach>
					</select></td>
					<td><input type="submit" value="Go"
						onclick="return Validate();"></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<c:forEach items="${teacher.allStudents}" var="student">
					<tr>
						<td><c:choose>
								<c:when test="${param.enter=='1'}">
									<input type="checkbox" value="${student.username}"
									name="checkboxValues" id="checkboxValues">
									${student.fullName}
								</c:when>
								<c:otherwise>
									<input type="checkbox" value="${student.username}"
									name="checkboxValues" id="checkboxValues">
									${student.fullName}
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
				<tr>
					<td />
					<td><input type="submit" value="Submit"
						onclick="return Validate();"></td>
				</tr>

			</table>
		</div>
	</form>
	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>