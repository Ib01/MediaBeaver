<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="includes/header.jsp"%>
	
	 <script type="text/javascript" >
	
	
		$(function ()
		{	
			$("#Next").click(function() 
			{
				$("form:first").attr("action", "/configWizard/configNext");
				$("form:first").submit();
			});
			
			$("#Cancel").click(function() 
			{
				$("form:first").attr("action", "/configWizard/configCancel");
				$("form:first").submit();
			});
			
		}); 
			
	</script>

	
	<h2>Config Wizard. Step 1</h2>
	
	<form:form method="POST" commandName="config" id="configForm" class="formLayout">
		
		<form:hidden path="selectedRegExSelectorIndex"/>
		<form:hidden path="id"/>
		<form:hidden path="lastUpdate"/>
		
		
		<form:label path="description" >Description</form:label>
		<form:input path="description" style="width: 550px" class="validate[required]"/>
		<br>
		
		<form:label path="action">Action</form:label>
		<form:select path="action">
			<form:options items="${actions}" />
		</form:select>
		<br>
	
		<form:label path="sourceDirectory">Source Directory</form:label>
		<form:input path="sourceDirectory" style="width: 550px" class="validate[required]"/>
		<br>
		
		
		<form:label path="destinationRoot">Destination Root</form:label>
		<form:input path="destinationRoot" style="width: 550px" class="validate[required]"/>
		<br>
	
		<form:label path="relativeDestinationPath">Relative Path</form:label>
		<form:input path="relativeDestinationPath" style="width: 550px" class="validate[required]"/>
		<br>
			
		<br>
		<br>
		<input type="button" value="Next" style="width: 100; float: right; margin-left: 5px;" id="Next"/>
		<input type="button" value="Cancel" style="width: 100; float: right" id="Cancel"/>
		<br>
		<br>
		<br>
		
	</form:form>
	 
	
<%@include file="includes/footer.jsp"%>












































