<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Peer Review Tool</title>
</head>
<script type="text/javascript">
	window.onload = function() {
		document.getElementById("vassignment").onclick = function() {
			var myForm = document.createElement("form");
			myForm.action = this.href;// the href of the link
			myForm.target = "myFrame";
			myForm.method = "POST";
			myForm.submit();
			return false; // cancel the actual link
		}
		document.getElementById("eassignment").onclick = function() {
			var myForm = document.createElement("form");
			myForm.action = this.href;// the href of the link
			myForm.target = "myFrame";
			myForm.method = "POST";
			myForm.submit();
			return false; // cancel the actual link
		}
	}
</script>
<style>

/* http://svite-league-apps-content.s3.amazonaws.com/bgimages/wood.jpg */
body {
	background-image:
		url('${pageContext.request.contextPath}/resources/peer1.jpg');
	background-size: cover;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	margin: 0;
	padding: 0;
}

h1 {
	color: white;
}
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
	position: absolute; bottom : 0;
	width: 100%;
	bottom: 0;
}
</style>
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
	<table align="center">
		<tr>
			<td><h2>Please select from below :</h2></td>
		</tr>
		<tr>
			<td>
				<form action="/PeerTool/studentPage" method="post">
				 <input type="submit" value="Submit/View assignment">
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<form action="/PeerTool/eassignment" method="post">
					<input type="submit"
						value="Evaluate peer's assignment">
				</form>
			</td>
		</tr>
	</table>
 
	<br />
	<h4 align="center" style="color:aqua;">${uploadmsg}</h4>
	<div id="footer">Copyright © Arizona State University</div>
</body>
</html>