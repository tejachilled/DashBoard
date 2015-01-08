<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Peer Review Tool</title>
</head>
<body>
	<h1 align="center">${headermsg}</h1>
	<h4 align="right">user: ${student.username}</h4>
	<br/><br/><br/><br/>
	<h3 align="center">To submit your Assignment - <a href="/PeerTool/submitwork">Click here</a></h3>
	<h3 align="center">To evaluate peer's Assignment - <a href="/PeerTool/eassignment">Click here</a></h3>
	<h3 align="center">To view your Assignment - <a href="/PeerTool/vassignment">Click here</a></h3>
	<br/>
	<h4 align="center">${uploadmsg}</h4>
</body>
</html>