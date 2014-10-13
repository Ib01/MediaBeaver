<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	    
	    <script type="text/javascript" >
	
		$(function ()
		{	
			$("#Enter").click(function() 
			{
				$("form:first").attr("action", "/configuration/initialise");
				$("form:first").submit();
			});
			
			
			$('#sourceDirectory').validationEngine('showPrompt', 'This a custom msg', '', 'topRight', true);
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
		
			<spring:hasBindErrors name="configuration">
				        <h2>Errors</h2>
				        
			            <ul>
				            <c:forEach var="error" items="${errors.allErrors}">
				                <li>11${error.defaultMessage}</li>
				            </c:forEach>
			            </ul>
				        
				    </spring:hasBindErrors>
		
		
		
					
		
					<form:errors path="tvRootDirectory"  />
		
		
		
					<div class="roundedPanel">
						<form:label path="sourceDirectory">Source Directory</form:label>
						<form:input path="sourceDirectory" style="width: 550px" class="autoComplete validate[required]"/>
						<br>
						<p>This is the directory you use to store your media files before they are renamed and organised. If you download 
						content regulalry you will probably want to make this your source directory.</p>
					</div>
					<div class="roundedPanel">
						<form:label path="tvRootDirectory">Tv Directory</form:label>

				<!-- <div class="tvRootDirectoryformError parentFormconfigForm formError"
					style="position: absolute; opacity: 0.87; top: 192px; left: 694px; margin-top: -32px;">
					<div class="formErrorContent">
						* This field is required<br>
					</div>
					<div class="formErrorArrow">
						<div class="line10">
							
						</div>
						<div class="line9">
							
						</div>
						<div class="line8">
							
						</div>
						<div class="line7">
							
						</div>
						<div class="line6">
							
						</div>
						<div class="line5">
							
						</div>
						<div class="line4">
							
						</div>
						<div class="line3">
							
						</div>
						<div class="line2">
							
						</div>
						<div class="line1">
							
						</div>
					</div>
				</div> -->


						<form:input path="tvRootDirectory" style="width: 550px" class="validate[required]"/>
						<br>
						<p>This field should point to the directory you keep your TV Episodes in</p>
					</div>
					<div class="roundedPanel">
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


























