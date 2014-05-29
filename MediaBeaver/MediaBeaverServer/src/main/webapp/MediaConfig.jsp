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
	
	
	<h2>Media Item Configuration </h2>
	
	<form:form method="POST" action="/config/save" commandName="config"
		class="formLayout">
		
		<form:hidden path="selectedRegExSelectorIndex"/>
		<form:hidden path="id"/>
	
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
			<c:forEach items="${config.configVariables}" varStatus="i">
				<form:label path="configVariables[${i.index}].name">Variable Name</form:label>
				<form:input path="configVariables[${i.index}].name" style="width:350px"/>
				<form:checkbox path="configVariables[${i.index}].required" style="margin-left: 10px; float:left"/> 
				<form:label path="configVariables[${i.index}].required" style="float:left">Required</form:label>
				<br>
		    </c:forEach>
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
		<input type="button" value="Cancel" onclick="window.location.replace('/configList');" style="width: 100"/>
		<br>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>












































