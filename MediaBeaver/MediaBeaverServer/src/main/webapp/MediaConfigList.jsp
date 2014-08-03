<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
		$(function ()
		{	
			/* $("#addExpression").click(function() 
			{
				submitRegExSelectorChange("/config/addRegExSelector", this);
			});
			*/
		});
	</script>
	
	<style>
		.detailedListItem{
			border-radius: 8px;
			background-color: #F1F1F1;
			padding: 10px;
			padding-bottom: 15px;
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
	
	
	<h2>Media Configuration Items</h2>
	
	<form:form method="POST" action="/config/save" commandName="configList" class="formLayout">
		
		<c:forEach items="${configList}" var="config" varStatus="i">
			
			<div class="detailedListItem">
			
				<div style="float:right; background-color: #ffffff; padding: 4px; margin: 2px">
					<a href="/configWizard/${config.id}" class="editExpression">edit</a> | 
					<a href="/configList/delete/${config.id}" class="deleteExpression">delete</a>
				</div>
			
				<h3>Configuration detail</h3>
				
				<div class="detailedListItemLevel1Box">
					<label>Description: </label><c:out value="${config.description}" />
					<br>
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
		 		
				<div class="detailedListItemLevel1Box">
						
					<c:forEach items="${config.regExSelectors}" var="selector" varStatus="ii">		
						<label>Description: </label><c:out value="${selector.description}" />
						<br>
						<label>expression: </label><c:out value="${selector.expression}" />
						<br>
						
						<h3>Regex Setters</h3>
						
						<c:forEach items="${selector.variableSetters}" var="setter" varStatus="iii">
							<br>
							<label>Name: </label><c:out value="${setter.variableName}" />
							<br>	
							<label>Group Assembly: </label><c:out value="${setter.groupAssembly}" />
							<br>	
							<label>Replace Expression: </label><c:out value="${setter.replaceExpression}" />
							<br>
							<label>Replace Character: </label><c:out value="${setter.replaceWithCharacter}" />
							<br>
						</c:forEach>
						
					</c:forEach>	
				</div>
				
			</div>
			<br>
			<br>
			
			
		</c:forEach>
		
		<br>
		<br>
		<a class="mainButton" href="/configWizard">New Configuration Item</a>
		<br>
		
	</form:form> 
	

<%@include file="includes/footer.jsp"%>












































