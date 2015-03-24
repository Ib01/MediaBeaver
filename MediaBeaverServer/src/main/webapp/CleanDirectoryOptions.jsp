<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<%@include file="includes/header.jsp"%>

	<myTags:ErrorDisplay modelObject="configuration"/>
	
	<script type="text/javascript" >
	
		$(function ()
		{	
			/* $("#Save").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/configuration/save");
					$("form:first").submit();
				}
			}); */
			
			
			
		}); 
		
	</script>
	
	
	<h2>Clean Directory Options</h2>
  
	<form:form method="POST" commandName="configuration" id="configForm" class="formLayout">
		<%-- <form:hidden path="id"/>
		<div class="level1Panel">
			<form:label path="sourceDirectory">Source Directory</form:label>
			<form:input path="sourceDirectory" style="width: 750px" class="autoComplete validate[required]"/>
			<br>
		</div>
		
		<div class="level1Panel">
			<form:label path="tvRootDirectory">Tv Root</form:label>
			<form:input path="tvRootDirectory" style="width: 750px" class="validate[required]"/>
			<br>
			<form:label path="episodeFormatPath">Episode Path Format</form:label>
			<form:textarea path="episodeFormatPath" style="width: 750px; height: 80px" class="autoComplete validate[required]"/>
			<br>
		</div>
		
		<div class="level1Panel">
			<form:label path="movieRootDirectory">Movie Root</form:label>
			<form:input path="movieRootDirectory" style="width: 750px" class="validate[required]"/>
			<br>
			<form:label path="movieFormatPath">Movie Path Format</form:label>
			<form:textarea path="movieFormatPath" style="width: 750px; height: 80px" class="autoComplete validate[required]"/>
			<br>
		</div>
		
		<div class="level1Panel">
		 	<form:label path="videoExtensionFilter">Video Extensions</form:label>
		 	<form:textarea path="videoExtensionFilter" style="width: 750px; height: 100px" class="validate[required]"/>
		 	<br>
	 		<form:label path="copyAsDefault">Move action</form:label>
	 		<form:checkbox path="copyAsDefault" /> (Copy when moving)
		 	<br>
	 	</div>
		
		<br>
		<br>
		<a class="mainButton" href="#" id="Save">Save</a>
		<br>
		<br> --%>
		
	</form:form>


		
<%@include file="includes/footer.jsp"%>









































