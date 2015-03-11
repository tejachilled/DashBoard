<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a group</title>
<script type="text/javascript">

function Validate(){
	var check  = document.getElementsByName('checkboxValues');
	var group = document.getElementById('groupid').value;
	if(group.length==0 || group == null){
		alert('Please enter a group name!');
		return false;
	}
	var count =0;
	for(var c = 0; c < check.length;c++){
	      if(check[c].checked){
	         count++;                                
	      }
	  }
	if(count==0){
		alert('Please select atleast one student');
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
	bottom: 0;
	width: 100%;
}
</style>
</head>
<body>
	<div id="header">
		<h1>Peer Review Tool</h1>
	</div>
	<form action="/PeerTool/saveNewGroup" method="post">
		<br />
		<div id="login-box">
			<h4 style="color: red" align="center">${customMsg}</h4>
			<table align="center">
				<tr>
					<td>New Group name: *</td>
					<td><input type="text" name="groupid" id ="groupid">
					</td>
				</tr>
				<tr>
					<td>Select students from below: </td>
				</tr>

				<c:forEach items="${teacher.allStudents}" var="student">
					<tr>
						<td><input type="checkbox" value="${student.username}"
							name="checkboxValues" id = "checkboxValues"> ${student.fullName}</td>
					</tr>
				</c:forEach>

				<tr>
					<td />
					<td><input type="submit" value="Submit" onclick="return Validate();"></td>
				</tr>

			</table>
		</div>
	</form>
	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>