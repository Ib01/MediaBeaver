<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<html>
	<head>
	    <title>Welcome to Media Beaver</title>
	    <link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/smoothness/jquery-ui.css" />
	    <link rel="stylesheet" href="/resources/css/validationEngine.jquery.css" type="text/css"/>
		<link rel="stylesheet" href="/resources/css/styles.css" type="text/css" />
		
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
	    <script type="text/javascript" src="/resources/script/handlebars-v1.3.0.js"></script>
	    <script type="text/javascript" src="/resources/script/Common.js"></script>
	    <script type="text/javascript" src="/resources/script/jquery.validationEngine-en.js" charset="utf-8"></script>
		<script type="text/javascript" src="/resources/script/jquery.validationEngine.js" charset="utf-8"></script>
	    <script type="text/javascript" src="/resources/script/jquery.autocomplete.js"></script>
	    
	    <myTags:ErrorDisplay modelObject="configuration"/>
	    
	    
	    <script type="text/javascript" >
	
			$(function ()
			{	
				$("#Enter").click(function() 
				{
					if($("form:first").validationEngine('validate'))
					{
						$("form:first").attr("action", "/configuration/initialise");
						$("form:first").submit();
					}
				});
							
	
				//$('#formID1').validationEngine('hide');
				//$('#formID1').validationEngine('hideAll');
			}); 
		

		</script>
	    
	</head>

	<body style="background-color: #323232">
	
			<div style="
				width: 800px;
				height: 580px;
				position: absolute;
    			top: 0; right: 0; bottom: 0; left: 0;
    			margin: auto;
				background-color: #FFFFFF; 
    			text-align: left;
				padding-top:1px;
				padding-right:15px;
				padding-bottom:1px;
				padding-left:15px;
				box-shadow: 0px 0px 20px 3px #d3d3d3;
				border-radius: 4px;">
				
				<p style="font-size: 36px">Welcome to Media Beaver <br/><span style="font-size: 24px; margin-left: 50px;">The platform independent media management solution</span></p>
				<p style="font-size: 18px">Enter your details below to get started</p>
		
				<form:form method="POST" commandName="configuration" id="configForm" class="formLayout">
		
					<div class="level1Panel">
						<form:label path="sourceDirectory">Download Directory</form:label>
						<form:input path="sourceDirectory" style="width: 550px" class="autoComplete validate[required]"/>
						<br>
						<p>This is the directory you use to store your media files before they are renamed and organised.</p>
					</div>
					<div class="level1Panel">
						<form:label path="tvRootDirectory">Tv Directory</form:label>
						<form:input path="tvRootDirectory" style="width: 550px" class="validate[required]"/>
						<br>
						<p>This field should point to the directory you keep your TV Episodes in</p>
					</div>
					<div class="level1Panel">
						<form:label path="movieRootDirectory">Movie Directory</form:label>
						<form:input path="movieRootDirectory" style="width: 550px" class="validate[required]"/>
						<br>
						<p>This field should point to the directory you keep your Movies in</p>
					</div>
		
					<br>
					<a class="mainButton" href="#" id="Enter">Enter</a>
					
					
					
				</form:form>
				
				
			</div> 
		
	
	</body>

</html>


























