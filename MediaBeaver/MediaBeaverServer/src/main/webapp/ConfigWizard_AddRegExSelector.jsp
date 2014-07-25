<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
	
	
		$(function ()
		{	
			$("#Save").click(function() 
			{
				$("form:first").attr("action", "/configWizard/addRegExSelectorSave");
				$("form:first").submit();
			});
			
			$("#Previous").click(function() 
			{
				$("form:first").attr("action", "/configWizard/regExSelectorsPrevious");
				$("form:first").submit();
			});
			
		}); 
			
	</script>

	
	<h2>Config Wizard: Add or Update Regex Selector</h2>
	
	<form:form method="POST" commandName="config" id="configForm" class="formLayout">
		
		<form:label path="selectedRegExSelector.description">Description</form:label>
   		<form:input path="selectedRegExSelector.description" style="width:400px" id="description" class="validate[required]" />
		<br/>
		
   		<form:label path="selectedRegExSelector.expression">Expression</form:label>
   		<form:input path="selectedRegExSelector.expression" style="width:650px" id="expression" class="validate[required]" data-prompt-position="topRight: -130"/>
		
		
		
		<br>
		<br>
		<input type="button" value="Save" style="width: 100" id="Save"/><br>
		<input type="button" value="Cancel" style="width: 100" id="Cancel"/>
		<br>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>












































