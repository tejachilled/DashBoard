<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

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
</style>
</head>
<script>
	function submit() {
		var myForm = document.getElementById("form");
		myForm.submit();
	}
</script>
<body >
<form action="/PeerTool/WelcomePage" method="post" >
	<h1 align="center">${headermsg} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="home.png" width="30" onclick="submit();">&nbsp;<img src="view.png" width="30">&nbsp;<img src="self.png" width="30"></h1>
	<br />
<div>
	<div id="left_div">
		<img src="${ImageFile}" align="left" width="100%" />
	</div>
	<div id="right_div">
	Summary Analytics<br><br>
<div class="example">
    <div class="tabs">

<div id="tab0"> <a href="#tab0">Metadata</a>
      <div>
      Assignment1_JenniferTimm<Br><br>
      ${student.submission_date}<br><br>
      ${student.imagesNumber}<br><br>
      ${student.charCount} characters<br><br>
      ${student.wordCount} words<br><br>
      </div>
     </div>

     <div id="tab2"> <a href="#tab2">Text </a>
      <div>... 30 lines of CSS is rather a lot, and...</div>
     </div>

     <div id="tab3"> <a href="#tab3">Image </a>
      <div>... that 2 should have been enough, but...</div>
     </div>

     <div id="tab1"> <a href="#tab1">FullText</a>
      <div>
      ${ContentFile}
</div>
     </div>
     </div>
    </div>
   </div>
 </div> 
	<br/>

	<table border="1" width="60%" align="right">
			<tr>
				<td align="center"><big>Review</big> </td>
			</tr>
			<tr>
				<td>
				<div class="CSSTableGenerator">
					<table>
						<tr>
							<td colspan="3">Reviewer 1: <font color="orange"><b>90</b></font></td>
						</tr>
						<tr>
							<td>
							Data analysis (30%)
							</td>
							<td>
								30
							</td>
							<td>
								Thorough exploratory analysis.
							</td>
						</tr>
						<tr>
							<td>Rationales of visualization design and story (30%)</td>
							<td>
							 	30
							</td>
							<td>
							
							</td>
						</tr>
						<tr>
							<td>Visualization clarity (10%)</td>
							<td>
							10
							</td>
							<td>
							Love the icon. The vis title clearly highlight the results.
							</td>
						</tr>
						<tr>
							<td>Consistency (5%)</td>
							<td>
							5
							</td>
							<td>
							
							</td>
						</tr>
						<tr>
							<td>Aesthetic (10%)</td>
							<td>
							5
							</td>
							<td>
							Given the icon was added to improve the readability, but the bar chart is not particularly apprealling
							</td>
						</tr>	
						
							<tr>
							<td>Originality (15%)</td>
							<td>
								10
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
					<div class="CSSTableGenerator">
					<table>						
						<tr>
							<td colspan="3">Reviewer 2: <font color="orange"><b>85</b></font></td>
						</tr>
						<tr>
							<td>
							Data analysis (30%)
							</td>
							<td>
								30
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td>Rationales of visualization design and story (30%)</td>
							<td>
							 	30
							</td>
							<td>
								Since it's analyzing Djokovic's success, maybe a picture of his rather than an icon will elevate the attention. Just a thought.
							</td>
						</tr>
						<tr>
							<td>Visualization clarity (10%)</td>
							<td>
							10
							</td>
							<td>
								It's very clear. 
							</td>
						</tr>
						<tr>
							<td>Consistency (5%)</td>
							<td>
							5
							</td>
							<td>
								consistent coloring.
							</td>
						</tr>
						<tr>
							<td>Aesthetic (10%)</td>
							<td>
							5
							</td>
							<td>
								Too simple.
							</td>
						</tr>	
						
							<tr>
							<td>Originality (15%)</td>
							<td>
								5
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
					<div class="CSSTableGenerator">
					<table>
						<tr>
							<td colspan="3">Meta Reviewer: <font color="orange"><b>92</b></font></td>
						</tr>
						<tr>
							<td>
							Data analysis (30%)
							</td>
							<td>
								30
							</td>
							<td>
								Very comprehensive exploratory analysis. Good call on not modeling data yet. 
							</td>
						</tr>
						<tr>
							<td>Rationales of visualization design and story (30%)</td>
							<td>
							 	30
							</td>
							<td>
								Reference to reviewer 2. 
							</td>
						</tr>
						<tr>
							<td>Visualization clarity (10%)</td>
							<td>
							10
							</td>
							<td>
								Clear icon key improves visualization clarity.
							</td>
						</tr>
						<tr>
							<td>Consistency (5%)</td>
							<td>
							5
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td>Aesthetic (10%)</td>
							<td>
							7
							</td>
							<td>
								
							</td>
						</tr>	
						
							<tr>
							<td>Originality (15%)</td>
							<td>
								10
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