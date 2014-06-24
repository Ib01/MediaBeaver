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
				alert("unimplemented");
				//submitRegExSelectorChange("/config/deleteRegExSelector", this);
			}); 
			
			$(".editExpression").click(function() 
			{
				submitRegExSelectorChange("/config/updateRegExSelector", this);
			});			
			
			$("#addVariable").click(function() 
			{
				addNewRegExVariable();
				//alert("adf");
				//submitRegExSelectorChange("/config/updateRegExSelector", this);
			});			
		});
		
		
		function addNewRegExVariable() 
		{ 
			//add new reg ex variable 
			var sidx = $(".variableIndex").last().val();
			var idx = Number(sidx) + 1; 
			
			if(isNaN(idx))
				idx = 0;
				
			var ob = {index: idx};
				
			var source   = $("#newVariableTemplate").html();
			var template = Handlebars.compile(source);
			var html = template(ob);
			
			$("#variableListContainer").append(html);
			//TODO:
			//wireDeleteRegExVariableButton();
	    }
		
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
	
	
<script id="newVariableTemplate" type="text/x-handlebars-template">
  <div class="variableContainer">
	<input type="hidden" value="${i.index}" class="variableIndex">
 	<label for="configVariables{{index}}.name">Variable Name</label>
	<input id="configVariables{{index}}.name" name="configVariables[{{index}}].name" style="width:350px" type="text"/>
	<input id="configVariables{{index}}.required1" name="configVariables[{{index}}].required" style="margin-left: 10px; float:left" type="checkbox" value="true" checked="checked"/>
		<input type="hidden" name="_configVariables[{{index}}].required" value="on"/> 
	<label for="configVariables{{index}}.required" style="float:left">Required</label>
  </div>
  <br>
</script>
	
	
	
	
	<h2>Media Item Configuration </h2>
	
	<form:form method="POST" action="/config/save" commandName="config"
		class="formLayout">
		
		<form:hidden path="selectedRegExSelectorIndex"/>
		<form:hidden path="id"/>
		<form:hidden path="lastUpdate"/>
		
	
		<div class="shadowBox">
			<div class="shadowBoxHeader">Details 
		   		<span style="float: right;" class="shadowBoxHelp">
			   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
			   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
		   		</span>
	   		</div>
		</div>
		<br>
		<div class="shadowBox">
			<form:label path="description">Description</form:label>
			<form:input path="description" style="width: 550px" />
			<br>
	
			<form:label path="action">Action</form:label>
			<form:select path="action">
				<form:options items="${actions}" />
			</form:select>
			<br>
			
		</div>
		<br>
		
		
		<div class="shadowBox">
			<div class="shadowBoxHeader">Variables 
		   		<span style="float: right;" class="shadowBoxHelp">
			   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
			   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
		   		</span>
	   		</div>
		</div>
		<br>
		
		<div class="shadowBox">
		
			<span id="variableListContainer">
				<c:forEach items="${config.configVariables}" varStatus="i" var="variable">
					<div class="variableContainer">
					
						<input type="hidden" value="${i.index}" class="variableIndex">
						<input type="hidden" name="configVariables[${i.index}].id" value="${variable.id}"/>
						<input type="hidden" name="configVariables[${i.index}].lastUpdate" value="${variable.lastUpdate}"/>
						<input type="hidden" name="configVariables[${i.index}].value" value="${variable.value}"/>
						
					 
						<form:label path="configVariables[${i.index}].name">Variable Name</form:label>
						<form:input path="configVariables[${i.index}].name" style="width:350px"/>
						<form:checkbox path="configVariables[${i.index}].required" style="margin-left: 10px; float:left"/> 
						<form:label path="configVariables[${i.index}].required" style="float:left">Required</form:label>
					</div>
					<br>
			    </c:forEach>
			</span>
		    
		    <input type="button" value="Add Variable" id="addVariable" />
		</div>
		<br>
		
		
		<div class="shadowBox">
			<div class="shadowBoxHeader">Selectors 
		   		<span style="float: right;" class="shadowBoxHelp">
			   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
			   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
		   		</span>
	   		</div>
		</div>
		<br>
		
		<div class="shadowBox">
			<p>Regular Expression Selectors</p>
			<hr>
			<table style="width:100%">
				<tr>
					<td align="left" width="350px" valign="top">Description</td>
					<td align="left" width="350px" valign="top" style="margin-left: 100px;">Expression</td>
					<td align="right" width="80px" valign="top">Action</td>
				</tr>
			
				<c:forEach items="${config.regExSelectors}" var="selector" varStatus="i">
				
					<input type="hidden" name="regExSelectors[${i.index}].id" value="${selector.id}"/>
					<input type="hidden" name="regExSelectors[${i.index}].lastUpdate" value="${selector.lastUpdate}"/>
					<input type="hidden" name="regExSelectors[${i.index}].description" value="${selector.description}"/>
					<input type="hidden" name="regExSelectors[${i.index}].expression" value="${selector.expression}"/>
				
					<c:forEach items="${selector.variables}" var="variable" varStatus="ii">
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].id" value="${variable.id}"/>
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].lastUpdate" value="${variable.lastUpdate}"/>
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].groupAssembly" value="${variable.groupAssembly}"/>
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].replaceExpression" value="${variable.replaceExpression}"/>
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].replaceWithCharacter" value="${variable.replaceWithCharacter}"/>
											
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].configVariable.id" value="${variable.configVariable.id}"/>
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].configVariable.lastUpdate" value="${variable.configVariable.lastUpdate}"/>
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].configVariable.name" value="${variable.configVariable.name}"/>
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].configVariable.value" value="${variable.configVariable.value}"/>
						<input type="hidden" name="regExSelectors[${i.index}].variables[${ii.index}].configVariable.required" value="${variable.configVariable.required}"/>
					</c:forEach>
					
				
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
		    
		    <input type="button" value="Add Expression" id="addExpression" />
		</div>	
		<br>
		
		<div class="shadowBox">
			<p>Open Subtitles Selector</p>
			<hr>
		</div>	
		<br>
	
		<div class="shadowBox">
			<p>Generic Selectors</p>
			<hr>
			<form:label path="extensionsSelector">Extensions</form:label>
			<form:textarea path="extensionsSelector" rows="3" cols="100"/>
			<br>

			<form:label path="selectAllFiles">All files</form:label>
			<form:checkbox path="selectAllFiles" />
			<br>
			
			<form:label path="selectAllFolders">All folders</form:label>
			<form:checkbox path="selectAllFolders" />
			<br>
			
			<form:label path="selectAllEmptyFolders">Empty folders</form:label>
			<form:checkbox path="selectAllEmptyFolders" />
			<br>
		</div>	
		<br>
	
	
		<div class="shadowBox">
			<div class="shadowBoxHeader">Paths 
		   		<span style="float: right;" class="shadowBoxHelp">
			   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
			   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
		   		</span>
	   		</div>
		</div>
		<br>
		
	
		<div class="shadowBox">
			<form:label path="sourceDirectory">Source Directory</form:label>
			<form:input path="sourceDirectory" style="width: 550px" />
			<br>
		
			<form:label path="destinationRoot">Destination Root</form:label>
			<form:input path="destinationRoot" style="width: 550px" />
			<br>
		
			<form:label path="relativeDestinationPath">Destination Path</form:label>
			<form:input path="relativeDestinationPath" style="width: 550px" />
			<br>
		
		</div>
			
		<br>
		<br>
		<input type="submit" value="Save" style="width: 100"/><br>
		<input type="button" value="Cancel" onclick="window.location.replace('/config/cancel');" style="width: 100"/>
		<br>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>












































