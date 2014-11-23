<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<%@include file="includes/header.jsp"%>


	<script type="text/javascript" >
	
		var dontCheck = false;
	
		$(function ()
		{	

			$(".highlightableLi").mouseover(function() 
			{
				$(this).css("background-color", "#F1F1F1");
			});
			$(".highlightableLi").mouseout(function() 
			{
				$(this).css("background-color", "");
			});
			$(".highlightableLi").click(function() 
			{
				if(!dontCheck)
				{
					$(this).find("input:checkbox").prop(
							'checked', 
							!$(this).find("input:checkbox").is(':checked')
					);
				}
				dontCheck = false;
			});
			
			$(".highlightableLi input:checkbox").click(function() 
			{
				dontCheck = true;
			});
			
			
			$(".folderName").mouseover(function() 
			{
				$(this).css("text-decoration", "underline");
			});
			$(".folderName").mouseout(function() 
			{
				$(this).css("text-decoration", "");
			});
			$(".folderName").click(function() 
			{				
				if($(this).parent("li").find(".openHiddenInput").val() == "true")
					$(this).parent("li").find(".openHiddenInput").val("false");
				else
					$(this).parent("li").find(".openHiddenInput").val("true");
				
				$("form:first").attr("action", "/source");
				$("form:first").submit();
				
				return false;
			});
			
			$("#moveFiles").click(function() 
			{	
				if($('.selectedCheckbox:checked').length > 0)
				{
					$("form:first").attr("action", "/source/move");
					$("form:first").submit();
					return false;
				}
			});
			$("#deleteFiles").click(function() 
			{	
				if($('.selectedCheckbox:checked').length > 0)
				{
					$("form:first").attr("action", "/source/delete");
					$("form:first").submit();
					return false;
				}
			});
			$("#moveManually").click(function() 
			{	
				if($('.selectedCheckbox:checked').length > 0)
				{
					$("form:first").attr("action", "/source/serviceMove");
					$("form:first").submit();
					return false;
				}
			});
			
			$("#openAll1").click(function() 
			{	
				submitOpenOrSelectAll("open all");
			});

			$("#selectAll1").click(function() 
			{	
				submitOpenOrSelectAll("select all");
			});
			
			
			function submitOpenOrSelectAll(action)
			{
				$("#action").val(action);
				
				$("form:first").attr("action", "/source");
				$("form:first").submit();
			}
			
		}); 

	</script>
	
	<style>
		.highlightableLi{
			cursor: hand;			
		}
	</style>
	
	
	<form:form method="POST" commandName="directory">
		<h2>Source Directory</h2>
		
		<div style="border: solid 1px #F1F1F1">
			<div style="background-color: #F1F1F1; padding: 3px;">
				<form:checkbox path="openAll" label="Expand All" /> |
				<form:checkbox path="selectAll" label="Select All"/>
				<form:hidden path="action"/>
				
				<div style="float: right">
					<a href="#" id="deleteFiles">Delete</a> | 
					<a href="#" id="moveManually">Move Manually</a> |
					<a href="#" id="moveFiles">Move Automatically</a> 
					
				</div> 
			</div>
			
			<ul style="list-style: none; padding-left: 0px;">
				<li>
					<form:hidden path="path"/>
					<form:hidden path="name"/>
					<form:hidden path="file"/>
					<form:hidden path="open"/>
					<img src="/resources/images/folder_24.png" style="padding-left: 10px;"><c:out value="${directory.path}" /> 
				</li>
				<li>
					<myTags:Folder folder="${directory}" parentObject="files"/>
				</ul>
			</ul>
		</div>
		
		<br>
		<br>
		
		<c:if test="${directory.action == 'deleted' || directory.action == 'moved'}">
		
			<div style="border: 1px solid #F1F1F1">
				<div style="background-color: #717372;  margin: 3px; color: white; padding: 5px;">
				<%-- 
					<c:if test="${directory.successCount > 0 && directory.failureCount > 0}">
						An error occurred while attempting to process your files. 
					</c:if> --%>
				
					<%-- <c:if test="${directory.successCount > 0}">
						<c:out value="${directory.successCount}"></c:out> files successfully <c:out value="${directory.action}"></c:out>  
					
						<ul>
							<c:forEach items="${directory.successes}" var="event" varStatus="i">
								<li><c:out value="${event.sourcePath}" /></li>
							</c:forEach>
						</ul>
						
					</c:if> --%>
					
					<c:out value="${directory.successCount}"></c:out> files were successfully <c:out value="${directory.action}"></c:out>
					
					<c:if test="${directory.failureCount > 0}">
						<c:out value="${directory.failureCount}"></c:out> files could not be <c:out value="${directory.action}"></c:out>
						
						<ul>
							<c:forEach items="${directory.failures}" var="event" varStatus="i">
								<li><c:out value="${event.sourcePath}" /></li>
							</c:forEach>
						</ul>
						
						<p style="margin-left: 22px">Please see <a href="/activity">Activities</a> for more detailed detail</p>
					</c:if>
					
				</div>
			</div>
			
		</c:if>
		
		<br>
		
	</form:form>
		
<%@include file="includes/footer.jsp"%>









































