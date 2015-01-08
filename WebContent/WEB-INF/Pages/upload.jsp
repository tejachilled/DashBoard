<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Upload</title>
<script type="text/javascript">
function validate(){
	//alert('coming');
	var link = document.getElementById("htmlLink").value;
	var file = document.getElementById("fileChooser").value;
	//alert(link);alert(file);
	if(link ==null || file == null || link =="" || file==""){
		alert('Please fill mandatory fields');
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
</style>
</head>
<body>
	<form method="post" action="upload" enctype="multipart/form-data">
		<h1 align="center">${headermsg}</h1>
		
		<h4 align="right">User: ${student.username}</h4>
		<h4 align="center">Number of submissions left: 2</h4>
		 <br /> <br />
		<div id="msg">
		<table align="center">
		<tr><td>
			HTML link:*</td><td>  <input type="text" id="htmlLink"> </td></tr>
			
			<tr><td> Select a file to upload:*</td><td> <input type="file" name="dataFile"
				id="fileChooser" /></td></tr><tr><td></td><td> <input type="submit"
				value="Upload" onclick="return validate();"/></td></tr>
				</table>
		</div>
		<h4 align="center">${uploadmsg}</h4>
	</form>
	
</body>
</html>