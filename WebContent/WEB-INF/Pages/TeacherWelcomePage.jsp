<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Peer Review Tool</title>
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
	position: absolute;
	text-align: center;
	bottom: 0;
	width: 100%;
}
</style>
<script type="text/javascript">
	function random1() {
		var radio = document.getElementById('randomNumber');
		radio.checked = false;
		return true;
	}
	function random2() {
		var radio = document.getElementById('submissionCount');
		radio.checked = false;
		return true;
	}
	function validate() {
		var radioRandom = document.getElementById('randomNumber');
		var number = radioRandom.value;
		var rnt = document.getElementById('randomNumberText').value;
		var id = document.getElementById('assignment_id').value;
		var aname = document.getElementById('assignment_name').value;
		var scountElement = document.getElementById('submissionCount');
		var scount = scountElement.value;
		var snt =document.getElementById('submissionCountText').value;
		
		if (radioRandom.checked) {
			rnt = '';
			if (isNaN(number) || number.length == 0) {
				alert('Please enter correct random number field');
				return false;
			}
		} else {
			if (isNaN(rnt) || rnt.length == 0) {
				alert('Please enter correct random number field');
				return false;
			}
		}
		if (isNaN(id) || id.length == 0) {
			alert('Please enter correct assignment id');
			return false;
		}
		if (scountElement.checked) {
			document.getElementById('submissionCountText').value='';
			if (isNaN(scount) || scount.length == 0) {
				alert('Please enter correct submission count number');
				return false;
			}
		} else {
			if (isNaN(snt) || snt.length == 0) {
				alert('Please enter correct submission count number');
				return false;
			}
		}
		if (aname.length == 0) {
			alert('Please enter assignment name');
			return false;
		}

	}
</script>
<body>

	<form action="/PeerTool/viewStudents" method="post">
		<div id="header">
			<h1>Peer Review Tool</h1>
		</div>
		<br />
		<h4 align="right">user:
			${student.username}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</h4>

		<div id="section">
			<table align="center">
				<tr>
					<td>Please select group from dropdown:</td>
					<td><select name="groupid" id="groupid">
							<c:forEach items="${student.groups}" var="group">
								<option value="${group}">${group}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>Please give a random number to evaluate:</td>
					<td><input type="radio" name="randomNumber" id="randomNumber"
						value="2" checked="checked">2&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="text" name="randomNumber" id="randomNumberText"
						style="width: 50px;" onchange="return random1();" /></td>
				</tr>
				<tr>
					<td>Please enter assignment id:</td>
					<td><input type="text" name="assignment_id" id="assignment_id" style="width: 100px;"/></td>
				</tr>
				<tr>
					<td>Please enter assignment name:</td>
					<td><input type="text" name="assignment_name"
						id="assignment_name" style="width: 100px;"/></td>
				</tr>
				<tr>
					<td>Number of submissions allowed:</td>
					<td><input type="radio" name="submissionCount"
						id="submissionCount" value="2" checked="checked">2&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" name="submissionCount" id="submissionCountText"
						onchange="return random2(); " style="width: 50px;"/></td>
				</tr>
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