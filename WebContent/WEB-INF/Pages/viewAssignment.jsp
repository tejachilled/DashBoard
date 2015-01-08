<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Assignment</title>
<style type="text/css" media="screen">
body {
	height: 100%;
}

#left_div {
	position: relative;
	float: left;
	height: 100%;
	width: 49%;
	margin: auto;
	text-align: center;
	border: 1px solid #000;
}

#right_div {
position: relative;
	float: right;
	height: 100%;
	width: 49%;
	margin: auto;
	text-align: left;
	border: 1px solid #000;
}

#textarea {
	display: block;
	margin-left: auto;
	margin-right: auto;
}
</style>
</head>
<body>
	<h1 align="center">${headermsg}</h1>
	<br />
	<div id="left_div">
		<img src="data:image/jpeg;base64,${ImageFile}" align="left" width="100%" />
	</div>
	<div id="right_div">
		<table border="1" width="100%">
			<tr>
				<td><textarea cols="1" rows="27" readonly="readonly"
						style="width: 100%;">${ContentFile}</textarea></td>
			</tr>
		</table>
	</div>
	<br/>
	<table border="1" width="60%" align="center">
			<tr>
				<td align="center"><big>${reviewheader}</big> </td>
			</tr>
			<tr>
				<td><c:if test="${not empty student.review}"><c:forEach var="listValue" items="${student.review}">
				<li>${listValue}</li></c:forEach></c:if></td>
			</tr>
		</table>

</body>
</html>