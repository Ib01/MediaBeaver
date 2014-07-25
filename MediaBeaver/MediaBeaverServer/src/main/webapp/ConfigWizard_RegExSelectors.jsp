<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
	
	
		$(function ()
		{	
			$("#Next").click(function() 
			{
				$("form:first").attr("action", "/configWizard/regExSelectorsNext");
				$("form:first").submit();
			});
			
			$("#Previous").click(function() 
			{
				$("form:first").attr("action", "/configWizard/regExSelectorsPrevious");
				$("form:first").submit();
			});
			
			$("#Add").click(function() 
			{
				$("form:first").attr("action", "/configWizard/regExSelectorsAdd");
				$("form:first").submit();
			});
			
		}); 
			
	</script>

	
	<h2>Config Wizard. Step 2</h2>
	
	<form:form method="POST" commandName="config" id="configForm" class="formLayout">
		
		<table style="width:100%">
			<tr style="background-color: #2A2F33; color: #FFFFFF">
				<td align="left" width="350px" valign="top" style="padding :3px; text-align: center;" >Description</td>
				<td align="left" width="350px" valign="top" style="margin-left: 100px;padding :3px; text-align: center;">Expression</td>
				<td align="right" width="80px" valign="top" style="padding :3px; text-align: center;">Action</td>
			</tr>
		
			<c:forEach items="${config.regExSelectors}" var="selector" varStatus="i">
			
				<tr>
					<td align="left" valign="top"><c:out value="${selector.description}" /></td>
					<td align="left" valign="top" style="margin-left: 100px;"><c:out value="${selector.expression}" /></td>
					<td align="right" valign="top">
						<a href="#" class="editExpression">edit</a> | 
						<a href="#" class="deleteExpression">delete</a><input type="hidden" id="selectorIndex" value="${i.index}" />
					</td>
				</tr>
		    </c:forEach>
		    
	    </table>
	
		
		
		<input type="button" value="Add" style="width: 100" id="Add"/>
		<br>
		<br>
		<input type="button" value="Next" style="width: 100; float: right" id="Next"/>
		<input type="button" value="Previous" style="width: 100; float: right" id="Previous"/>
		<br>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>












































