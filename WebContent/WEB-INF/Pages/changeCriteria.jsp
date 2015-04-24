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
	position: fixed;
	text-align: center;
	bottom: 0;
	width: 100%;
}
</style>
<script type="text/javascript">
	function random1() {
		var radio = document.getElementById('randomNumber');
		radio.checked = false;
		alert(radio.checked);
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
		var scountElement = document.getElementById('submissionCount');
		var scount = scountElement.value;
		var snt = document.getElementById('submissionCountText').value;
		
		if (radioRandom.checked) {
			rnt = '';
			if (isNaN(number) || number.length == 0) {
				alert('Please enter correct random number ');
				return false;
			}
		} else {
			if (isNaN(rnt) || rnt.length == 0) {
				alert('Please enter correct random number ');
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
		} 
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
	function hidden(){
		var display ='${display}';
		if(display == 'display'){
			var div  = document.getElementById('display');
			div.style.display = 'block';
			var select = document.getElementById('assignid');
			select.value = '${teacher.assignment_id}';
			var aid = document.getElementById('assignment_id');
			aid.value = '${teacher.assignment_id}';
		}
		
		
	}
</script>
<body onload="return hidden();">
	<div id="header">
		<h1>Peer Review Tool</h1>
	</div>
	<h4 align="right">user:
			${teacher.username}
			
		<a href="/PeerTool/teacherfirst" > <img
			src="${pageContext.request.contextPath}/resources/home.png"
			width="30">
		</a> <a href="/PeerTool/logout"> <img
			src="${pageContext.request.contextPath}/resources/logout.png"
			width="30">
		</a>
	</h4>
	
	<form action="/PeerTool/submitaid" method="post">
	<h4 align="center">Group : ${groupid}</h4>
	<input type="hidden" name="group_id" value="${groupid}">
		<input type="hidden" name="username" value="${teacher.username}">
		<div id="section" align="center">
			<h4 style="color: red" align="center">${customMsg}</h4>
			<table align="center">
				<tr>
					<td>Please select an assignment id:</td>
					<td><select name="assignid" id="assignid" >
							<c:forEach items="${teacher.assignids}" var="aid">
								<option value="${aid}">${aid}</option>
							</c:forEach>
					</select></td>
					
					<td><input align="middle" type="submit" value="Go"/>
					<input type="hidden" name="assignment_id" />
					</td>
				</tr>
			</table>
		</div>
		</form>
		<form action="/PeerTool/modifiedCriteria" method="post">
		<div align="center" id="display" style="display: none;">
			<table>
				<tr>
					<td>Please give a random number to evaluate:</td>
					<td><input type="radio" name="randomNumber" id="randomNumber"
						value="${teacher.randomNumber }" checked="checked">${teacher.randomNumber }&nbsp;&nbsp;&nbsp;&nbsp; <input
						type="text" name="randomNumber" id="randomNumberText"
						style="width: 50px;" onchange="return random1();" /></td>
				</tr>
				
				<tr>
					<td>Number of submissions allowed:</td>
					<td><input type="radio" name="submissionCount"
						id="submissionCount" value="${teacher.submissionCount }" checked="checked">${teacher.submissionCount }&nbsp;&nbsp;&nbsp;&nbsp;
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
				<c:forEach items="${teacher.operationParameterses }" varStatus="i" var="review">
					<TR>
						<TD><INPUT type="checkbox" name="chk" /></TD>
						<TD>${i.index +1 }</TD>
						<TD id="one"><INPUT type='text'
							name="operationParameterses[${i.index }].criteria" value="${review.criteria}" /></TD>
						<TD><INPUT type='text' name="operationParameterses[${i.index }].weight" value="${review.weight }"/>
						</TD>
					</TR>
					</c:forEach>
				</TABLE>
			</div>
			<br /> <input align="middle" type="submit" value="Submit"
				onclick=" return validate();" />
		</div>

		<div id="footer">Copyright © Arizona State University</div>

	</form>
</body>
</html>