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
			
			
			$(".editSelector").click(function() 
			{
				/* alert($(this).parent().parent().find(".selectorIndex").val()); */
				
				var index = $(this).parent().parent().find(".selectorIndex").val();
				$("#selectedRegExSelectorIndex").val(index);
				
				$("form:first").attr("action", "/configWizard/regExSelectorsUpdate");
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
			var newSortInputs = $(".sorOrder");
			
			//set new sort orders
			for (var i = 0; i < newSortInputs.length; i++) 
			{
				$(newSortInputs[i]).val(i);
			}
			
			
			/* 
			var indexArray = [];
			var indexInputs = $(".selectorIndex");
			
			//get orriginal index values of reshuffled selectors
			for (var i = 0; i < indexInputs.length; i++) {
				indexArray[i] = $(indexInputs[i]).val(); 
			}
			
			//Send indexes of reshufled selectors 
			sendAjax("/configWizard/orderSelectors", indexArray);
			
			//re-index selectors
			for (var i = 0; i < indexInputs.length; i++) 
			{
				$(indexInputs[i]).val(i);
			} */
		}
		 
		
		
		function sendAjax(url, data) 
		{
			$.ajax({ 
			    url: url, 
			    type: 'POST', 
			    dataType: 'json', 
			    data: JSON.stringify({reorderList: data}), 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) {},
			    error:function(data,status,er) {}
			}); 
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
	
		<form:hidden path="selectedRegExSelectorIndex"/>
		
		<div id="sortable">
		
			<c:forEach items="${config.regExSelectors}" var="selector" varStatus="i">
			
				<div class="detailedListItem">
					<div style="float:right; background-color: #ffffff; padding: 4px; margin: 2px">
						<a class="editSelector">edit</a>
						
						<%-- <a href="/configWizard/regExSelectorsUpdate/${i.index}" class="editExpression">edit</a> --%> | 
						<a href="/configWizard/regExSelectorsDelete/${i.index}" class="deleteExpression">delete</a>
						
					</div>
					
					<form:hidden path="regExSelectors[${i.index}].sorOrder" class="sorOrder"/>
				
					<input type="hidden" class="selectorIndex" value='<c:out value="${i.index}" />'>
					<label>Description: </label><c:out value="${selector.description}" />
					<br>
					<label>expression: </label><c:out value="${selector.expression}" />
					<br>
				</div>
					
			</c:forEach>
		
		</div>
		<br>
		
		
		<a class="button" href="#" id="Add">Add</a>
		<br>
		<br>
		<a class="mainButton" href="#" id="Next">Next</a>
		<a class="mainButton" href="#" id="Previous">Previous</a>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>












































