<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
	
	
		$(function ()
		{	
			$("#Save").click(function() 
			{
				$("form:first").attr("action", "/configWizard/regExSelectorSave");
				$("form:first").submit();
			});
			
			$("#Cancel").click(function() 
			{
				$("form:first").attr("action", "/configWizard/configNext");
				$("form:first").submit();
			});
			
		}); 
			
	</script>

	
	<h2>Config Wizard: Add or Update Regex Selector</h2>
	
	<form:form method="POST" commandName="regExSelector" id="configForm" class="formLayout">
		<form:hidden path="id"/>
		<form:hidden path="index"/>
		<%-- <form:hidden path="lastUpdate"/> --%>
		
		<form:label path="description">Description</form:label>
   		<form:input path="description" style="width:400px" id="description" class="validate[required]" />
		<br/>
		
   		<form:label path="expression">Expression</form:label>
   		<form:input path="expression" style="width:650px" id="expression" class="validate[required]" data-prompt-position="topRight: -130"/>
		
		<h2>Variable Setters</h2>
		
		<c:forEach items="${regExSelector.variableSetters}" varStatus="i" var="setter">
			<form:hidden path="id"/>
			<%-- <form:hidden path="lastUpdate"/> --%>
		
			<form:label path="variableSetters[${i.index}].variableName">Name</form:label>
			<c:out value="${setter.variableName}" ></c:out>
			<form:hidden path="variableSetters[${i.index}].variableName"/>
			<br style="clear: both"/>
			
			<form:label path="variableSetters[${i.index}].groupAssembly">Group Assembly</form:label>
			<form:input path="variableSetters[${i.index}].groupAssembly" style="width:100px" class="validate[required]" /> 
			<br style="clear: both"/>
			
			
			<form:label path="variableSetters[${i.index}].replaceExpression">Replace RegEx</form:label>
			<form:input path="variableSetters[${i.index}].replaceExpression" style="width:250px" class="validate[required]" /> 
			<br style="clear: both"/>
			
			
			<form:label path="variableSetters[${i.index}].replaceWithCharacter">Replace String</form:label>
			<form:input path="variableSetters[${i.index}].replaceWithCharacter" style="width:100px" class="validate[required]" /> 
			<br style="clear: both"/>
			
			<hr style="clear: both"/>
		
		   <br/>
		   <br/>
			
		</c:forEach>
		
		
		
		
		<br>
		<br>
		<input type="button" value="Save" style="width: 100" id="Save"/><br>
		<input type="button" value="Cancel" style="width: 100" id="Cancel"/>
		<br>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>












































