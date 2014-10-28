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
			
			$("#openAllButton").click(function() 
			{			
				$("#openAll").val("true");
				
				$("form:first").attr("action", "/source");
				$("form:first").submit();
				return false;
			});
			
			$("#closeAllButton").click(function() 
			{			
				$("#closeAll").val("true");
				
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
		}); 

	</script>
	
	<style>
		.highlightableLi{
			cursor: hand;			
		}
	</style>
	
	
	
	
	<form:form method="POST" commandName="directory" class="formLayout">
		<h2>Source Directory</h2>
		
		<form:hidden path="openAll"/>
		<form:hidden path="closeAll"/>
		
	
		<ul style="list-style: none; padding-left: 0px;">
			<li>
				<form:hidden path="path"/>
				<form:hidden path="name"/>
				<form:hidden path="file"/>
				<form:hidden path="open"/>
				
				<img src="/resources/images/folder_24.png" style="padding-left: 10px;"><c:out value="${directory.path}" /> 
				[<a href="#" id="openAllButton">Open All</a>][<a href="#" id="closeAllButton">Close All</a>]
			</li>
			<li>
				<myTags:Folder folder="${directory}" parentObject="files"/>
			</ul>
		</ul>
	
		<br>
		<br>
		<a class="mainButton" href="#" id="moveFiles">Move Files</a>
		
		
	</form:form>
		
<%@include file="includes/footer.jsp"%>









































