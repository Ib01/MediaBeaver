<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<%@include file="includes/header.jsp"%>
	
	<myTags:ErrorDisplay modelObject="movedata"/>
	
	<script type="text/javascript" >
	
		$(function ()
		{	
			$("#moveFiles").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/manualmove/move");
					$("form:first").submit();
				}
			});
	
		}); 
	
	</script>
	
	<h2>Manually Move File</h2>
  
	<form:form method="POST" commandName="movedata" class="formLayout">

		<div class="level1Panel">
			<form:label path="selectedRoot">Source File</form:label>
			<span>${movedata.sourceFile}</span>
			<form:hidden path="sourceFile"/>
			<br>
		
			<form:label path="selectedRoot">Destination Root</form:label>
			<form:select path="selectedRoot" >
				<form:options items="${movedata.rootPaths}" />
			</form:select>
			<br>
			
			<form:label path="selectedRoot">Destination Path</form:label>
			<form:input path="pathEnd" style="width: 750px" class="validate[required]"/>
			<br>	
		</div>

		<a class="mainButton" href="#" id="moveFiles">Move File</a>
		<a class="mainButton" href="${movedata.referingPage}" id="Cancel">Cancel</a>
		<form:hidden path="referingPage"/>
		<br>
		<br>
	</form:form>
	
<%@include file="includes/footer.jsp"%>









































