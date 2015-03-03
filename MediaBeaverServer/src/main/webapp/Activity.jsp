<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<%@include file="includes/header.jsp"%>

	<myTags:ErrorDisplay modelObject="activity"/>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			$("#match").click(function() 
			{
				$("#selectedPath").val($(this).siblings(".sourcePath").val());
				
				$("form:first").attr("action", "/activity/match");
				$("form:first").submit(); 
				
			});
			
			
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
			<table class="level1Panel" style="width: 100%">
			
				<tr>
					<td  style="width: 10px;">
						<input type="hidden" class="filePath">
						
						<c:if test="${event.eventType == 'Delete'}">
							<img src="/resources/images/film_delete.png">
						</c:if>
						<c:if test="${event.eventType == 'Move'}">
							<img src="/resources/images/document_move.png">
						</c:if>
						<c:if test="${event.eventType == 'Copy'}">
							<img src="/resources/images/page_copy.png">
						</c:if>
						<c:if test="${event.eventType == 'Rename'}">
							<img src="/resources/images/textfield_rename.png">
						</c:if>
					</td>
					<td style="font-style: italic; white-space: nowrap; width: 150px;">
						<fmt:formatDate type="both" pattern="dd/MM/yyyy hh:mm a"  value="${event.eventTime}" />	
					</td>
					<td style="font-weight: bold;">
						<c:out value="${event.errorDescription}" />
					</td>
					<td style="width: 10px">
						<c:if test="${event.result == 'Succeeded'}">
							<img src="/resources/images/tick.png">
						</c:if>
						<c:if test="${event.result == 'Failed'}">
							<img src="/resources/images/cross.png">
						</c:if>
					</td>
				</tr>
				
				<c:if test="${!empty event.sourcePath}">
					<tr>
						<td>&nbsp;</td>
						<td>Source Path:</td>
						<td><c:out value="${event.sourcePath}" /></td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
				
				<c:if test="${!empty event.destinationPath}">
					<tr>
						<td>&nbsp;</td>
						<td>Destination Path:</td>
						<td><c:out value="${event.destinationPath}" /></td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
				
				<%-- <c:if test="${event.eventType == 'Move'}"> --%>
				<c:if test="${event.eventType == 'Delete'}">
					<tr>
						<td>&nbsp;</td>
						<td>Available actions:</td>
						<td><a href="#" id="match">Match and move</a><input type="hidden" class="sourcePath" value="${event.sourcePath}"></td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
				
			</table>
		</c:forEach>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>









































