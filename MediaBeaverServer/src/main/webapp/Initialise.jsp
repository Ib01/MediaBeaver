<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>


	<script type="text/javascript" >
	
		$(function ()
		{	
			$("#Save").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/configuration/save");
					$("form:first").submit();
				}
			});
			
			
			setAutoComplete();
		}); 
		
	</script>
	
	<h2>Initialise</h2>
  
	<form:form method="POST" commandName="configuration" id="configForm" class="formLayout">
		<%-- <form:hidden path="id"/>

		<div class="roundedPanel">
			<form:label path="sourceDirectory">Source Directory</form:label>
			<form:input path="sourceDirectory" style="width: 550px" class="autoComplete validate[required]"/>
			<br>
		</div>
		
		<div class="roundedPanel">
			<form:label path="tvRootDirectory">Tv Root</form:label>
			<form:input path="tvRootDirectory" style="width: 550px" class="validate[required]"/>
			<br>
			<form:label path="episodePath">Episode Path Format</form:label>
			<form:textarea path="episodePath" style="width: 550px; height: 80px" class="autoComplete validate[required]"/>
			<br>
		</div>
		
		<div class="roundedPanel">
			
			<form:label path="movieRootDirectory">Movie Root</form:label>
			<form:input path="movieRootDirectory" style="width: 550px" class="validate[required]"/>
			<br>
			<form:label path="moviePath">Movie Path Format</form:label>
			<form:textarea path="moviePath" style="width: 550px; height: 80px" class="autoComplete validate[required]"/>
			<br>
		</div>
	 
		 <div class="roundedPanel">
		 	<form:label path="videoExtensionFilter">Video Extensions</form:label>
		 	<form:textarea path="videoExtensionFilter" style="width: 550px; height: 100px" class="validate[required]"/>
		 	<br>
		 </div>
	 
		<br>
		<br>
		<a class="mainButton" href="#" id="Save">Save</a>
		<a class="mainButton" href="#" id="Cancel">Cancel</a>
		<br>
		<br> --%>
		
	</form:form>


		
<%@include file="includes/footer.jsp"%>









































