<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
	
	
		$(function ()
		{	
			$("#addExpression").click(function() 
			{
				submitRegExSelectorChange("/config/addRegExSelector", this);
			});
			
			$(".deleteExpression").click(function() 
			{
				//alert("d");
				submitRegExSelectorChange("/config/deleteRegExSelector", this);
			});
			
			$(".editExpression").click(function() 
			{
				submitRegExSelectorChange("/config/updateRegExSelector", this);
			});			
			
			
		});
		
		
		function submitRegExSelectorChange(url, caller)
		{
			var id = $(caller).parent().find("#selectorIndex").val();
			$("#selectedRegExSelectorIndex").val(id);
			
			$("form:first").attr("action", url);
			$("form:first").submit();
		}
		
		
		
		function getModel()
		{
			var config = 
			{
				description: $("#description").val(),
				action: $("#action").val(),
				configVariables: getConfigVariables(),
				sourceDirectory: $("#sourceDirectory").val(),
				
			};
			
			//useOpenSubtitlesThumbprintService: 
			//openSubtitlesFieldMaps
			//regExSelectors
			//extensionsSelector;
			//selectAllFiles;
			//selectAllFolders;
			//selectAllEmptyFolders;
			//destinationRoot;
			//relativeDestinationPath;
 			
			alert(JSON.stringify(config));
			
			return JSON.stringify(config);
		}
		
		function getConfigVariables()
		{
			var i = 0;
			var vars = new Array();
			
			while($("#configVariables"+ i +"\\.name").length)
			{
				vars[i] ={
						"id":$("#configVariables"+ i +"\\.id").val(),
						"name":$("#configVariables"+ i +"\\.name").val(),
						"required":$("#configVariables"+ i +"\\.required1").is(':checked') 
						};
				++i;
			} 
			
			return vars;
		}
		
		
		
		function sendAjax(url, model) 
		{
			$.ajax({ 
			    url: url, 
			    type: 'POST', 
			    dataType: 'json', 
			    data: model, 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) 
			    { 
			    	alert("done");
			    },
			    error:function(data,status,er) { 
			        alert("erro occured");
			    }
			}); 
		}
		
		
	</script>
	
	<h2>Media Item Configuration</h2>
	
	<form:form method="POST" action="/config/save" commandName="config"
		class="formLayout">
		
		<form:hidden path="selectedRegExSelectorIndex"/>
	
	
		<input type="hidden" id="tester" name="tester" value="tester value"/>
	
		<form:hidden path="id"/>
	
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
		</div>
		<br>
		
		<div class="shadowBox">
			<c:forEach items="${config.configVariables}" varStatus="i">
				<form:label path="configVariables[${i.index}].name">Variable Name</form:label>
				<form:input path="configVariables[${i.index}].name" style="width:350px"/>
				<form:checkbox path="configVariables[${i.index}].required" label="Required"/>
				<br>
		    </c:forEach>
		</div>
		
		<br>
		
		<div class="shadowBox">
			<div class="shadowBoxHeader">Regular Expression Selectors 
		   		<span style="float: right;" class="shadowBoxHelp">
			   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
			   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
		   		</span>
	   		</div>
		</div>
		
		<div class="shadowBox">
			<table style="width:100%">
				<tr>
					<td>Description</td>
					<td>Expression</td>
					<td></td>
				</tr>
			
				<c:forEach items="${config.regExSelectors}" var="selector" varStatus="i">
					<tr>
					<td><c:out value="${selector.description}" /></td>
					<td><c:out value="${selector.expression}" /></td>
					<td><a href="#" class="editExpression">edit</a>
					<a href="#" class="deleteExpression">delete</a><input type="hidden" id="selectorIndex" value="${i.index}" /></td>
					</tr>
			    </c:forEach>
		    </table>
		    
		    <input type="button" value="Add Expression" id="addExpression" />
		</div>	
		
		
		
	
	
		<br>
		<br>
		<br>
		<input type="submit" value="Submit" />
	</form:form>
	
	
	<%-- ${config.name} --%>

<%@include file="includes/footer.jsp"%>












































