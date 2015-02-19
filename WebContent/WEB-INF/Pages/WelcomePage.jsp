<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Peer Review Tool</title>
</head>
<script type="text/javascript">
window.onload=function() {
	   document.getElementById("vassignment").onclick=function() {
	     var myForm = document.createElement("form");
	     myForm.action=this.href;// the href of the link
	     myForm.target="myFrame";
	     myForm.method="POST";
	     myForm.submit();
	    return false; // cancel the actual link
	   }
	   document.getElementById("eassignment").onclick=function() {
	     var myForm = document.createElement("form");
	     myForm.action=this.href;// the href of the link
	     myForm.target="myFrame";
	     myForm.method="POST";
	     myForm.submit();
	    return false; // cancel the actual link
	   }
	 }
</script>
<style>

/*http://fc08.deviantart.net/fs24/f/2008/017/8/f/Library_page_background_by_WJD.jpg */
body {
background-image: url('http://svite-league-apps-content.s3.amazonaws.com/bgimages/wood.jpg');

	background-size: cover;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	margin: 0;
	padding: 0;
}
h1 {color: white;}

</style>
<body >
<br/><br/>
	<h1 align="center">${headermsg}</h1>
	<h4 align="right">  user: ${student.username}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </h4>
	<br/><br/><br/><br/>
	<h3 align="center">To submit your Assignment - <a href="/PeerTool/submitwork">Click here</a></h3>
	<h3 align="center">To evaluate peer's Assignment - <a href="/PeerTool/eassignment" id="eassignment">Click here</a></h3>
	<h3 align="center">To view your Assignment - <a href="/PeerTool/vassignment" id="vassignment">Click here</a></h3>
	<br/>
	<h4 align="center">${uploadmsg}</h4>
</body>
</html>