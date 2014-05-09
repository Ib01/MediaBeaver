<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp" %>

<script type="text/javascript" >

	$(function ()
	{	
		$(".shadowBoxHelp").click(function() {
			$(this).parent().parent().find("#helpArea").fadeToggle('fast');
		});
		
		$("#testExp").click(function() {
			sendAjax("/movieRegEx/Test/");
		});
		
		$("#saveExp").click(function() {
			sendAjax("/movieRegEx/Save/");
		});
		
		
	});
	
	
	function sendAjax(url) 
	{
		$.ajax({ 
		    url: url, 
		    type: 'POST', 
		    dataType: 'json', 
		    data: getModel(), 
		    contentType: 'application/json',
		    mimeType: 'application/json',
		    success: function(data) 
		    { 
		    	resetErrors();
		    	showErrors(data.errors);
		    	
		    	$("#generatedName").val(data.generatedName);
		    	$("#generatedYear").val(data.generatedYear);
		    },
		    error:function(data,status,er) { 
		        alert("erro occured");
		    }
		}); 
	}
	
	function showErrors(errors)
	{
		for(var i=0; i < errors.length; i++)
    	{
			var field = "#" + errors[i].field.replace(".", "\\.");
			
			$(field).css("border", "solid 1px red");
			$(field + "_error").children(":first").html(errors[i].message);
			$(field + "_error").fadeIn(400);
		}
	}
	
	function resetErrors()
	{
		$("#expression").css("border", "");
		$("#nameParser\\.assembledItem").css("border", "");
		$("#yearParser\\.assembledItem").css("border", "");
		$("#testFileName").css("border", "");
		$("#expression_error").fadeOut(400);
		$("#nameParser\\.assembledItem_error").fadeOut(400);
		$("#yearParser\\.assembledItem_error").fadeOut(400);
		$("#testFileName_error").fadeOut(400);
	}
	
	
	function getModel()
	{
		var np = {"assembledItem": $("#nameParser\\.assembledItem").val(), "recursiveRegEx": $("#nameParser\\.recursiveRegEx").val()};
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
		
		return JSON.stringify(model);
	}
	
</script>

<h2>Movie Expression Generator</h2>

<form:form method="POST" action="/regExSelector/saveRegExSelector" commandName="regExSelector">
   
   <form:hidden path="id" id="id"/>
   
   <div class="shadowBox" >
   		<div class="shadowBoxHeader">Regular Expression 
	   		<span style="float: right;" class="shadowBoxHelp">
		   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
		   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
	   		</span>
   		</div>

   		<p id="helpArea" style="display: none;">Enter a regular expression in the Expression text box below.  This regular expression will serve to identify 
   		a file as a movie file and will enable the system to extract the movie name and year from the file name. the 
   		regular expression must contain 2 or more regular expression capture groups.  these groups will be used to assemble the 
   		movie name and year below</p>
		<br/>
		
		<form:label path="description">Description</form:label><br/>
   		<form:input path="description" style="width:695px" id="description"/>
		<div class="errorBox" style="width:692px" id="description_error">
   			<div style="width:690px;"></div>
   		 </div>
		<br/>
		
   		<form:label path="expression">Expression</form:label>
   		<form:input path="expression" style="width:695px" id="expression"/>
   		<div class="errorBox" style="width:692px" id="expression_error">
   			<div style="width:690px;"></div>
   		 </div>
	   	
   </div>
   <br/>
   
   
   <%-- <c:forEach items="${regExSelector.variables}" varStatus="i"> --%>
   
   <div class="shadowBox">
	   <div class="shadowBoxHeader">Configuration Variable
		  <span style="float: right;" class="shadowBoxHelp" >
		   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
		   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
	   		</span>
   		</div>
	   
	   <p id="helpArea" style="display: none;">Use the groups extracted in the regular expression above to assemble the movie name by entering the group numbers 
	   and any additional text in the Assembled Name text box.  Clean the movie name by 
	   using the Remove Characters text box to remove characters specified; or alternatively enter a regular expression with a 
	   single capture group in the Recursive Expression text box</p>
	   
	   <table style="width:100%">
			<tr>
				<td>Variable</td>
				<td>Group Assembly</td>
				<td>Replace Expression</td>
				<td>Replace with Character</td>
				<td></td>
			</tr>
			
			
			<c:forEach items="${regExSelector.variables}" var="variable" >
				<tr>
					<td><c:out value="${variable.configVariable.name}" /></td>
					<td><c:out value="${variable.groupAssembly}" /></td>
					<td><c:out value="${variable.replaceExpression}" /></td>
					<td><c:out value="${variable.replaceWithCharacter}" /></td>
					<td><a href="#">edit</a></td>
				</tr>
			</c:forEach>
	    </table>
	    
	    <br />
	    <hr/>
	    
	    <form:select path="toAddVariable.configVariable">
			<form:option value="NONE"> --SELECT--</form:option>
			<form:options items="${configVariables}" itemValue="id" itemLabel="name"></form:options>
		</form:select>
	   
   
   		<form:label path="toAddVariable.groupAssembly">Group Assembly</form:label><br/>
   		<form:input path="toAddVariable.groupAssembly" style="width:495px"/> 
   		<!-- <div class="errorBox" style="width:492px" id="nameParser.assembledItem_error">
   			<div style="width:490px;"></div>
   		 </div>   -->
   		   
   		<br/>
   		<form:label path="toAddVariable.replaceExpression">Replace Expression</form:label><br/>
   		<form:input path="toAddVariable.replaceExpression" style="width:100%" id="nameParser_removeCharacters"/>
   		<br/>
   		<form:label path="toAddVariable.replaceWithCharacter">Replace with Character</form:label><br/>
   		<form:input path="toAddVariable.replaceWithCharacter" style="width:100%" id="nameParser.recursiveRegEx"/>
	   	
	   	<br/>
	   	<input type="button" value="Add Variable" id="addVariable" />
	   
	   <%-- 
	   <form:label path="variables[${i.index}].configVariable">Variable</form:label><br/>
	   <form:select path="variables[${i.index}].configVariable">
			<form:option value="NONE"> --SELECT--</form:option>
			<form:options items="${configVariables}" itemValue="id" itemLabel="name"></form:options>
		</form:select>
	    <br/>
   
   		<form:label path="variables[${i.index}].groupAssembly">Group Assembly</form:label><br/>
   		<form:input path="variables[${i.index}].groupAssembly" style="width:495px"/> 
   		<!-- <div class="errorBox" style="width:492px" id="nameParser.assembledItem_error">
   			<div style="width:490px;"></div>
   		 </div>   -->
   		   
   		<br/>
   		<form:label path="variables[${i.index}].replaceExpression">Replace Expression</form:label><br/>
   		<form:input path="variables[${i.index}].replaceExpression" style="width:295px" id="nameParser_removeCharacters"/>
   		<br/>
   		<form:label path="variables[${i.index}].replaceWithCharacter">Replace with Character</form:label><br/>
   		<form:input path="variables[${i.index}].replaceWithCharacter" style="width:695px" id="nameParser.recursiveRegEx"/> --%>
   </div>
	   
	<br/>
	<%-- </c:forEach> --%>
   
    <%-- <div class="shadowBox">
	   <div class="shadowBoxHeader">Add Variable
		  <span style="float: right;" class="shadowBoxHelp" >
		   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
		   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
	   		</span>
   		</div>
	   
	   <p id="helpArea" style="display: none;">Use the groups extracted in the regular expression above to assemble the movie name by entering the group numbers 
	   and any additional text in the Assembled Name text box.  Clean the movie name by 
	   using the Remove Characters text box to remove characters specified; or alternatively enter a regular expression with a 
	   single capture group in the Recursive Expression text box</p>
	   
		<form:select path="toAddVariable.configVariable">
			<form:option value="NONE"> --SELECT--</form:option>
			<form:options items="${configVariables}" itemValue="id" itemLabel="name"></form:options>
		</form:select>
	   
   
   		<form:label path="toAddVariable.groupAssembly">Group Assembly</form:label><br/>
   		<form:input path="toAddVariable.groupAssembly" style="width:495px"/> 
   		<!-- <div class="errorBox" style="width:492px" id="nameParser.assembledItem_error">
   			<div style="width:490px;"></div>
   		 </div>   -->
   		   
   		<br/>
   		<form:label path="toAddVariable.replaceExpression">Replace Expression</form:label><br/>
   		<form:input path="toAddVariable.replaceExpression" style="width:295px" id="nameParser_removeCharacters"/>
   		<br/>
   		<form:label path="toAddVariable.replaceWithCharacter">Replace with Character</form:label><br/>
   		<form:input path="toAddVariable.replaceWithCharacter" style="width:695px" id="nameParser.recursiveRegEx"/>
    </div> --%>
   
   
   
   
	<br/>
	<input type="submit" value="Save" id="saveExp" />
   
   
   
   
   <%-- <div class="shadowBox">
	   <div class="shadowBoxHeader">Step 2) Assemble and Clean the Movie Name
		  <span style="float: right;" class="shadowBoxHelp" >
		   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
		   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
	   		</span>
   		</div>
	   
	   <p id="helpArea" style="display: none;">Use the groups extracted in the regular expression above to assemble the movie name by entering the group numbers 
	   and any additional text in the Assembled Name text box.  Clean the movie name by 
	   using the Remove Characters text box to remove characters specified; or alternatively enter a regular expression with a 
	   single capture group in the Recursive Expression text box</p>
   
   		<form:label path="nameParser.assembledItem">Assembled Name</form:label><br/>
   		<form:input path="nameParser.assembledItem" style="width:495px" id="nameParser.assembledItem"/> 
   		<div class="errorBox" style="width:492px" id="nameParser.assembledItem_error">
   			<div style="width:490px;"></div>
   		 </div>  
   		   
   		<br/>
   		<form:label path="nameParser.removeCharacters">Remove Characters</form:label><br/>
   		<form:input path="nameParser.removeCharacters" style="width:295px" id="nameParser_removeCharacters"/>
   		<br/>
   		<form:label path="nameParser.recursiveRegEx">Recursive Expression</form:label><br/>
   		<form:input path="nameParser.recursiveRegEx" style="width:695px" id="nameParser.recursiveRegEx"/>
   </div>
	<br/>

	<div class="shadowBox">
	   <div class="shadowBoxHeader">Step 3) Assemble and Clean the Movie Year
		   <span style="float: right;" class="shadowBoxHelp" >
		   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
		   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
	   		</span>
   		</div>
	   
	   <p id="helpArea" style="display: none;">Use the groups extracted in the regular expression above to assemble the movie year by entering the group numbers 
	   and any additional text in the Assembled Name text box.  Clean the movie year by using the Remove Characters text box 
	   to remove characters specified; or alternatively enter a regular expression with a 
	   single capture group in the Recursive Expression text box</p>
   
   		<form:label path="yearParser.assembledItem">Assembled Name</form:label><br/>
   		<form:input path="yearParser.assembledItem" style="width:495px" id="yearParser.assembledItem"/>  
   		<div class="errorBox" style="width:492px" id="yearParser.assembledItem_error">
   			<div style="width:490px;"></div>
   		 </div>
   		<br/>
   		<form:label path="yearParser.removeCharacters">Remove Characters</form:label><br/>
   		<form:input path="yearParser.removeCharacters" style="width:295px" id="yearParser_removeCharacters"/>
   		<br/>
   		<form:label path="yearParser.recursiveRegEx">Recursive Expression</form:label><br/>
   		<form:input path="yearParser.recursiveRegEx" style="width:695px" id="yearParser.recursiveRegEx"/>
   </div>
	<br/>

	<div class="shadowBox">
	   <div class="shadowBoxHeader" >Step 4) Validate the regular expression
		   <span style="float: right;" class="shadowBoxHelp" >
		   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
		   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
	   		</span>
		</div>
	   
	   <p id="helpArea" style="display: none;">Test the regular expression, and the name and year filter entered above by entering a 
	   file name and clicking on the Test button.  The Captured Movie Name and the Captured Movie Year fields will be 
	   populated if the test passes.</p>
   
   		<form:label path="testFileName">File Name</form:label><br/>
   		<form:input path="testFileName" style="width:645px" id="testFileName"/><input type="button" value="Test" id="testExp" />
   		<div class="errorBox" style="width:642px" id="testFileName_error">
   			<div style="width:640px;"></div>
   		 </div>
   		<br/>
   		
   		<form:label path="generatedName">Captured Movie Name</form:label><br/>
   		<form:input path="generatedName" style="width:395px" id="generatedName"/>
   		<br/>
   		
   		<form:label path="generatedYear">Captured Movie Year</form:label><br/>
   		<form:input path="generatedYear" style="width:395px" id="generatedYear" />
   		
   </div>

	<br/>
   	<br/>
	<input type="button" value="Save" id="saveExp" />
	<br> --%>
	
	
</form:form>



<%@include file="includes/footer.jsp" %>












































