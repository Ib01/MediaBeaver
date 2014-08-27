<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <%@include file="includes/header.jsp"%> --%>
<html>
	<head>
		<!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"> -->
		 <!-- <script src="//code.jquery.com/jquery-1.10.2.js"></script> -->
		 <!-- <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script> -->
		 
		 <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
		 <script type="text/javascript" src="/resources/script/jquery.autocomplete.js"></script>
		 
		 <style>
		 
			 body { font-family: sans-serif; font-size: 14px; line-height: 1.6em; margin: 0; padding: 0; }
			.container { width: 800px; margin: 0 auto; }
			
			.autocomplete-suggestions { border: 1px solid #999; background: #FFF; cursor: default; overflow: auto; -webkit-box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); -moz-box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); }
			.autocomplete-suggestion { padding: 2px 5px; white-space: nowrap; overflow: hidden; }
			.autocomplete-no-suggestion { padding: 2px 5px;}
			.autocomplete-selected { background: #F0F0F0; }
			.autocomplete-suggestions strong { font-weight: normal; color: #3399FF; }
			
			input { font-size: 28px; padding: 10px; border: 1px solid #CCC; display: block; margin: 20px 0; }
		 </style>
	</head>
	<body>
	
	

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

		/* function setAutoComplete()
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
		 */
		 
		function setAutoComplete()
		{
			 var countries = [
                { value: '{Andorra}', data: 'AD' },
                { value: '{Zimbabwe}', data: 'ZZ' },
                { value: '.Trim', data: 'Trim' },
                { value: '.Split', data: 'Split' }
             ];

			 var methods = [
			                  { value: '.Trim', data: 'Trim' },
			                  { value: '.Split', data: 'Split' }
			               ];
			 
			 
             $('.autoComplete').autocomplete({
                 lookup: countries,
                 minChars: 1,
                 delimiter: /\s*/
             });
             
             
             
             alert("adf");
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
	
<%-- <%@include file="includes/footer.jsp"%> --%>

</body>
</html>










































