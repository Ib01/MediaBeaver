<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<%@include file="includes/header.jsp"%>


	<script type="text/javascript" >
	
		$(function ()
		{	
			/* $(".selectableRow").mouseover(function() 
			{
				$(this).css("background-color", "#F1F1F1");
			});
			$(".selectableRow").mouseout(function() 
			{
				$(this).css("background-color", "");
			});
			$(".selectableRow").click(function() 
			{
				$(this).parent("tr").find(".selectedColumn").find(".selectedCheckbox").prop(
						'checked', 
						!$(this).parent("tr").find(".selectedColumn").find(".selectedCheckbox").is(':checked')
				);
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
				$("#currentPath").val($(this).parent("td").find(".filePath").val());
				$("form:first").attr("action", "/source");
				$("form:first").submit();
				return false;

			}); */
			
		}); 

	</script>
	
	<style>
		.selectableRow{
			cursor: hand;			
		}
	</style>
	
	
	<myTags:Folder folder="${directory.rootDirectory}"/>
	
	<form:form method="POST" commandName="directory" class="formLayout">
		<%-- <form:hidden path="currentPath"/>
		
		<h2>Source Directory</h2>
		
		<table style="width: 980px">
			<tr>
				<!-- <td style="vertical-align: bottom; width :30px;">
					<input type="checkbox" id="selectAll">
				</td> -->
				<td style="vertical-align: bottom; width :30px;">	
					<c:if test="${!file.file}"><img src="/resources/images/folder_24.png"></c:if>
				</td>
				<td style="vertical-align: bottom" class="selectableRow" >	
					<c:out value="${directory.currentPath}" />
				</td>
			</tr>
		</table>
		
		
		<table style="width: 950px; margin-left: 32px;">	
			<c:forEach items="${directory.files}" var="file" varStatus="i">
				<tr>
					<td style="vertical-align: bottom; text-align: center; width :10px; font-size: 24px">
						<c:if test="${file.file}"><span style="font-size: 28px">-</span></c:if>
						<c:if test="${!file.file}"><span style="font-size: 20px">+</span></c:if>
					</td>
					<td style="vertical-align: bottom; width :18px;" class="selectedColumn">
						<form:checkbox path="files[${i.index}].selected" class="selectedCheckbox"/>
					</td>
					
					<td style="vertical-align: bottom; width :30px;">	
						<c:if test="${file.file}"><img src="/resources/images/document_24.png"></c:if>
						<c:if test="${!file.file}"><img src="/resources/images/folder_24.png"></c:if>
					</td>
					
					<td style="vertical-align: bottom" class="selectableRow">	
						<c:if test="${file.file}"><c:out value="${file.name}" /></c:if>
						<c:if test="${!file.file}"><span class="folderName"><c:out value="${file.name}" /></span></c:if>
					
						<form:hidden path="files[${i.index}].path" class="filePath"/>				
						<form:hidden path="files[${i.index}].file" class="isFile"/>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		 --%>
		
	</form:form>


		
<%@include file="includes/footer.jsp"%>









































