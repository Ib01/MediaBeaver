<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
	
	
		$(function ()
		{	
			/* 
			$(".toggleHide" ).click(function() 
			{
				$(this).parent().parent().find(".visibilityPanel").toggle( "blind", 500 );
				
			}); */
		
		}); 

	

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
			color:#1F1F1F;
			margin-top: 2px;
			font-size: 14px;
			font-weight: normal;
		}
	</style>

	
	<h2>Config Wizard Step 2: Modify Open Subtitles Selectors</h2>
	
	<form:form method="POST" commandName="config" id="configForm" class="formLayout">
	
		<%-- <form:hidden path="selectedRegExSelectorIndex"/> --%>
		
		<div id="sortable">
		
			<c:forEach items="${config.openSubtitlesSelectors}" var="selector" varStatus="i">
			
				<div class="detailedListItem">
				
			<%-- 		<div style="float:right; background-color: #ffffff; padding: 4px; margin: 2px">
						<a href="#" class="sortUp">&nbsp;&nbsp;&#8657;&nbsp;&nbsp;</a> |
						<a href="#" class="sortDown">&nbsp;&nbsp;&#8659;&nbsp;&nbsp;</a>  |
						<a class="editSelector" href="#">edit</a>  | 
						<a href="/configWizard/regExSelectorsDelete/${i.index}" class="deleteExpression">delete</a>
					</div> --%>
					
					<h3><c:out value="${selector.description}" /> <a href="#" class="toggleHide">[ show details ]</a></h3>
					
					<form:hidden path="regExSelectors[${i.index}].sorOrder" class="sorOrder"/>
					<input type="hidden" class="selectorIndex" value='<c:out value="${i.index}" />'>
					
					<div class="visibilityPanel">
						<label>expression: </label><c:out value="${selector.expression}" />
						<br>
					</div>
					
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












































