<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Upload</title>
<script type="text/javascript">
	function validate() {
		//alert('coming');
		var link = document.getElementById('htmlLink').value;
	//alert(link);
		var file = document.getElementById('fileChooser').value;
		//alert(link);alert(file);
		if (link == null || file == null || link == '' || file == '') {
			alert('Please fill mandatory fields');
			return false;
		}
		//alert(file);alert(file.indexOf('.zip'));
		if (file.indexOf('.zip') == -1) {
			alert('Please upload proper zip file');
			return false;
		}
		
		var count = ${student.count}
		if(count ==0){
			alert('Number of submissions reached max!');
			return false;
		}
	}
</script>
<style>
#msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

body {
	background-image: url('${pageContext.request.contextPath}/resources/bg.png');
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
	clear: both;
	position: fixed;
	text-align: center;
	bottom: 0;
	width: 100%;
}
</style>
</head>
<body>
<div id="header">
		<h1>${headermsg}</h1>
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
	<form method="post" action="/PeerTool/upload" enctype="multipart/form-data">
		
		<h4 align="center">Number of submissions left: ${student.count } </h4>
		<br /> <br />
		<div id="msg">
			<table align="center">
				<tr>
					<td>
						HTML link:*
					</td>
					<td><input type="text" name="link" id="htmlLink"/></td>
				</tr>
				<tr>
					<td>Select a file to upload:*</td>
					<td><input type="file" name="fileChooser" id="fileChooser" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Upload"
						onclick="return validate();" /></td>
				</tr>
			</table>
		</div>
		<h4 align="center">${uploadmsg}</h4>
		
	</form>
<div id="footer">Copyright © Arizona State University</div>
</body>
</html>