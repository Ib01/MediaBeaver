<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
	
	
		$(function ()
		{	
			$( "#sortable" ).sortable({
				  stop: sortStop
			});
			
		    $( "#sortable" ).disableSelection();
			
			$("#Next").click(function() 
			{
				$.ajax({
					  url: "/configWizard/validateRegExSelectors",
					  success: validateRegExSelectors
					});
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
		
		
		function validateRegExSelectors(errors)
		{
			//ints returned are index numbers of inavalid selectors.  we will display an error next to its row
			for (var i = 0; i < errors.length; i++) 
			{
				$("#selector" + errors[i]).validationEngine('showPrompt', 'Variable setter data is missing for this selector', 'error','topLeft', true);
			}
			
			if(errors.length == 0)
			{
				$("form:first").attr("action", "/configWizard/regExSelectorsNext");
				$("form:first").submit();
			}
			
		}
		
		function sortStop( event, ui ) 
		{
			var varList = [];
			var items = $(".selectorIndex");
			
			for (var i = 0; i < items.length; i++) 
			{
				alert($(items[i]).val());
				varList[i] = $(items[i]).val(); 
			}
			
			
			alert(varList);
		}
		 
	</script>
	
	<style>
		.detailedListItem{
			border-radius: 8px;
			background-color: #F1F1F1;
			padding: 10px;
			padding-bottom: 15px;
			margin-top: 5px;
			margin-bottom: 5px;
		}
		.formLayout .detailedListItem label{
			width: 200px;
		}
		
		.detailedListItemLevel1Box{
			background-color: white; 
			padding:7px;
			border-radius: 8px;
			margin-bottom: 6px;
		}
		
		.detailedListItem H3{
			color:#FF8A00;
			margin-top: 2px;
			font-size: 14px;
			font-weight: normal;
		}
	</style>

	
	<h2>Config Wizard. Step 2</h2>
	
	<form:form method="POST" commandName="config" id="configForm" class="formLayout">
		
		<div id="sortable">
		
			<c:forEach items="${config.regExSelectors}" var="selector" varStatus="i">
			
				<div class="detailedListItem">
					<div style="float:right; background-color: #ffffff; padding: 4px; margin: 2px">
						<a href="/configWizard/regExSelectorsUpdate/${i.index}" class="editExpression">edit</a> | 
								<a href="/configWizard/regExSelectorsDelete/${i.index}" class="deleteExpression">delete</a>
					</div>
				
					<input type="hidden" class="selectorIndex" value='<c:out value="${i.index}" />'>
					
					<label>Description: </label><c:out value="${selector.description}" />
					<br>
					<label>expression: </label><c:out value="${selector.expression}" />
					<br>
				</div>
					
			</c:forEach>
		
		</div>
		<br>
		
		
		<%-- <table style="width:100%" >
			<tr style="background-color: #555555; color: #FFFFFF">
				<td align="left" width="350px" valign="top" style="padding :3px; text-align: center;" >Description</td>
				<td align="left" width="350px" valign="top" style="margin-left: 100px;padding :3px; text-align: center;">Expression</td>
				<td align="right" width="80px" valign="top" style="padding :3px; text-align: center;">Action</td>
			</tr>
		
			<c:forEach items="${config.regExSelectors}" var="selector" varStatus="i">
				
				<tr  id="selector${i.index}" class="sortable">
					<td align="left" valign="top"><c:out value="${selector.description}"/>
					</td>
					<td align="left" valign="top" style="margin-left: 100px;"><c:out value="${selector.expression}" /></td>
					<td align="right" valign="top">
						<a href="/configWizard/regExSelectorsUpdate/${i.index}" class="editExpression">edit</a> | 
						<a href="/configWizard/regExSelectorsDelete/${i.index}" class="deleteExpression">delete</a>
					</td>
				</tr>
		    </c:forEach>
		    
	    </table> --%>
	
		
		<a class="button" href="#" id="Add">Add</a>
		<br>
		<br>
		<a class="mainButton" href="#" id="Next">Next</a>
		<a class="mainButton" href="#" id="Previous">Previous</a>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>












































