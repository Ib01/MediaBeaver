<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<%@include file="includes/header.jsp"%>

	<myTags:ErrorDisplay modelObject="activity"/>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			$(".manualMoveButton").click(function() 
			{
				$("#selectedPath").val($(this).siblings(".sourcePath").val());
				
				$("form:first").attr("action", "/activity/manualMove");
				$("form:first").submit(); 
				
			});
			
			$("#filter").click(function() 
			{
				$("form:first").attr("action", "/activity/filter");
				$("form:first").submit(); 
				
			});
			
			$( "#earlistDate" ).datepicker({ dateFormat: 'dd MM yy' });
			
		}); 
		

	</script>
	
	<h2>System Activity</h2>
	
	<form:form method="POST" commandName="activity" class="formLayout">
		<form:hidden path="selectedPath"/>
	
		<form:label path="earlistDate">Earliest Date</form:label>
		<form:input path="earlistDate" readonly="true"/>
		<a class="button" href="#" id="filter">Filter</a>
		<br>
		
		

		<c:forEach items="${activity.activities}" var="event" varStatus="i">
			<div class="roundedPanel">
			
				<c:if test="${event.eventType == 'Delete'}">
					<img src="/resources/images/film_delete.png" style="float: left; margin-right: 10px">
				</c:if>
				<fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm a"  value="${event.eventTime}" />
				
				<c:if test="${event.result == 'Succeeded'}">
					<img src="/resources/images/tick.png" style="float: right;">
				</c:if>
				
				
				<%-- 
				<div >Time: <fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm a"  value="${event.eventTime}" /></div>
				<div >Event: <c:out value="${event.eventType}" /></div>
				<div >Result: <c:out value="${event.result}" /></div>
				
				<div>Source: <c:out value="${event.sourcePath}" /></div>
				<div>Destination: <c:out value="${event.destinationPath}" /></div>
				<div>Description: <c:out value="${event.errorDescription}" /></div> --%>
		
				<%-- <c:if test="${event.eventType == 'Delete'}">
					<input type="hidden" class="sourcePath" value="${event.sourcePath}"/>
					<a href="#" class="manualMoveButton">Move file to a new location</a>
				</c:if> --%>
				
				<input type="hidden" class="filePath">
			</div>
			
		</c:forEach>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>









































