<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp" %>

<script type="text/javascript" >

	$(function ()
	{	
		registerHandleBarIfCond();
		
		$(".shadowBoxHelp").click(function() {
			$(this).parent().parent().find("#helpArea").fadeToggle('fast');
		});
		
		$("#testExp").click(function() {
			sendAjax("/regExSelector/Test/");
		});
		
		$("#saveExp").click(function() 
		{
			$("form:first").attr("action", "/regExSelector/save");
			$("form:first").submit();
			
			
			//sendAjax("/regExSelector/Save/");
		});
		
		wireDeleteRegExVariableButton();
		
		$("#addVariable").click(function() 
		{
			var data = getNewRegExVariableViewModel();
			var json = JSON.stringify(data);
			
			sendAjax('/regExSelector/addRegExVariable/', json, displayRegExVariables, errorResponse);
		});
	});
	
	
	function wireDeleteRegExVariableButton()
	{
		$(".deleteRegExVariableButton").click(function() 
		{
			$.ajax({
				  url: "/regExSelector/deleteRegExVariable/",
				  data: {index : $(this).attr("id")},
				  success: deleteRegExVariable
				});
		});
	}
	
	
	
	function sendAjax(url, data, successMethod, errorMethod) 
	{
		$.ajax({ 
		    url: url, 
		    type: 'POST', 
		    dataType: 'json', 
		    data: data, 
		    contentType: 'application/json',
		    mimeType: 'application/json',
		    success: successMethod,
		    error: errorMethod
		}); 
	}
	
	function displayRegExVariables(data) 
    { 
		var source   = $("#configVariableTemplate").html();
		var template = Handlebars.compile(source);
		var html = template(data);
		
		$("#variablesContainer").html(html);
		wireDeleteRegExVariableButton();
    }
	
	function deleteRegExVariable(id)
	{
		$("#" + id).parent().remove();
		
		//alert(id);
	}
	
	
	
	function errorResponse(data,status,er) 
	{ 
        alert("erro occured");
    }
	
	function getNewRegExVariableViewModel()
	{	
		var model = 
		{
			"selectedConfigVariable": $("#toAddVariable\\.selectedConfigVariable").val(), 
			"groupAssembly": $("#toAddVariable\\.groupAssembly").val(),
			"replaceExpression": $("#toAddVariable\\.replaceExpression").val(),
			"replaceWithCharacter": $("#toAddVariable\\.replaceWithCharacter").val()
		};
		
		return model;
	}
	
	
	
	function registerHandleBarIfCond()
	{
		Handlebars.registerHelper('ifCond', function (v1, operator, v2, options) 
		{
		    switch (operator) {
		        case '==':
		            return (v1 == v2) ? options.fn(this) : options.inverse(this);
		        case '===':
		            return (v1 === v2) ? options.fn(this) : options.inverse(this);
		        case '<':
		            return (v1 < v2) ? options.fn(this) : options.inverse(this);
		        case '<=':
		            return (v1 <= v2) ? options.fn(this) : options.inverse(this);
		        case '>':
		            return (v1 > v2) ? options.fn(this) : options.inverse(this);
		        case '>=':
		            return (v1 >= v2) ? options.fn(this) : options.inverse(this);
		        case '&&':
		            return (v1 && v2) ? options.fn(this) : options.inverse(this);
		        case '||':
		            return (v1 || v2) ? options.fn(this) : options.inverse(this);
		        default:
		            return options.inverse(this);
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

<script id="configVariableTemplate" type="text/x-handlebars-template">
{{#each variables}}
  	<div class="shadowBox">
		<div id="{{@index}}" class="deleteRegExVariableButton" 
			   style="background-color: #8d8d8d;font-size: 18px;font-weight: bold;color: #FF8A00; 
			   padding: 1px 5px 1px 5px; float:right; cursor: hand;">X</div>

	   <label for="variables{{@index}}.configVariable">Variable</label>
		<select id="variables{{@index}}.configVariable" name="variables[{{@index}}].configVariable">
			<option value="NONE"> --SELECT--</option>
			{{#each ../configVariables}}
				{{#ifCond id '==' ../configVariable.id}}
					<option value="{{id}}" selected="selected">{{name}}</option>
				{{else}}
					<option value="{{id}}" >{{name}}</option>
				{{/ifCond}} 
			{{/each}}
		</select>
	    <br/>
	 
		<label for="variables{{@index}}.groupAssembly">Group Assembly</label>
		<input id="variables{{@index}}.groupAssembly" name="variables[{{@index}}].groupAssembly" style="width:400px" type="text" value="{{groupAssembly}}"/> 
		<div class="errorBox" style="width:400px" id="nameParser.assembledItem_error">
			<div style="width:400px;"></div>
		 </div>  
		<br/>
		
		<label for="variables{{@index}}.replaceExpression">Replace RegEx</label>
		<input id="variables{{@index}}.replaceExpression" name="variables[{{@index}}].replaceExpression" style="width:650px" type="text" value="{{replaceExpression}}"/>
		<br/>
		
		<label for="variables{{@index}}.replaceWithCharacter">Replace String</label>
		<input id="variables{{@index}}.replaceWithCharacter" name="variables[{{@index}}].replaceWithCharacter" style="width:100px" type="text" value="{{replaceWithCharacter}}"/>
		<br/>
	</div>
	<br/>
{{/each}}
</script>

<h2>Movie Expression Generator</h2>





<form:form method="POST" action="/regExSelector/save" commandName="regExSelector" class="formLayout">
   
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
  </div>
   <br/>
   
   <div class="shadowBox" >
   		
		<form:label path="description">Description</form:label>
   		<form:input path="description" style="width:400px" id="description"/>
		<div class="errorBox" style="width:400px" id="description_error">
   			<div style="width:400px;"></div>
   		</div>
		<br/>
		
   		<form:label path="expression">Expression</form:label>
   		<form:input path="expression" style="width:650px" id="expression"/>
   		<div class="errorBox" style="width:650px" id="expression_error">
   			<div style="width:650px;"></div>
   		 </div>
	   	<br/>
   </div>
   <br/>
   
   
   
   <div class="shadowBox">
	   <div class="shadowBoxHeader">Configuration Variables
		  <span style="float: right;" class="shadowBoxHelp" >
		   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
		   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
	   		</span>
   		</div>
	   
	   <p id="helpArea" style="display: none;">Use the groups extracted in the regular expression above to assemble the movie name by entering the group numbers 
	   and any additional text in the Assembled Name text box.  Clean the movie name by 
	   using the Remove Characters text box to remove characters specified; or alternatively enter a regular expression with a 
	   single capture group in the Recursive Expression text box</p>
   </div>
   <br/>
   
   <span id="variablesContainer">
	   <c:forEach items="${regExSelector.variables}" varStatus="i">
	
		   <div class="shadowBox">
			   <div id="${i.index}" class="deleteRegExVariableButton" 
			   style="background-color: #8d8d8d;font-size: 18px;font-weight: bold;color: #FF8A00; 
			   padding: 1px 5px 1px 5px; float:right; cursor: hand;">X</div>
		   
			   <form:label path="variables[${i.index}].configVariable">Variable</form:label>
			   <form:select path="variables[${i.index}].configVariable">
					<form:option value="NONE"> --SELECT--</form:option>
					<form:options items="${regExSelector.configVariables}" itemValue="id" itemLabel="name"></form:options>
				</form:select>
			    <br/>
		   
		   		<form:label path="variables[${i.index}].groupAssembly">Group Assembly</form:label>
		   		<form:input path="variables[${i.index}].groupAssembly" style="width:400px"/> 
		   		<div class="errorBox" style="width:400px" id="nameParser.assembledItem_error">
		   			<div style="width:400px;"></div>
		   		 </div>  
		   		<br/>
		   		
		   		<form:label path="variables[${i.index}].replaceExpression">Replace RegEx</form:label>
		   		<form:input path="variables[${i.index}].replaceExpression" style="width:650px" />
		   		<br/>
		   		
		   		<form:label path="variables[${i.index}].replaceWithCharacter">Replace String</form:label>
		   		<form:input path="variables[${i.index}].replaceWithCharacter" style="width:100px" />
		   		<br/>
		   </div>
		   <br/>
			
		</c:forEach>
	</span>
	
   
    <div class="shadowBox">
	   
	    <form:label path="toAddVariable.configVariable">Variable</form:label>
	    
		<form:select path="toAddVariable.selectedConfigVariable">
			<form:option value="NONE"> --SELECT--</form:option>
			<form:options items="${regExSelector.configVariables}" itemValue="id" itemLabel="name"></form:options>
		</form:select>
		
		<br/>
		
   		<form:label path="toAddVariable.groupAssembly">Group Assembly</form:label>
   		<form:input path="toAddVariable.groupAssembly" style="width:400px"/> 
   		<div class="errorBox" style="width:400px" id="nameParser.assembledItem_error">
   			<div style="width:400px;"></div>
   		 </div>  
   		<br/>
   		
   		<form:label path="toAddVariable.replaceExpression">Replace RegEx</form:label>
   		<form:input path="toAddVariable.replaceExpression" style="width:650px" />
   		<br/>
   		
   		<form:label path="toAddVariable.replaceWithCharacter">Replace String</form:label>
   		<form:input path="toAddVariable.replaceWithCharacter" style="width:100px" />
   		<br/>
   		
   		<input type="button" value="Add Variable" id="addVariable" />
    </div>
	<br/>
	
   
   
   <div class="shadowBox">
	   <div class="shadowBoxHeader" >Test Expression
		   <span style="float: right;" class="shadowBoxHelp" >
		   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
		   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
	   		</span>
		</div>
	   
	   <p id="helpArea" style="display: none;">Test the regular expression, and the name and year filter entered above by entering a 
	   file name and clicking on the Test button.  The Captured Movie Name and the Captured Movie Year fields will be 
	   populated if the test passes.</p>
   </div>
   <br/>
   
   <div class="shadowBox">
	
   		<form:label path="testFileName">File Name</form:label>
   		<form:input path="testFileName" style="width:650px" id="testFileName"/>
   		<div class="errorBox" style="width:642px" id="testFileName_error">
   			<div style="width:640px;"></div>
   		</div>
   		<br/>
   		<input type="button" value="Test" id="testExp" />
   		<br/>
   		
   		
   		
   		
   		<form:label path="testName">Captured Movie Name</form:label><br/>
   		<form:input path="testName" style="width:400px" id="testName"/>
   		<br/>
   		
   		<form:label path="testYear">Captured Movie Year</form:label><br/>
   		<form:input path="testYear" style="width:400px" id="testYear" />
   </div>

	<br/>
   	<br/>
	<input type="button" value="Save" id="saveExp" />
	<br>
	<br>
	<br>




<%-- 
<span id="testContainer">
	<c:forEach items="${regExSelector.testList}" varStatus="i">
		
		<span id="bla">
			<form:input path="testList[${i.index}]" /> <input type="button" value="delete" class="deleteTest" /> <br/>
		</span>
		
	</c:forEach>
</span>
	
	<input type="button" value="add test" id="addTestItem" />
		
		 --%>
	</form:form>




<!-- <script type="text/javascript" >

	$(function ()
	{	
		
		
			$("#addTestItem").click(function() 
			{
				alert("asdf");
				
				$("#testContainer").append("<input id=\"testList2\" name=\"testList[2]\" type=\"text\" value=\"three xxx\"/>");
			});
		
		
			$(".deleteTest").click(function() 
			{
				//alert($(this).parent().attr("id"));
				
				$(this).parent().remove();
			});
			
	});

</script>
 -->



<%@include file="includes/footer.jsp" %>











































