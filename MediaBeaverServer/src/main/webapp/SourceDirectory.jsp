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
				$("form:first").attr("action", "/source/move");
				$("form:first").submit();
				return false;
			});
			$("#deleteFiles").click(function() 
			{	
				$("form:first").attr("action", "/source/delete");
				$("form:first").submit();
				return false;
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
	
	
	
	
	<form:form method="POST" commandName="directory" class="formLayoutX">
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
		
		
		
		
	</form:form>
		
<%@include file="includes/footer.jsp"%>









































