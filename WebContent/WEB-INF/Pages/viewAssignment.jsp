<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Assignment</title>
<style type="text/css" media="screen">
/* Tabbed example */
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
	text-align: center;
	bottom: 0;
	width: 100%;
	position: fixed;
}
div.tabs {
  min-height: 29em;		/* No height: can grow if :target doesn't work */
  position: relative;		/* Establish a containing block */
  line-height: 1;		/* Easier to calculate with */
  z-index: 0}			/* So that we can put other things behind */
div.tabs > div {
  display: inline}		/* We want the buttons all on one line */
div.tabs > div > a {
  color: black;			/* Looks more like a button than a link */
  background: #CCC;		/* Active tabs are light gray */
  padding: 0.2em;		/* Some breathing space */
  border: 0.1em outset #BBB;	/* Make it look like a button */
  border-bottom: 0.1em solid #CCC} /* Visually connect tab and tab body */
div.tabs > div:not(:target) > a {
  border-bottom: none;		/* Make the bottom border disappear */
  background: #999}		/* Inactive tabs are dark gray */
div.tabs > div:target > a,	/* Apply to the targeted item or... */
:target #default2 > a {		/* ... to the default item */
  border-bottom: 0.1em solid #CCC; /* Visually connect tab and tab body */
  background: #CCC}		/* Active tab is light gray */
div.tabs > div > div {
  background: #CCC;		/* Light gray */
  z-index: -2;			/* Behind, because the borders overlap */
  left: 0; top: 1.3em;		/* The top needs some calculation... */
  bottom: 0; right: 0;		/* Other sides flush with containing block */
  overflow: auto;		/* Scroll bar if needed */
  padding: 0.3em;		/* Looks better */
  border: 0.1em outset #BBB}	/* 3D look */
div.tabs > div:not(:target) > div { /* Protect CSS1 & CSS2 browsers */
  position: absolute }		/* All these DIVs overlap */
div.tabs > div:target > div, :target #default2 > div {
  position: absolute;		/* All these DIVs overlap */
  z-index: -1}			/* Raise it above the others */

div.tabs :target {
  outline: none}

.CSSTableGenerator {
	margin:0px;padding:0px;
	width:100%;
	box-shadow: 10px 10px 5px #888888;
	border:1px solid #000000;
	
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
	
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
	
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
	
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}.CSSTableGenerator table{
    border-collapse: collapse;
        border-spacing: 0;
	width:100%;
	height:100%;
	margin:0px;padding:0px;
}.CSSTableGenerator tr:last-child td:last-child {
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
}
.CSSTableGenerator table tr:first-child td:first-child {
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}
.CSSTableGenerator table tr:first-child td:last-child {
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
}.CSSTableGenerator tr:last-child td:first-child{
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
}.CSSTableGenerator tr:hover td{
	background-color:#cccc99;
		

}
.CSSTableGenerator td{
	vertical-align:middle;
	
	background-color:#cccccc;

	border:1px solid #000000;
	border-width:0px 1px 1px 0px;
	text-align:left;
	padding:7px;
	font-size:14px;
	font-family:Arial;
	font-weight:normal;
	color:#000000;
}.CSSTableGenerator tr:last-child td{
	border-width:0px 1px 0px 0px;
}.CSSTableGenerator tr td:last-child{
	border-width:0px 0px 1px 0px;
}.CSSTableGenerator tr:last-child td:last-child{
	border-width:0px 0px 0px 0px;
}
.CSSTableGenerator tr:first-child td{
		background:-o-linear-gradient(bottom, #003366 5%, #003f7f 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #003366), color-stop(1, #003f7f) );
	background:-moz-linear-gradient( center top, #003366 5%, #003f7f 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#003366", endColorstr="#003f7f");	background: -o-linear-gradient(top,#003366,003f7f);

	background-color:#003366;
	border:0px solid #000000;
	text-align:center;
	border-width:0px 0px 1px 1px;
	font-size:14px;
	font-family:Arial;
	font-weight:bold;
	color:#ffffff;
}
.CSSTableGenerator tr:first-child:hover td{
	background:-o-linear-gradient(bottom, #003366 5%, #003f7f 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #003366), color-stop(1, #003f7f) );
	background:-moz-linear-gradient( center top, #003366 5%, #003f7f 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#003366", endColorstr="#003f7f");	background: -o-linear-gradient(top,#003366,003f7f);

	background-color:#003366;
}
.CSSTableGenerator tr:first-child td:first-child{
	border-width:0px 0px 1px 0px;
}
.CSSTableGenerator tr:first-child td:last-child{
	border-width:0px 0px 1px 1px;
} 


div.left_div {
	float: left;
	height: 100%;
	position: relative;
	width: 49%;
	border: 1px solid #000;
	min-height: 31em;
  	
}

#right_div {
position: relative;
min-height: 31em;
	float: right;
	width: 49%;
	height: 100%;
	border: 1px solid #000;
}

 
html, body{
	height: 100%;
   min-height: 100%;
}
</style>
</head>
<body style="height: 100%;" >
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
<c:if test="${ student.charCount eq 0  }">
	<h3 align="center"> You didn't upload this assignment !</h3>
	<div id="footer" style=" margin: 0 auto; position: absolute;">Copyright © Arizona State University</div>	
</c:if>
<c:if test="${ student.charCount gt 0  }">
	<form action="/PeerTool/WelcomePage" method="post" >
	
	<div>
		<div class="left_div" >
			<center><b >Student Website</b></center>
			<object type="text/html" data="${student.link}" style="width: 100%; height: 100%; min-height: 30em;"> </object>
		</div>
			<div id="right_div">
	<center><b>Summary Analytics</b></center><br/>
<div>
    <div class="tabs">

<div id="tab0"> <a href="#tab0">Metadata</a>
     <div>
      Name : ${student.username }<br><br>
      Assignment id:  ${student.assignment_id}<br><br>
      ${student.submission_date}<br><br>
      No of Images: ${student.imagesNumber}<br><br>
      ${student.charCount} characters<br><br>
      ${student.wordCount} words<br><br>
      </div>
     </div>

     <div id="tab2"> <a href="#tab2">Text </a>
      <div><pre>${student.content}</pre></div>
     </div>

     <div id="tab3"> <a href="#tab3">Image </a>
      <div><c:forEach items="${student.images}" var="img">
		<img src="${img}" style="width: 50%; height: 50%;"/><br/></c:forEach></div>
     </div>

     <div id="tab1" > <a href="#tab1">FullText</a>
      <div style="display: block; white-space:normal;"><pre>
     <c:out value="${student.fullContext}"/></pre>
</div>
     </div>
     </div>
    </div>
   </div>
 </div> 
	<br/>
<c:if test="${fn:length(student.teacherMarks) >0 }">
	<table border="1" width="60%" align="center" id="mainTable">
			<tr>
				<td align="center"><big>Review</big> </td>
			</tr>
			<tr>
				<td>
				<c:set var="count" value="0" />
				
				
				<div class="CSSTableGenerator" id="table1">
					<table >
							<tr>
								<td colspan="3"> Meta Reviewer <font color="orange">: ${student.teacherTotMarks } </font></td>
							</tr>
							<c:forEach var="ops" items="${student.toDisplay}"
								varStatus="j">
								<tr>
									<td style="width: 20%;">${ops.criteria}</td>
									<td style="width: 20%;"><input type="text"
										style="width: 50px;" readonly="readonly"
										value="${student.teacherMarks[j.index ].weight }"> /
										${ops.weight }</td>
									<td><input type="text" style="width: 500px;" readonly="readonly"
										value="${student.teacherMarks[j.index ].criteria }">
									</td>
								</tr>
							</c:forEach>
					</table>	
					<c:forEach begin="1" end="${fn:length(student.reviewCriteria)/2}" varStatus="i" >
						<table >
							<tr>
								<td colspan="3"> Reviewer: ${i.index}  <font color="orange">: ${student.studentTotMarks[i.index-1] } </font></td>
							</tr>
							<c:forEach var="ops" items="${student.toDisplay}"
								varStatus="j">
								<tr>
									<td style="width: 20%;">${ops.criteria}</td>
									<td style="width: 20%;"><input type="text"
										style="width: 50px;" id = "${count }" name= "${count }" readonly="readonly"
										value="${student.reviewCriteria[count].weight }">/
										${ops.weight }</td>
											
									<td><input type="text" style="width: 500px;" readonly="readonly" id = "${count }" name= "${count }"
									value = "${student.reviewCriteria[count].criteria }">
									<c:set var="count" value="${count + 1}" />
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:forEach>						
				</div>
				
				</td>
				
			</tr>
		</table>
	</c:if>	
</form>
<div id="footer">Copyright © Arizona State University</div>	
</c:if>
				

</body>
</html>