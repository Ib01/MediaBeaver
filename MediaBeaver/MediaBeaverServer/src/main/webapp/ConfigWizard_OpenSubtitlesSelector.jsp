<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>
<!-- <html>
	<head>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
		 <script src="//code.jquery.com/jquery-1.10.2.js"></script>
		 <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	</head>
	<body> -->
	
	<script type="text/javascript" >
	
	
		$(function ()
		{	
			$("#enableOpenSubtitles").click(function() 
			{
				$("form:first").attr("action", "/configWizard/openSubtitlesSelectorsEnable");
				$("form:first").submit();
			}); 
			
			$("#Previous").click(function() 
			{
				$("form:first").attr("action", "/configWizard/openSubtitlesSelectorsPrevious");
				$("form:first").submit();
			}); 
			
			$("#Next").click(function() 
			{
				$("form:first").attr("action", "/configWizard/openSubtitlesSelectorsNext");
				$("form:first").submit();
			}); 
		
			setAutoComplete();
		}); 

		function setAutoComplete()
		{
			var availableTags = [
	           "{Dog}",
	           "{Cat}"
	         ];
			
			$( ".autoComplete" ).autocomplete({
			  source: availableTags
			});
			
			alert("fd");
		}
		

	</script>
	
	<style>
		.detailedListItem{
			border-radius: 8px;
			background-color: #F1F1F1;
			padding: 10px;
			padding-bottom: 15px;
			margin-top: 5px;
			margin-bottom: 5px;
		}
		.formLayout .detailedListItem label{
			width: 200px;
		}
		
		.detailedListItemLevel1Box{
			background-color: white; 
			padding:7px;
			border-radius: 8px;
			margin-bottom: 6px;
		}
		
		.detailedListItem H3{
			color:#1F1F1F;
			margin-top: 2px;
			font-size: 14px;
			font-weight: normal;
		}
	</style>


	

	
	<h2>Config Wizard Step 2: Modify Open Subtitles Selectors</h2>

	<form:form method="POST" commandName="config" id="configForm" class="formLayout">

		<form:checkbox path="includeOpenSubtitles" id="enableOpenSubtitles"/> Enable Open Subtitles Selector
	
		<form:hidden path="selectedRegExSelectorIndex"/>
		
		<div class="detailedListItem ui-widget">
		
			<c:forEach items="${config.openSubtitlesSelectors}" var="selector" varStatus="i">
	
				<%-- <div class="ui-widget">
				  <label for="tags">Tags: </label>
				  <!-- <input id="tags" class="tags"> -->
				  <form:input path="openSubtitlesSelectors[${i.index}].openSubititleField" style="width: 400px" class="tags"/>
				</div> --%>
	
	
				  
				<label><c:out value="${selector.pathTokenName}" /></label>
			
				<form:input path="openSubtitlesSelectors[${i.index}].openSubititleField" style="width: 400px" class="autoComplete"/>
				
				<br/ >
			</c:forEach>
			
		</div>
	 
		<br>
		<br>
		<a class="mainButton" href="#" id="Next">Next</a>
		<a class="mainButton" href="#" id="Previous">Previous</a>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>
<!-- 
</body>
</html>
 -->









































