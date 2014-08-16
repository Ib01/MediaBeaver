<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
		$(function ()
		{	
			$(".visibilityPanel").hide();
			
			$(".toggleHide" ).click(function() 
			{
				$(this).parent().parent().find(".visibilityPanel").toggle( "blind", 500 );
				
			});
			
			
			/* $( "#sortable" ).sortable({
				  stop: sortStop,
				  start: sortStart
			});
			
		    $( "#sortable" ).disableSelection(); */
		});
		
		
		function sortStop( event, ui ) 
		{
			//alert("afd");
		}
		
		function sortStart( event, ui ) 
		{
			//$("#visibilityPanel").toggle( "blind", 500 );
		}
	</script>
	
	<style>
		.detailedListItem{
			border-radius: 8px;
			background-color: #F1F1F1;
			padding: 10px;
			padding-bottom: 15px;
			margin-bottom: 15px;
		}
		label{
			width: 200px;
		}
		
		.detailedListItem_level1Box{
			background-color: white; 
			padding:7px;
			border-radius: 8px;
			margin-bottom: 6px;
		}
		
		.detailedListItem H3{
			color:#1F1F1F;
			margin-top: 4px;
			font-size: 14px;
			font-weight: normal;
		}
	</style>
	
	
	<h2>Media Configuration Items</h2>
	
	<form:form method="POST" action="/config/save" commandName="configList" class="formLayout">
		
		<div id="sortable">
			<c:forEach items="${configList}" var="config" varStatus="i">
				
				<div class="detailedListItem">
				
					<div style="float:right; background-color: #ffffff; padding: 4px; margin: 2px">
						<a href="/configList/sortUp/${i.index}" class="editExpression">&nbsp;&nbsp;&#8657;&nbsp;&nbsp;</a> |
						<a href="/configList/sortDown/${i.index}" class="editExpression">&nbsp;&nbsp;&#8659;&nbsp;&nbsp;</a>  |
						<a href="/configWizard/${config.id}" class="editExpression">edit</a> | 
						<a href="/configList/delete/${config.id}" class="deleteExpression">delete</a>
					</div>
				
					<h3><c:out value="${config.description}" /> <a href="#" class="toggleHide">[ show details ]</a></h3>
					
					<div class="visibilityPanel">
					
						<div class="detailedListItem_level1Box">
							<%-- <label>Description: </label><c:out value="${config.description}" />
							<br> --%>
							<label>Action: </label><c:out value="${config.action}" />
							<br>
							<label>Source Directory: </label><c:out value="${config.sourceDirectory}" />
							<br>
							<label>Destination Root: </label><c:out value="${config.destinationRoot}" />
							<br>
							<label>Destination Path: </label><c:out value="${config.relativeDestinationPath}" />
							<br>
						</div>
						
						<h3>Regex Selectors</h3>
				 			
						<c:forEach items="${config.regExSelectors}" var="selector" varStatus="ii">
						
							<div class="detailedListItem_level1Box">
								<label>Description: </label><c:out value="${selector.description}" />
								<br>
								<label>expression: </label><c:out value="${selector.expression}" />
								<br>
								
								<p style="font-style: italic;">Regex Setters:</p>
								<c:forEach items="${selector.pathTokenSetters}" var="setter" varStatus="iii">
									<label>Name: </label><c:out value="${setter.pathTokenName}" />
									<br>	
									<label>Group Assembly: </label><c:out value="${setter.groupAssembly}" />
									<br>	
									<label>Replace Expression: </label><c:out value="${setter.replaceExpression}" />
									<br>
									<label>Replace Character: </label><c:out value="${setter.replaceWithCharacter}" />
									<br>
								</c:forEach>
							</div>
							
						</c:forEach>
					
					</div>
				</div>	
				
							
			</c:forEach>	
		</div>
			
		<br>
		<br>
		
			
		
		
		<br>
		<br>
		<a class="mainButton" href="/configWizard">New Configuration Item</a>
		<br>
		
	</form:form> 
	

<%@include file="includes/footer.jsp"%>












































