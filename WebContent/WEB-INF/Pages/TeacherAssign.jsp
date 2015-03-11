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
		var scountElement = document.getElementById('submissionCount');
		var scount = scountElement.value;
		var snt = document.getElementById('submissionCountText').value;

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
			alert('Please enter any numeric value for assignment id');
			return false;
		}
		if (scountElement.checked) {
			document.getElementById('submissionCountText').value = '';
			if (isNaN(scount) || scount.length == 0) {
				alert('Please enter correct submission count number');
				return false;
			}
		} else {
			if (isNaN(snt) || snt.length == 0) {
				alert('Please enter correct submission count number');
				return false;
			}
		} /*
		alert('coming');
		alert(document.getElementById('one'));
		var theTbl = document.getElementById('dataTable');
		for(var i=0;i<theTbl.rows.length;i++){
		        alert(theTbl.rows[i].cells[2].innerHTML) ; 
		        alert(theTbl.rows[i].cells[2].innerText) ;		    
		}
		
		return false; */
	}

	function addRow(tableID) {
		var table = document.getElementById(tableID);
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var cell1 = row.insertCell(0);
		var element1 = document.createElement("input");
		element1.type = "checkbox";
		element1.name = "chkbox[]";
		cell1.appendChild(element1);
		var cell2 = row.insertCell(1);
		cell2.innerHTML = rowCount + 1;
		var cell3 = row.insertCell(2);
		var element2 = document.createElement("input");
		element2.type = "text";
		var length = (table.rows.length) - 1; 
		element2.id = "operationParameterses[" + length + "].criteria";
		element2.name = "operationParameterses[" + length + "].criteria";
		cell3.appendChild(element2);
		var cell4 = row.insertCell(3);
		var element3 = document.createElement("input");
		element3.type = "text";
		element3.id = "operationParameterses[" + length + "].weight";
		element3.name = "operationParameterses[" + length + "].weight";
		cell4.appendChild(element3);

	}

	function deleteRow(tableID) {
		try {
			var table = document.getElementById(tableID);
			var rowCount = table.rows.length;

			for (var i = 0; i < rowCount; i++) {
				var row = table.rows[i];
				var chkbox = row.cells[0].childNodes[0];
				if (null != chkbox && true == chkbox.checked) {
					table.deleteRow(i);
					rowCount--;
					i--;
				}
			}
		} catch (e) {
			alert(e);
		}
	}
</script>
<body>

	<form action="/PeerTool/submitReview" method="post">
		<div id="header">
			<h1>Peer Review Tool</h1>
		</div>
		<br />
		<h4 align="right">user:
			${teacher.username}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</h4>
		<input type="hidden" name="username" value="${teacher.username}">
		<div id="section" align="center">
			<h4 style="color: red" align="center">${customMsg}</h4>
			<table align="center">
				<tr>
					<td>Please select group from dropdown:</td>
					<td><select name="groupid" id="groupid">
							<c:forEach items="${teacher.groups}" var="group">
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
					<td><input type="text" name="assignment_id" id="assignment_id"
						style="width: 100px;" /></td>
				</tr>
				<tr>
					<td>Number of submissions allowed:</td>
					<td><input type="radio" name="submissionCount"
						id="submissionCount" value="2" checked="checked">2&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" name="submissionCount" id="submissionCountText"
						onchange="return random2(); " style="width: 50px;" /></td>
				</tr>
			</table>
			<div>
				<h3 align="center">Review Panel</h3>
				<table width="350px" align="center">
					<tr>
						<td></td>
						<td><INPUT type="button" value="Add Row"
							onclick="addRow('dataTable')" /></td>
						<td><INPUT type="button" value="Delete Row"
							onclick="deleteRow('dataTable')" /></td>
					</tr>
					<tr>
						<td></td>
						<td>Evaluation Criteria</td>
						<td>Weight</td>
					</tr>
				</table>

				<TABLE id="dataTable" width="350px" border="1">
					<TR>
						<TD><INPUT type="checkbox" name="chk" /></TD>
						<TD>1</TD>
						<TD id="one"><INPUT type='text'
							name="operationParameterses[0].criteria" /></TD>
						<TD><INPUT type='text' name="operationParameterses[0].weight" />
						</TD>
					</TR>
				</TABLE>
			</div>
			<br /> <input align="middle" type="submit" value="Submit"
				onclick=" return validate();" />
		</div>

		<div id="footer">Copyright © Arizona State University</div>

	</form>
</body>
</html>