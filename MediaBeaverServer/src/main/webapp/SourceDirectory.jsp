<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>


	<script type="text/javascript" >
	
		$(function ()
		{	
			
			$(".selectableRow").click(function() 
			{
				/* if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/configuration/save");
					$("form:first").submit();
				} */
			});
			
			/* $("#Save").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/configuration/save");
					$("form:first").submit();
				}
			}); */
			
		}); 

	</script>
	
	<style>
		.selectableRow{
			cursor: hand;
		}
	</style>
	
	
	<form:form method="POST" commandName="directory" class="formLayout">
		<h2>Source Directory</h2>
		
		<table style="width: 100%">
			<tr>
				<td style="vertical-align: bottom; width :30px;">
					<input type="checkbox" id="selectAll">
				</td>
				<td style="vertical-align: bottom; width :30px;">	
					<c:if test="${!file.file}"><img src="/resources/images/folder_24.png"></c:if>
				</td>
				<td style="vertical-align: bottom" class="selectableRow" >	
					<c:out value="${directory.currentPath}" />
				</td>
			</tr>
		</table>
		
		
		<table style="width: 100%; margin-left: 32px;">	
			<c:forEach items="${directory.files}" var="file" varStatus="i">
				<tr>
					<td style="vertical-align: bottom; width :30px;">
						<form:checkbox path="files[${i.index}].selected"/>
					</td>
					<td style="vertical-align: bottom; width :30px;">	
						<c:if test="${file.file}"><img src="/resources/images/document_24.png"></c:if>
						<c:if test="${!file.file}"><img src="/resources/images/folder_24.png"></c:if>
					</td>
					<td style="vertical-align: bottom" class="selectableRow">	
						<c:out value="${file.name}" />
					</td>
				</tr>
			</c:forEach>
		</table>
		
	</form:form>


		
<%@include file="includes/footer.jsp"%>









































