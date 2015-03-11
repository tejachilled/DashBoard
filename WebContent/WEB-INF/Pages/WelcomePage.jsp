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

/*http://fc08.deviantart.net/fs24/f/2008/017/8/f/Library_page_background_by_WJD.jpg */
body {
	background-image:
		url('http://svite-league-apps-content.s3.amazonaws.com/bgimages/wood.jpg');
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
</style>
<body>
	<br />
	<br />

	<h1 align="center">${headermsg}</h1>
	<h1 align="right">
		<img src="/WebContent/WEB-INF/images/home.png" width="30"
			onclick="submit();" />&nbsp;<img src="view.png" width="30">&nbsp;<img
			src="self.png" width="30">
	</h1>
	<h4 align="right">user:
		${student.username}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</h4>
	<table align="center">
		<tr>
			<td><h2>Please select from below :</h2></td>
		</tr>
		<tr>
			<td>
				<form action="/PeerTool/studentPage" method="post">
					1)&nbsp;&nbsp;<input type="submit" value="Submit/View assignment">
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<form action="/PeerTool/eassignment" method="post">
					2)&nbsp;&nbsp;<input type="submit"
						value="Evaluate peer's assignment">
				</form>
			</td>
		</tr>
	</table>

	<br />
	<h4 align="center">${uploadmsg}</h4>
</body>
</html>