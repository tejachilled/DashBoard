<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Assignment</title>
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
	function submit() {
		var myForm = document.getElementById("form");
		myForm.submit();
	}

	function validate(){		
		var tMarks = '${marks1}';
		alert(tMarks.length);
		if(tMarks!=null){
			if(tMarks.length>5){
				permission('1');permission('2');permission('3');
			}
			else{				
				document.getElementById('mainTable').style.display = 'none';
				document.getElementById('table1').style.display = 'none';
				document.getElementById('table2').style.display = 'none';
				document.getElementById('table3').style.display = 'none';
			}
		} else{
			document.getElementById('mainTable').style.display = 'none';
			document.getElementById('table1').style.display = 'none';
			document.getElementById('table2').style.display = 'none';
			document.getElementById('table3').style.display = 'none';
		}
	}
	function permission(str){		
		if(str==1){
			document.getElementById('da'+str).value = '${marks1.analysis}';
			document.getElementById('ra'+str).value = '${marks1.design}';
			document.getElementById('ca'+str).value = '${marks1.vc}';
			document.getElementById('con'+str).value = '${marks1.consistency}';
			document.getElementById('as'+str).value = '${marks1.aesthetic}';
			document.getElementById('org'+str).value = '${marks1.orginality}';	
		}else if(str==2){
			document.getElementById('da'+str).value = '${marks2.analysis}';
			document.getElementById('ra'+str).value = '${marks2.design}';
			document.getElementById('ca'+str).value = '${marks2.vc}';
			document.getElementById('con'+str).value = '${marks2.consistency}';
			document.getElementById('as'+str).value = '${marks2.aesthetic}';
			document.getElementById('org'+str).value = '${marks2.orginality}';	
		} else if(str==3){
			document.getElementById('da'+str).value = '${marks3.analysis}';
			document.getElementById('ra'+str).value = '${marks3.design}';
			document.getElementById('ca'+str).value = '${marks3.vc}';
			document.getElementById('con'+str).value = '${marks3.consistency}';
			document.getElementById('as'+str).value = '${marks3.aesthetic}';
			document.getElementById('org'+str).value = '${marks3.orginality}';	
		}
	}


</script>
<body style="height: 100%;" onload="validate();">
	<form action="/PeerTool/WelcomePage" method="post" >
	<h1 align="center">${headermsg} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="home.png" width="30" onclick="submit();">&nbsp;<img src="view.png" width="30">&nbsp;<img src="self.png" width="30"></h1>
	<br/>
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
      Assignment1_JenniferTimm<Br><br>
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

	<table border="1" width="60%" align="right" id="mainTable">
			<tr>
				<td align="center"><big>Review</big> </td>
			</tr>
			<tr>
				<td>
				<div class="CSSTableGenerator" id="table1">
					<table>
						<tr>
							<td colspan="3">Meta Reviewer: <font color="orange"><b>90</b></font></td>
						</tr>
						<tr>
							<td>
							Data analysis (30%)
							</td>
							<td>
								<input type="text" style="width: 50px;" id="da1" name="da1" value="30" readonly="readonly">
							</td>
							<td>
								Thorough exploratory analysis.
							</td>
						</tr>
						<tr>
							<td>Rationales of visualization design and story (30%)</td>
							<td>
							 	<input type="text" style="width: 50px;" id="ra1" name="ra1" readonly="readonly">
							</td>
							<td>
							
							</td>
						</tr>
						<tr>
							<td>Visualization clarity (10%)</td>
							<td>
							<input type="text" style="width: 50px;" id="ca1" name="ca1" value="2" >
							</td>
							<td>
							Love the icon. The vis title clearly highlight the results.
							</td>
						</tr>
						<tr>
							<td>Consistency (5%)</td>
							<td>
							<input type="text" style="width: 50px;" id="con1" name="con1" readonly="readonly">
							</td>
							<td>
							
							</td>
						</tr>
						<tr>
							<td>Aesthetic (10%)</td>
							<td>
							<input type="text" style="width: 50px;" id="as1" name="as1" readonly="readonly">
							</td>
							<td>
							Given the icon was added to improve the readability, but the bar chart is not particularly apprealling
							</td>
						</tr>	
						
							<tr>
							<td>Originality (15%)</td>
							<td>
								<input type="text" style="width: 50px;" id="org1" name="org1" readonly="readonly">
							</td>
							<td>
								I don't understand the color selection.
							</td>
						</tr>					
					</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="CSSTableGenerator" id="table2">
					<table>						
						<tr>
							<td colspan="3">Reviewer 1: <font color="orange"><b>85</b></font></td>
						</tr>
						<tr>
							<td>
							Data analysis (30%)
							</td>
							<td>
								<input type="text" style="width: 50px;" id="da2" name="da2" value="30" readonly="readonly">
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td>Rationales of visualization design and story (30%)</td>
							<td>
							 	<input type="text" style="width: 50px;" id="ra2" name="ra2" readonly="readonly">
							</td>
							<td>
								Since it's analyzing Djokovic's success, maybe a picture of his rather than an icon will elevate the attention. Just a thought.
							</td>
						</tr>
						<tr>
							<td>Visualization clarity (10%)</td>
							<td>
							<input type="text" style="width: 50px;" id="ca2" name="ca2" value="2" >
							</td>
							<td>
								It's very clear. 
							</td>
						</tr>
						<tr>
							<td>Consistency (5%)</td>
							<td>
							<input type="text" style="width: 50px;" id="con2" name="con2" readonly="readonly">
							</td>
							<td>
								consistent coloring.
							</td>
						</tr>
						<tr>
							<td>Aesthetic (10%)</td>
							<td>
							<input type="text" style="width: 50px;" id="as2" name="as2" readonly="readonly">
							</td>
							<td>
								Too simple.
							</td>
						</tr>	
						
							<tr>
							<td>Originality (15%)</td>
							<td>
								<input type="text" style="width: 50px;" id="org2" name="org2" readonly="readonly">
							</td>
							<td>
								Not particularly impressive visualization.
							</td>
						</tr>					
					</table>
					</div>
				</td>
				
			</tr>
			<tr>
				<td bgcolor="#FFFFCC">
					<div class="CSSTableGenerator" id="table3">
					<table>
						<tr>
							<td colspan="3">Reviewer 2: <font color="orange"><b>92</b></font></td>
						</tr>
						<tr>
							<td>
							Data analysis (30%)
							</td>
							<td>
								<input type="text" style="width: 50px;" id="da3" name="da3" value="30" readonly="readonly">
							</td>
							<td>
								Very comprehensive exploratory analysis. Good call on not modeling data yet. 
							</td>
						</tr>
						<tr>
							<td>Rationales of visualization design and story (30%)</td>
							<td>
							 	<input type="text" style="width: 50px;" id="ra3" name="ra3" readonly="readonly">
							</td>
							<td>
								Reference to reviewer 2. 
							</td>
						</tr>
						<tr>
							<td>Visualization clarity (10%)</td>
							<td>
							<input type="text" style="width: 50px;" id="ca3" name="ca3" value="2" >
							</td>
							<td>
								Clear icon key improves visualization clarity.
							</td>
						</tr>
						<tr>
							<td>Consistency (5%)</td>
							<td>
							<input type="text" style="width: 50px;" id="con3" name="con3" readonly="readonly">
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td>Aesthetic (10%)</td>
							<td>
							<input type="text" style="width: 50px;" id="as3" name="as3" readonly="readonly">
							</td>
							<td>
								
							</td>
						</tr>	
						
							<tr>
							<td>Originality (15%)</td>
							<td>
								<input type="text" style="width: 50px;" id="org3" name="org3" readonly="readonly">
							</td>
							<td>
								What would you do if bar charts are not allowed to use?
							</td>
						</tr>					
					</table>
					</div>
				</td>
				
			</tr>
		</table>
		
</form>
				

</body>
</html>