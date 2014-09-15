<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Spring MVC Form Handling</title>
    
    <link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/smoothness/jquery-ui.css" />
    <link rel="stylesheet" href="/resources/css/validationEngine.jquery.css" type="text/css"/>
	<link rel="stylesheet" href="/resources/css/styles.css" type="text/css" />
	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
	<!-- <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script> -->
	
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
	
    <script type="text/javascript" src="/resources/script/handlebars-v1.3.0.js"></script>
    <script type="text/javascript" src="/resources/script/Common.js"></script>
    <script type="text/javascript" src="/resources/script/jquery.validationEngine-en.js" charset="utf-8"></script>
	<script type="text/javascript" src="/resources/script/jquery.validationEngine.js" charset="utf-8"></script>
    
    <script type="text/javascript" src="/resources/script/jquery.autocomplete.js"></script>
</head>
<body >
	
		
	<div id="headerDiv">
		Media Beaver
		<div id="Menu" style="">
			<a href="/configuration">Configuration</a> | <a href="/events">Events</a> 
		</div>
	</div>
	
	
	
<!-- 	#contentDiv 
{
	width: 800px;
	margin-top: 10px;
	margin-bottom: 10px;
	margin-left: auto;
	margin-right: auto;
	text-align: left;
	padding-top:1px;
	padding-right:15px;
	padding-bottom:1px;
	padding-left:15px; 
	background-color: #FFFFFF; 
	box-shadow: 0px 0px 20px 3px #d3d3d3;
	border-radius: 4px;
}
	 -->
	<div id="outerDiv" style="width: 1000px; margin-left: auto; margin-right: auto; padding-top: 10px;">
	
		<div id="menuDiv" 
		style="float:left; width: 160px; height: 200px; 
		background-color: #FFFFFF; box-shadow: 0px 0px 20px 3px #d3d3d3;border-radius: 4px; ">
		
			<div style="font-size:16; width: 147px; height: 187px; background-color: #3E403F; padding-left: 3px;padding-top: 3px;
			border-radius: 4px; margin-top: 5px;margin-left: 5px;margin-bottom: 10px;margin-right: 10px;">
				<a href="/configuration">Configuration</a>
			</div>
		</div>
		
		<div id="contentDiv">
		
		
		
	
	
	
	
	
	
	