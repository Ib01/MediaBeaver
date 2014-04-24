<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
	
	
		$(function ()
		{	
			$("#addExpression").click(function() 
			{
				
				getModel();
				//sendAjax("/config/addRegEx/");
			});
			
		});
		
		function getModel()
		{
			var i = 0;
			while($("#configVariables"+ i +"\\.name").length)
			{
				alert(i);
				++i;
			} 
			
			
				
			//var configVariable = {"name":$("#nameParser\\.assembledItem").val()} 
			
			
			/* var np = {"assembledItem": $("#nameParser\\.assembledItem").val(), "recursiveRegEx": $("#nameParser\\.recursiveRegEx").val()};
			var yp = {"assembledItem": $("#yearParser\\.assembledItem").val(), "recursiveRegEx": $("#yearParser\\.recursiveRegEx").val()};	
			var model = 
			{
				"id": $("#id").val(), 
				"expression": $("#expression").val(),
				"nameParser":  np, 
				"yearParser": yp,
				"testFileName": $("#testFileName").val(),
				"generatedName": $("#generatedName").val(),
				"generatedYear": $("#generatedYear").val()
			};
			 */
			return JSON.stringify(model);
		}
		
		
	</script>
	
	<h2>Media Item Configuration</h2>
	
	<form:form method="POST" action="/config/save" commandName="config"
		class="formLayout">
	
		<div class="shadowBox">
		
			<form:label path="description">Description</form:label>
			<form:input path="description" style="width: 550px" />
			<br>
	
			<form:label path="action">Action</form:label>
			<form:select path="action">
				<form:options items="${actions}" />
			</form:select>
			<br>
			
			<form:label path="sourceDirectory">Source Directory</form:label>
			<form:input path="sourceDirectory" style="width: 550px" />
			<br>
			
		</div>
	
		<br>
		
		<div class="shadowBox">
			
			<div class="shadowBoxHeader">Configuration Variables 
		   		<span style="float: right;" class="shadowBoxHelp">
			   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
			   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
		   		</span>
	   		</div>
		
			<c:forEach items="${config.configVariables}" varStatus="i">
				<form:label path="configVariables[${i.index}].name">Variable Name</form:label>
				<form:input path="configVariables[${i.index}].name" style="width:350px"/>
				<form:checkbox path="configVariables[${i.index}].required" label="Required"/>
				<br>
		    </c:forEach>
		
		</div>
		
		<div class="shadowBox">
			
			<div class="shadowBoxHeader">File Selectors 
		   		<span style="float: right;" class="shadowBoxHelp">
			   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
			   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
		   		</span>
	   		</div>
		
			<%-- <c:forEach items="${config.configVariables}" varStatus="i">
				<form:label path="configVariables[${i.index}].name">Variable Name</form:label>
				<form:input path="configVariables[${i.index}].name" style="width:350px"/>
				<form:checkbox path="configVariables[${i.index}].required" label="Required"/>
				<br>
		    </c:forEach> --%>
		    
		    
		    <input type="button" value="Add Expression" id="addExpression" />
		
		</div>
		
		
	
	
		<br>
		<br>
		<br>
		<input type="submit" value="Submit" />
	</form:form>
	
	
	<%-- ${config.name} --%>

<%@include file="includes/footer.jsp"%>












































