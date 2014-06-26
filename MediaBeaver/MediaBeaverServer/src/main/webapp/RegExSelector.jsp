<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp" %>

<script type="text/javascript" >
	
 	var configVariables =[<c:forEach var="item" items="${configVariables}">
		{"name":'<c:out value="${item.name}" />', "id": '<c:out value="${item.id}" />'},
	</c:forEach>]; 
		
	$(function ()
	{	
		registerHandleBarIfCond();
		
		$("#addVariable").click(function() 
		{
			addHide();
			setTimeout(function(){}, 80);
			
			if(addError())
				return; 
			
			addNewRegExVariable(this);
			refreshTestResults();
			clearAddRegExFields();
		});
		
		wireDeleteRegExVariableButton();
		
		$(".shadowBoxHelp").click(function() {
			$(this).parent().parent().find("#helpArea").fadeToggle('fast');
		});
		
		$("#testButton").click(function() 
		{
			testHide();
			setTimeout(function(){}, 80);
			
			if(testError())
				return;
			
			postAjax("/regExSelector/test", JSON.stringify(getRegExSelector()), showTestResults, errorResponse); 
		});
		
		$("#saveExp").click(function() 
		{
			saveHide();
			setTimeout(function(){}, 80);
			
			if(saveError())
				return; 
						
			$("form:first").attr("action", "/regExSelector/save");
			$("form:first").submit();
		});
		
	});
	
	function wireDeleteRegExVariableButton()
	{
		$(".deleteRegExVariableButton").click(function() 
		{
			$(this).parent().next("br").remove();
			$(this).parent().remove();
			refreshTestResults();
		});
	} 
	
	function addNewRegExVariable(caller) 
	{ 
		//add new reg ex variable 
		var sidx = $(".variableIndex").last().val();
		var idx = Number(sidx) + 1; 
		
		if(isNaN(idx))
			idx = 0;
			
		var ob = {
				index: idx,
				variables: configVariables,
				selectedConfigVariable: $("#toAdd_ConfigVariable").val(),
				groupAssembly: $("#toAdd_GroupAssembly").val(),
				replaceExpression: $("#toAdd_ReplaceExpression").val(),
				replaceWithCharacter: $("#toAdd_ReplaceWithCharacter").val(),
				};
			
		var source   = $("#newConfigVariableTemplate").html();
		var template = Handlebars.compile(source);
		var html = template(ob);
		
		$("#variablesContainer").append(html);
		//$(caller).parent().before(html);
		wireDeleteRegExVariableButton();
    }
	
	function clearAddRegExFields()
	{
		$("#toAdd_ConfigVariable").val("");
		$("#toAdd_GroupAssembly").val("");
		$("#toAdd_ReplaceExpression").val("");
		$("#toAdd_ReplaceWithCharacter").val("");
	}
	
	
	function refreshTestResults() 
	{ 
		var regExs = $(".configVariableSelection").map(function() 
				{
					return {id: this.value, name: $(this).find("option:selected").text()};
				}
		).get();
		
		var testInputs = $(".testVariableContainer").find("#variableId").map(function() {return this.value;}).get();
		
		removeRedundantTests(regExs, testInputs);
		addNewTests(regExs, testInputs);
	}
	
	function removeRedundantTests(regExs, testInputs)
	{
		//remove regexp tests if the regex no longer exists
		for (var tii = 0; tii < testInputs.length; tii++) 
		{
			var exists = false;
			for (var rei = 0; rei < regExs.length; rei++) 
			{
				if(regExs[rei].id == testInputs[tii])
				{
					exists = true;
				}
			}
			
			if(!exists)
			{
				$("#testVariableContainer_" + testInputs[tii]).remove();
			}
		}
	}
	
	function addNewTests(regExs, testInputs)
	{
		//add regexp tests if the regex exists but there is no regexp test 
		for (var rei = 0; rei < regExs.length; rei++) 
		{
			var exists = false;
			for (var tii = 0; tii < testInputs.length; tii++)  
			{
				if(regExs[rei].id == testInputs[tii])
				{
					exists = true;
				}
			}
			
			if(!exists)
			{
				var source   = $("#newTestVariable").html();
				var template = Handlebars.compile(source);
				var html = template({"id":regExs[rei].id,"name":regExs[rei].name});
				$("#testResultsContainer").append(html);
				
			}
		}
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
	
	function getRegExSelector()
	{
		var obj = {
			"variables":getRegExVariablesArray(),
			"testFileName":$("#testResults_FileName").val(),
			"expression":$("#expression").val()
		};

		return obj;
	}
	
	function getRegExVariablesArray()
	{
		var vars = $("#variablesContainer").find(".shadowBox");
		
		var varList = [];
		for (var i = 0; i < vars.length; i++) 
		{
			var idx = $(vars[i]).find(".variableIndex").val();
			
			//note configVariable goes to selectedConfigVariable since we cannot get mapping functionality to work properly with ajax
			var v = {
				"id": $("#variables"+idx+"\\.id").val(),
				"lastUpdate":$("#variables"+idx+"\\.lastUpdate").val(),
				"selectedConfigVariable":$("#variables"+idx+"\\.configVariable").val(),
				"groupAssembly":$("#variables"+idx+"\\.groupAssembly").val(),
				"replaceExpression":$("#variables"+idx+"\\.replaceExpression").val(),
				"replaceWithCharacter":$("#variables"+idx+"\\.replaceWithCharacter").val() 
			};
		
			varList[i] = v;
		}
		
		return varList;		
	}
	
	function showTestResults(data)
	{
		for (var i = 0; i < data.testVariables.length; i++) 
		{
			if($("#testVariable_" + data.testVariables[i].id).length){
				$("#testVariable_" + data.testVariables[i].id).val(data.testVariables[i].value);
			}
		}
	}
	
	
	// Validation ///////////////////////////////////////////////////////////////////
	
	function saveError()
	{
		var error = false;
		
		if($("#description").validationEngine('validate')){
			error= true;
		}
		
		if($("#expression").validationEngine('validate')){
			error= true;
		}
		
		if(regExVariablesError()){
			error = true;
		}
		
		return error;
	}
	
	
	function addError()
	{
		var error = false;
		
		if($("#toAdd_ConfigVariable").validationEngine('validate'))
			error= true;
		
		if($("#toAdd_GroupAssembly").validationEngine('validate'))
			error= true;
		
		return error;
	}
	
	function saveHide()
	{
		$("#toAdd_ConfigVariable").validationEngine('hide');
		$("#toAdd_GroupAssembly").validationEngine('hide');
		$("#testResults_FileName").validationEngine('hide');
	}
	
	
	function addHide()
	{
		$("#description").validationEngine('hide');
		$("#expression").validationEngine('hide');
		$("#testResults_FileName").validationEngine('hide');
		regExVariablesHide();
	}
	
	function testHide()
	{
		$("#description").validationEngine('hide');
		$("#toAdd_ConfigVariable").validationEngine('hide');
		$("#toAdd_GroupAssembly").validationEngine('hide');
	}
	
	function testError()
	{
		var error = false;
		
		if($("#expression").validationEngine('validate'))
			error= true;
		
		if($("#testResults_FileName").validationEngine('validate'))
			error= true;	
		
		if(regExVariablesError())
			error = true;
		
		return error;
	}
	
	function regExVariablesError()
	{
		var vars = $("#variablesContainer").find(".shadowBox");
		
		var error = false;
		for (var i = 0; i < vars.length; i++) 
		{
			var idx = $(vars[i]).find(".variableIndex").val();
			
			if($("#variables"+idx+"\\.configVariable").validationEngine('validate'))
				error = true;
			if($("#variables"+idx+"\\.groupAssembly").validationEngine('validate'))
				error = true;
		}
		
		return error;
	}
	
	function regExVariablesHide()
	{
		var vars = $("#variablesContainer").find(".shadowBox");
		for (var i = 0; i < vars.length; i++) 
		{
			var idx = $(vars[i]).find(".variableIndex").val();
			
			$("#variables"+idx+"\\.configVariable").validationEngine('hide');
			$("#variables"+idx+"\\.groupAssembly").validationEngine('hide');
		}
	}
	
</script>


<script id="newConfigVariableTemplate" type="text/x-handlebars-template">
 <div class="shadowBox">
	<input type="hidden" id="index" value="{{index}}" class="variableIndex"> 
		   
	<div id="{{index}}" class="deleteRegExVariableButton" 
	style="background-color: #8d8d8d;font-size: 18px;font-weight: bold;color: #FF8A00; 
	padding: 1px 5px 1px 5px; float:right; cursor: hand;">X</div>
		   
	<label for="variables{{index}}.configVariable">Variable</label>
	<select id="variables{{index}}.configVariable" name="variables[{{index}}].configVariable" class="configVariableSelection validate[required]">
		<option value=""> --SELECT--</option>
		
		{{#each variables}}
			{{#ifCond id '==' ../selectedConfigVariable}}
				<option value="{{id}}" selected="selected">{{name}}</option>
			{{else}}
				<option value="{{id}}" >{{name}}</option>
			{{/ifCond}} 
		{{/each}}
	</select>
	<br/>
		   
	<label for="variables{{index}}.groupAssembly">Group Assembly</label>
	<input id="variables{{index}}.groupAssembly" name="variables[{{index}}].groupAssembly" style="width:400px" type="text" value="{{groupAssembly}}" class="validate[required]"/> 
	<br/>
		   		
	<label for="variables{{index}}.replaceExpression">Replace RegEx</label>
	<input id="variables{{index}}.replaceExpression" name="variables[{{index}}].replaceExpression" style="width:650px" type="text" value="{{replaceExpression}}"/>
	<br/>
		   		
	<label for="variables{{index}}.replaceWithCharacter">Replace String</label>
	<input id="variables{{index}}.replaceWithCharacter" name="variables[{{index}}].replaceWithCharacter" style="width:100px" type="text" value="{{replaceWithCharacter}}"/>
	<br/>
 </div>
 <br/>
</script>

<script id="newTestVariable" type="text/x-handlebars-template">
	<span class="testVariableContainer" id="testVariableContainer_{{id}}">
		<input type="hidden" id="variableId" value="{{id}}" />
		<label for="testVariable_{{id}}">{{name}}</label>
		<input id="testVariable_{{id}}" style="width:400px" type="text"/>
		<br/>
	</span>
</script>

<h2>Regular Expression Selector</h2>


<form:form method="POST" action="/regExSelector/save" commandName="regExSelector" class="formLayout">
   
   <form:hidden path="index"/>
   <form:hidden path="id"/>
   <%-- <form:hidden path="version"/> --%>
   <form:hidden path="lastUpdate" />
   
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
   		<form:input path="description" style="width:400px" id="description" class="validate[required]" />
		<!-- <div class="errorBox" style="width:400px" id="description_error">
   			<div style="width:400px;"></div>
   		</div> -->
		<br/>
		
   		<form:label path="expression">Expression</form:label>
   		<form:input path="expression" style="width:650px" id="expression" class="validate[required]" data-prompt-position="topRight: -130"/>
   		<div class="errorBox" style="width:650px" id="expression_error">
   			<div style="width:650px;"></div>
   		 </div>
	   	<br/>
   </div>
   <br/>
   
   
   
   <div class="shadowBox" >
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
		   	   
		   	   <input type="hidden" value="${i.index}" class="variableIndex"> 
		   	   
			   <form:hidden path="variables[${i.index}].id"/>
			   <%-- <form:hidden path="variables[${i.index}].version"/> --%>
			   <form:hidden path="variables[${i.index}].lastUpdate" />
		   
			   <div id="${i.index}" class="deleteRegExVariableButton" 
			   style="background-color: #8d8d8d;font-size: 18px;font-weight: bold;color: #FF8A00; 
			   padding: 1px 5px 1px 5px; float:right; cursor: hand;">X</div>
		   
			   <form:label path="variables[${i.index}].configVariable">Variable</form:label>
			  
			   <form:select path="variables[${i.index}].configVariable" class="configVariableSelection validate[required]" >
					<form:option value=""> --SELECT--</form:option>
					<form:options items="${configVariables}" itemValue="id" itemLabel="name" ></form:options>
				</form:select>
			    <br/>
		   
		   		<form:label path="variables[${i.index}].groupAssembly">Group Assembly</form:label>
		   		<form:input path="variables[${i.index}].groupAssembly" style="width:400px" class="validate[required]" /> 
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
	   
		<label for="toAdd_ConfigVariable">Variable</label>
		<select id="toAdd_ConfigVariable" class="validate[required]">
			<option value="" selected="selected"> --SELECT--</option>
			<c:forEach var="item" items="${configVariables}">
				<option value="${item.id}">${item.name}</option>
			</c:forEach>	
		</select>
		<br/>
		
   		<label for="toAdd_GroupAssembly">Group Assembly</label>
   		<input id="toAdd_GroupAssembly" style="width:400px" type="text" class="validate[required]" /> 
   		<br/>
   		
   		<label for="toAdd_ReplaceExpression">Replace RegEx</label>
   		<input id="toAdd_ReplaceExpression" style="width:650px" type="text"/> 
   		<br/>
   		
   		<label for="toAdd_ReplaceWithCharacter">Replace String</label>
   		<input id="toAdd_ReplaceWithCharacter" style="width:100px" type="text"/> 
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
		<label for="testResults_FileName">File Name</label>
		<input id="testResults_FileName" style="width:500px" type="text" class="validate[required]" />
		<input type="button" value="Test" id="testButton" />
		<br/>
		
		<span id="testResultsContainer">
	 		<c:forEach items="${regExSelector.variables}" varStatus="i" var="item" >
	 			
	 			<span class="testVariableContainer" id="testVariableContainer_${item.configVariable.id}">
	 				<input type="hidden" id="variableId" value="${item.configVariable.id}" />
					<label for="testVariable_${item.configVariable.id}">${item.configVariable.name}</label>
					<input id="testVariable_${item.configVariable.id}" style="width:400px" type="text"/>
			   		<br/>
				</span>
				
			</c:forEach>
		</span>	
	</div>
	
	<br/>
	<br/>
	<input type="button" value="Save" id="saveExp" style="width: 100" /><br/>
	<input type="button" value="Cancel" onclick="window.location.replace('/config');" style="width: 100"/>
	<br>
	<br>
	<br>

</form:form>


<%@include file="includes/footer.jsp" %>











































