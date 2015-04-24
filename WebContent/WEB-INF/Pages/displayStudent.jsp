<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload/View Assignment</title>
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
</head>
<script>
	function validate() {

		var select = document.getElementById("assignment_id").value;
		if (select != null && select.length > 0) {

			document.getElementById('aid1').value = select;
			document.getElementById('aid2').value = select;
			document.getElementById('aid1').name = 'aid';
			document.getElementById('aid2').name = 'aid';

		} else {
			var size = '${listSize}';
			if (size == 0) {
				alert('Please contact admin!');
				return false;
			}else {
				var list = '${list}';
				alert('Please select an assignment');
			}
			return false;

		}

	}
</script>
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
		<table align="center">
			<tr>
				<td>Please select an assignment :</td>
				<td><select name="assignment_id" id="assignment_id">
						<c:forEach items="${list}" var="aid">
							<option value="${aid}">${aid}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>
					<form action="/PeerTool/submitwork" method="post">
						 <strong>1)</strong>&nbsp;&nbsp;<input type="submit" value="Submit Assignment"
							onclick="return validate();"> <input type="hidden"
							id="aid1" />
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<form action="/PeerTool/vassignment" method="post">
						 <strong>2)</strong>&nbsp;&nbsp;<input type="submit" value="View Assignment"
							onclick="return validate();"> <input type="hidden"
							id="aid2" />
					</form>
				</td>
			</tr>
		</table>
	</div>

	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>