<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>


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
			
			/* $("#Cancel").click(function() 
			{
				$("form:first").attr("action", "/configWizard/configCancel");
				$("form:first").submit();
			}); */
			
			
		}); 

		
	</script>
	
	<h2>System Events</h2>
  
	<form:form method="POST" commandName="events" class="formLayout">

		<c:forEach items="${events}" var="event" varStatus="i">
			<div class="roundedPanel">
				
					<div >Time: <fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm a"  value="${event.eventTime}" /></div>
					<div >Event: <c:out value="${event.eventType}" /></div>
					<div >Result: <c:out value="${event.result}" /></div>
					<div>Source: <c:out value="${event.sourcePath}" /></div>
					<div>Destination: <c:out value="${event.destinationPath}" /></div>
					<div>Description: <c:out value="${event.errorDescription}" /></div>
			</div>
		</c:forEach>
		
		<%-- <c:forEach items="${events}" var="event" varStatus="i">
		<div>Description: <c:out value="${event.errorDescription}" /></div>
		</c:forEach> --%>
		
		
		<%-- <div class="roundedPanel">
			
			<table style="width: 700px">
				<tr>
					<th>Time</th>
					<th>Event</th>
					<th>Source</th>
					<th>Destination</th>
					<th>Result</th>
					<th>Description</th> 
				</tr>
				
				
				<c:forEach items="${events}" var="event" varStatus="i">
					<tr>
					<td ><fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm a"  value="${event.eventTime}" /></td>
					<td><c:out value="${event.eventType}" /></td>
					<td style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap; max-width: 230px;"><c:out value="${event.sourcePath}" /></td>
					<td style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap; max-width: 230px"><c:out value="${event.destinationPath}" /></td>
					<td><c:out value="${event.result}" /></td>
					<td><c:out value="${event.errorDescription}" /></td> 
					</tr>		
				</c:forEach>
			
			</table>
			
			
		</div> --%>
		
	 
		<br>
		<br>
		<a class="mainButton" href="#" id="Save">Save</a>
		<a class="mainButton" href="#" id="Cancel">Cancel</a>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>









































