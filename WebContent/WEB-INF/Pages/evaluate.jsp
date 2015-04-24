<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evaluate Assignment</title>
<style type="text/css" media="screen">
/* Tabbed example */
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
<script>
function validate(error) {
		if(error == 1){
			alert('Please give correct scores!');
			return false;
		}
		
		return true;
}
function onLoad(){
	var i=0;
	var teacherMark = '${peer.teacherTotMarks}';
	var toDIsplaySize = '${fn:length(reviewer.reviewCriteria)}';
	if(teacherMark >0 ){
		for(i =0;i<toDIsplaySize;i++){
			document.getElementById('operationParameterses['+i+'].weight').readOnly = true;
			document.getElementById('operationParameterses['+i+'].criteria').readOnly = true;
		}
	}
	return true;
}

</script>
<body style="height: 100%;" onload="return onLoad();">
	<div>
	<h1 align="center">${headermsg}</h1>
	<div align="right">user: ${reviewer.username}
		<a href="/PeerTool/Welcome"> <img
			src="${pageContext.request.contextPath}/resources/home.png"
			width="30">
		</a> <a href="/PeerTool/logout"> <img
			src="${pageContext.request.contextPath}/resources/logout.png"
			width="30">
		</a>	
	</div>
</div>
	
	<form action="/PeerTool/saveMarks" method="post" >
	<input type="hidden" name = "peerId" value="${peer.username }">
	<h4 style="color: red" align="center">${customMsg}</h4>
	<div>
		<div class="left_div" >
			<center><b >peer Website</b></center>
			<object type="text/html" data="${peer.link}" style="width: 100%; height: 100%; min-height: 30em;"> </object>
		</div>
			<div id="right_div">
	<center><b>Summary Analytics</b></center><br/>
<div>
    <div class="tabs">

<div id="tab0"> <a href="#tab0">Metadata</a>
      <div>
      ${peer.username }<br><br>
      Assignment id:  ${peer.assignment_id}<br><br>
      ${peer.submission_date}<br><br>
      No of Images: ${peer.imagesNumber}<br><br>
      ${peer.charCount} characters<br><br>
      ${peer.wordCount} words<br><br>
      </div>
     </div>
	<input type="hidden" name = "mode" value="${mode}">
     <div id="tab2"> <a href="#tab2">Text </a>
      <div><pre>${peer.content}</pre></div>
     </div>

     <div id="tab3"> <a href="#tab3">Image </a>
      <div><c:forEach items="${peer.images}" var="img">
		<img src="${img}" style="width: 50%; height: 50%;"/><br/></c:forEach></div>
     </div>

     <div id="tab1" > <a href="#tab1">FullText</a>
      <div style="display: block; white-space:normal;"><pre>
     <c:out value="${peer.fullContext}"/></pre>
</div>
     </div>
     </div>
    </div>
   </div>
 </div> 
	<br/>
<c:set var="error" value="0"/>
	<table border="1" width="80%" align="center">
			<tr>
				<td align="center"><big>Review Pannel </big> </td>
			</tr>
			<tr>
				<td>
				<div class="CSSTableGenerator" id="table1">
						<table>
							<tr>
								<td colspan="3"> Reviewer: 1 <font color="orange"></font></td>
							</tr>
							<c:forEach var="ops" items="${reviewer.reviewCriteria}"
								varStatus="j">
								<tr>
									<td style="width: 20%;">${ops.criteria}</td>
									<td style="width: 20%;"><input type="text"
										style="width: 50px;"
										id="operationParameterses[${j.index }].weight"
										name="operationParameterses[${j.index }].weight"
										value="${peer.reviewCriteria[j.index ].weight }"> /
										${ops.weight }</td>
										<c:if test="${ops.weight - peer.reviewCriteria[j.index ].weight lt 0}" >
											<c:set var="error" value="1"></c:set>
											<c:if test="${ops.weight - peer.teacherMarks[j.index ].weight gt 0}" >
												<c:set var="error" value="0"></c:set>
											</c:if>
										</c:if>
										
									<td><input type="text" style="width: 500px;"
										id="operationParameterses[${j.index }].criteria"
										name="operationParameterses[${j.index }].criteria"
										value="${peer.reviewCriteria[j.index ].criteria }">
									</td>
								</tr>
							</c:forEach>
						</table>
										
				</div>
				</td>
			</tr>
			
		</table><br/>	
		<table align="center"><tr><td>
		<input style="width: 80px;" type="submit" value="Submit" onclick="return validate('${error}');"><br/></td></tr>
		</table>
</form>
				

</body>
</html>