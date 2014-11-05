<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

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
			
			$("#Enter").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/configuration/initialise");
					$("form:first").submit();
				}
			});
		}); 

	</script>
	
	<h2>System Activity</h2>
  
	<form:form method="POST" commandName="events" class="formLayout">
		
		<form:hidden path="selectedPath"/>
	

		<c:forEach items="${activity.activities}" var="event" varStatus="i">
			<div class="roundedPanel">
			
				<div >Time: <fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm a"  value="${event.eventTime}" /></div>
				<div >Event: <c:out value="${event.eventType}" /></div>
				<div >Result: <c:out value="${event.result}" /></div>
				<div>Source: <c:out value="${event.sourcePath}" /></div>
				<div>Destination: <c:out value="${event.destinationPath}" /></div>
				<div>Description: <c:out value="${event.errorDescription}" /></div>
		
				<c:if test="${event.eventType == 'Delete'}">
					<form:hidden path="sourcePath" class="sourcePath"/>
					<a href="#" class="moveButton">Move file to a new location</a>
				</c:if>
				
				<input type="hidden" class="filePath">
			</div>
			
			
			
		</c:forEach>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>









































