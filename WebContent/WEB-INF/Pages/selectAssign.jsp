<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Assignment to Evaluate</title>
<style type="text/css">
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
	position: fixed;
	text-align: center;
	bottom: 0;
	width: 100%;
}
</style>
<script>
	function validate() {

		var select = document.getElementById("assignment_id").value;
		//alert(select);
		document.getElementById("assid").value = select;
		var size = '${listSize}';
		if (size == 0) {
			alert('Please contact admin!');
			return false;
		} else if (select.length == 0) {
			alert('Please select an assignment');
			return false;
		}

	}
</script>
</head>
<body>
	<div id="header">
		<h1>Peer Review Tool</h1>
	</div>
	<h4 align="right">
		user: ${student.username} <a href="/PeerTool/Welcome"> <img
			src="${pageContext.request.contextPath}/resources/home.png"
			width="30">
		</a> <a href="/PeerTool/logout"> <img
			src="${pageContext.request.contextPath}/resources/logout.png"
			width="30">
		</a>
	</h4>
	<h3 align="center">${headermsg}</h3>
	<div id="section">
	<form action="/PeerTool/evalassignment" method="post">
		<table align="center">
			<tr>
				<td>
					<h4>Select an assignment to evaluate:</h4>
				</td>
				<td>
					
						<select name="assignment_id" id="assignment_id">
							<c:forEach items="${list}" var="aid">
								<option value="${aid}">${aid}</option>
							</c:forEach>
						</select>
					<input type="hidden" name = "assid" id = "assid">
				</td>
			</tr>
		</table>
		<table align="center">
			<tr>
				<td><input type="submit" value="submit"
					onclick="return validate();"></td>
			</tr>
		</table>
		</form>
	</div>

	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>