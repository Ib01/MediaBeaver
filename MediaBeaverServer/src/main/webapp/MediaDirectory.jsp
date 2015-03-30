<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<%@include file="includes/header.jsp"%>

	<script type="text/javascript" >
	
		var uiEnabled = true;
		var deleteFilesEnabled;
		var moveManuallyEnabled;
		var moveFilesEnabled;
		var selectedLi;
		var openFromRootLi;
		var checkAfterOpenFromRoot;
		var operationErrorOccured;
		
		$(function ()
		{	
			registerHandleBarIfCond();
			
			$( "body" ).delegate( ".highlightableLi", "mouseover", function()  
			{
				$(this).css("background-color", "#F1F1F1");
			});
			
			$( "body" ).delegate( ".highlightableLi", "mouseout", function()  
			{
				$(this).css("background-color", "");
			});
			
		    $( "body" ).delegate( ".highlightableLi", "click", function()  
			{
				if(!uiEnabled) return;
				
				$(this).find("input:checkbox").prop(
						'checked', 
						!$(this).find("input:checkbox").is(':checked')
				);
				setMenuState();
				openFromRoot($(this)); 
			});
				
			$("body").delegate(".highlightableLi input:checkbox", "click", function()  
			{
				if(!uiEnabled) return;
				
				setMenuState();
				openFromRoot($(this).parent("li"));
				e.stopPropagation();
			});
			
			$("#moveManually").click(function() 
			{
				if(!uiEnabled || !moveManuallyEnabled) return;
				
				var selectedLis = $(".selectedCheckbox:checked").siblings("input[name$='mediaType'][value='Video']").parent("li");
				
				var vms = [];
				$(selectedLis).each(function(index) 
				{
					vms[index] = getFileViewModel(this);
				});
				
				doAjaxCall('/mediaDirectory/matchMedia', vms, matchMediaSuccess, operationError);
			});
			
			$("#cleanFiles").click(function() 
			{
				if(!uiEnabled || !moveManuallyEnabled) return;
				
				var selectedLis = $(".selectedCheckbox:checked").siblings("input[name$='mediaType'][value='Video']").parent("li");
				
				var vms = [];
				$(selectedLis).each(function(index) 
				{
					vms[index] = getFileViewModel(this);
				});
				
				doAjaxCall('/mediaDirectory/matchMedia', vms, matchMediaSuccess, operationError);
			});
			
			
			$("#deleteFiles").click(function() 
			{	
				if(!uiEnabled || !deleteFilesEnabled) return;
				
				$("#deleteConfirmationDialog").dialog("open");
			});
			
			$("#moveFiles").click(function() 
			{
				if(!uiEnabled || !moveFilesEnabled) return;
				
				$(".operationTried").val("false");
				selectedLi = getNextSelectedFileLi();
				
				if(selectedLi.length > 0)
				{
					operationErrorOccured = false;
					
					var vm = getFileViewModel(selectedLi);
					dissableInterface();
					callMove(vm);
				}
					
			});
	
			$( "body" ).delegate( ".folderName", "mouseover", function()
			{
				$(this).css("text-decoration", "underline");
			});
	
			$( "body" ).delegate( ".folderName", "mouseout", function()
			{
				$(this).css("text-decoration", "");
			});
			
			$("body").delegate(".folderName", "click", function(e)		
			{	
				if(!uiEnabled) return;
				
				e.stopPropagation();
				
				var isOpen = $(this).siblings("input[name$='open'][value='true']");
				if(isOpen.length > 0)
				{
					$(this).parent("li").next("li").remove();
					$(this).siblings("input[name$='open']").val("false");
					setMenuState();
				}
				else
				{
					selectedLi = $(this).parent("li");
					var vm = getFileViewModel(selectedLi);
					dissableInterface();
					callOpen(vm);
				}
			});
			
			$("#expandAll").click(function(e) 
			{	
				if(!uiEnabled) return;
				
				selectedLi =  getNextUnopenedFolderLi();
				if(selectedLi.length > 0)
				{
					var vm = getFileViewModel(selectedLi);
					dissableInterface();
					callOpenAll(vm);
				}
				else
				{
					$("#expandAll").hide();
					$("#colapseAll").show();
				}
				
				e.stopPropagation();
			});
			
			
			$("#colapseAll").click(function(e) 
			{	
				if(!uiEnabled) return; 
				
				$(".folderName").parent("li").next("li").children("ul").parent().remove();
				$(".folderName").siblings("input[name$='open']").val("false");
				
				$("#expandAll").show();
				$("#colapseAll").hide();
				
				setMenuState();
				
				e.stopPropagation();
			});
			
			$("#selectAll").click(function(e) 
			{	
				if(!uiEnabled) return;
				
				$("input:checkbox").prop('checked', true);
				
				$("#selectAll").hide();
				$("#deselectAll").show();
				setMenuState();
			});
			
			$("#deselectAll").click(function(e) 
			{	
				if(!uiEnabled) return;
				
				$("input:checkbox").prop('checked', false);
				
				$("#selectAll").show();
				$("#deselectAll").hide();
				setMenuState();
			});
			
			
			initialiseForm();
			
		    $("#operationErrorDialog").dialog({
		    	autoOpen : false, 
    			modal : true, 
    			width: 400, 
    			height: 220, 
    			show : "blind", 
    			hide : "blind", 
    			position: { my: "center", at: "center", of: "body" },
    			buttons: [{text: "Ok", click: function() {$( this ).dialog( "close" );}}]
			});
		    
		    $("#deleteConfirmationDialog").dialog({
		    	autoOpen : false, 
    			modal : true, 
    			width: 300, 
    			height: 220, 
    			show : "blind", 
    			hide : "blind", 
    			position: { my: "center", at: "center", of: "body" },
    			buttons: [
    			          {text: "No", click: function() {$( this ).dialog( "close" );}},
    			          {text: "Yes", click: function() {$( this ).dialog( "close" ); doDeleteFiles();}}
    			          ]
			});
		    
		    $("#unspecifiedErrorDialog").dialog({
		    	autoOpen : false, 
    			modal : true, 
    			width: 400, 
    			height: 220, 
    			show : "blind", 
    			hide : "blind", 
    			position: { my: "center", at: "center", of: "body" },
    			buttons: [{text: "Ok", click: function() {$( this ).dialog( "close" );}}]
			});
		    
		    
		    
		}); 
		
		
		
		function doDeleteFiles()
		{
			$(".operationTried").val("false");
			selectedLi = getNextSelectedLi();
			
			if(selectedLi.length > 0)
			{
				operationErrorOccured = false;
				
				var viewModel = getFileViewModel(selectedLi);
				dissableInterface();
				callDelete(viewModel);
			}
		}
		
		function initialiseForm()
		{
			selectedLi = $("#rootLi");
			var vm = getFileViewModel(selectedLi);
			dissableInterface();
			callOpen(vm);
		}
		
		
		//-----------------------------------------------------------------------------------//
		//-- AJAX Callbacks 															----//
		//---------------------------------------------------------------------------------//
	
		function callOpenFromRoot(viewModel)
		{
			 doAjaxCall('/mediaDirectory/openFolder', viewModel, openFromRootSuccess, operationError);
		}
		
		function callOpenAll(viewModel)
		{
			 doAjaxCall('/mediaDirectory/openFolder', viewModel, openAllSuccess, operationError);
		}
		
		function callOpen(viewModel)
		{
			 doAjaxCall('/mediaDirectory/openFolder', viewModel, openSuccess, operationError);
		}
		
		function callMove(viewModel)
		{
			 doAjaxCall('/mediaDirectory/moveFile', viewModel, moveSuccess, operationError);
		}
		
		function callDelete(viewModel)
		{
			 doAjaxCall('/mediaDirectory/deleteFile', viewModel, deleteSuccess, operationError);
		}
		
		function openFromRoot(li) 
		{
			if(isFolder(li) && isChecked(li))				
			{
				checkAfterOpenFromRoot = true;
				
				if(hasChildren(li))
				{
					removeChildren(li);
				}
				
				openFromRootLi = null;
				selectedLi = li;
			
				var vm = getFileViewModel(li);
				dissableInterface();
				callOpenFromRoot(vm);
			}  
		}
		
		function openAllSuccess(data)
		{
			addChildItems(data, selectedLi);
			
			selectedLi =  getNextUnopenedFolderLi();
			if(selectedLi.length > 0)
			{
				var vm = getFileViewModel(selectedLi);
				callOpenAll(vm);
			}
			else
			{
				$("#expandAll").hide();
				$("#colapseAll").show();
				enableInterface();
				setMenuState();
			}
		}
		
		function openFromRootSuccess(data)
		{
			addChildItems(data, selectedLi);
			
			//first time itteration of this method
			if(openFromRootLi == null)
			{
				openFromRootLi = $(selectedLi).next("li");
			}
			
			selectedLi =  getNextUnopenedFolderLiOfRoot();
			if(selectedLi.length > 0)
			{
				var vm = getFileViewModel(selectedLi);
				callOpenFromRoot(vm);
			}
			else
			{
				if(checkAfterOpenFromRoot)
				{
					$(openFromRootLi).find("input:checkbox").prop('checked', true);
				}
				checkAfterOpenFromRoot = false;
				
				enableInterface();
				setMenuState();
			}
		}
		
		function openSuccess(data)
		{
			addChildItems(data, selectedLi);
			enableInterface();
			setMenuState();
		}
		
		
		function moveSuccess(data)
		{
			//change dom
			if(data.operationSuccess)
			{
				$(selectedLi).remove();
			}
			else
			{
				//ensure we dont reprocess this item
				$(selectedLi).find(".operationTried").val("true");
				operationErrorOccured = true;
			}
			
			//move next selected item
			selectedLi = getNextSelectedFileLi();
			if(selectedLi.length > 0)
			{
				var vm = getFileViewModel(selectedLi);
				callMove(vm);
			}
			else
			{
				enableInterface();
				setMenuState();
				
				if(operationErrorOccured)
				{
					$("#operationErrorMessage").text("An error occured while attempting to move one or more files.");					
					$("#operationErrorDialog").dialog("open");
				}
			}
			
		}
		
		function deleteSuccess(data)
		{
			//change dom
			if(data.operationSuccess)
			{
				if($(selectedLi).next("li").find("ul").length > 0)
					$(selectedLi).next("li").remove();
				
				$(selectedLi).remove();
			}
			else
			{
				//ensure we dont reprocess this item again
				$(selectedLi).find(".operationTried").val("true");
				operationErrorOccured = true;
			}
			
			//delete next selected item
			selectedLi = getNextSelectedLi();
			if(selectedLi.length > 0)
			{
				var viewModel = getFileViewModel(selectedLi);
				callDelete(viewModel);
			}
			else
			{
				enableInterface();
				setMenuState();
				
				if(operationErrorOccured)
				{
					$("#operationErrorMessage").text("An error occured while attempting to delete one or more files.");
					$("#operationErrorDialog").dialog("open");
				}
			}
		}
		
		function matchMediaSuccess(data)
		{
			window.location = "/mediaMatcher"; 
		}
		
		function operationError(data, status, er)
		{
			$("#unspecifiedErrorDialog").dialog("open");
			enableInterface();
			setMenuState();
		}
		
		function doAjaxCall(url, viewModel, successFunction, errorFunction)
		{
			$.ajax({
                url: url,
                data: JSON.stringify(viewModel),
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                type: "POST",           
                success: successFunction,
                error: errorFunction
            });
		}
		
		
		//-----------------------------------------------------------------------------------//
		//-- Utilities 															        ----//
		//---------------------------------------------------------------------------------//
		
		function addChildItems(data, li)
		{
			var source = $("#folderTemplate").html(); 
			var template = Handlebars.compile(source); 
			var s = template(data);
			
			$(li).find("input[name$='open']").val("true");
			$(li).after(s);
		}
		
		function getFileViewModel(li)
		{
			return {
				"path": $(li).find("input:nth-child(1)").val(),
				"name": $(li).find("input:nth-child(2)").val(),
				"file": $(li).find("input:nth-child(3)").val(),
				"open": $(li).find("input:nth-child(4)").val()
			};
		}
		
		function setMenuState()
		{
			var numSelectedVideos = $(".selectedCheckbox:checked").siblings("input[name$='mediaType'][value='Video']").length;
			var numSelectedItems = $(".selectedCheckbox:checked").length;
		
			if(numSelectedVideos > 0)
			{
				$("#moveManually").css("color", "");
				$("#moveFiles").css("color", "");
				moveManuallyEnabled = true;
				moveFilesEnabled = true;
			}
			else
			{
				$("#moveManually").css("color", "grey");
				$("#moveFiles").css("color", "grey");
				moveManuallyEnabled = false;
				moveFilesEnabled = false;
			}
			
			if(numSelectedItems > 0)
			{
				$("#deleteFiles").css("color", "");
				deleteFilesEnabled = true;
			}
			else
			{
				$("#deleteFiles").css("color", "grey");
				deleteFilesEnabled = false;
			}
		}
		
		function dissableInterface()
		{
			uiEnabled = false;
			deleteFilesEnabled = false;
			moveManuallyEnabled = false;
			moveFilesEnabled = false;
			
			$(".selectedCheckbox").attr("disabled", "disabled");
			$("#folderMenu a").css("color", "grey");
			$("li").css("color", "grey");
		}
		
		function enableInterface()
		{
			uiEnabled = true;
			deleteFilesEnabled = true;
			moveManuallyEnabled = true;
			moveFilesEnabled = true;
			
			$(".selectedCheckbox").removeAttr("disabled");
			$("#folderMenu a").css("color", "");
			$("li").css("color", "");
		}
		
		
		/* get the first li that has a checked checkbox and an input indicating it is a file */
		function getNextSelectedFileLi()
		{
			return  $(".selectedCheckbox:checked").siblings("input[name$='file'][value='true']").siblings(".operationTried[value='false']").parent("li").first();
		}
		/* get the first checked li that we have not already tried to move / delete */
		function getNextSelectedLi()
		{
			return $('.selectedCheckbox:checked').siblings(".operationTried[value='false']").parent("li").first();
		}
		function getNextUnopenedFolderLi()
		{
			return $(".folderName").siblings("input[name$='open'][value='false']").parent("li").first();
		}
		function getNextUnopenedFolderLiOfRoot()
		{
			return $(openFromRootLi).find(".folderName").siblings("input[name$='open'][value='false']").parent("li").first();
		}
		function isFolder(li)
		{
			return ($(li).children(".folderName").length > 0);
		}
		function isOpen(li)
		{
			return ($(li).children("input[name$='open'][value='true']").length > 0);
		}
		function isChecked(li)
		{
			return ($(li).children(".selectedCheckbox:checked").length > 0);
		}
		function removeChildren(li)
		{
			$(li).next("li").remove();
		}	
		function hasChildren(li)
		{
			return ($(li).next("li").children("ul").length > 0);
		}
		
	</script>
	
	<script id="folderTemplate" type="text/x-handlebars-template"> 
		<li>
			<ul style="list-style: none">
				{{#files}} 
					<li class="highlightableLi"> 
						<input name="path" type="hidden" value="{{path}}">
						<input name="name" type="hidden" value="{{name}}">
						<input name="file" type="hidden" value="{{#if file}}true{{else}}false{{/if}}">
						<input name="open" type="hidden" value="{{#if open}}true{{else}}false{{/if}}" class="openHiddenInput">
						<input name="mediaType" type="hidden" value="{{mediaType}}">					

						<input type="checkbox" name="selected" class="selectedCheckbox">
					
						{{#if file}}

							{{#ifCond mediaType '==' 'Video'}}
								<img src="/resources/images/movies.png"> {{name}}
							{{/ifCond}}
							
							{{#ifCond mediaType '==' 'Music'}}
								<img src="/resources/images/file_extension_m4b.png"> {{name}}
							{{/ifCond}}

							{{#ifCond mediaType '==' 'Unknown'}}
								<img src="/resources/images/page_white_text.png"> {{name}}
							{{/ifCond}}
							
						{{else}}
							<img src="/resources/images/folder_vertical_open.png"> <span class="folderName">{{name}}</span>
						{{/if}}
				
						<input type="hidden" value="false" class="operationTried" >
					</li>
				{{/files}} 
			</ul>
		</li>	
	</script>	
	
	
	<style>
		.highlightableLi{
			cursor: hand;			
		}
		
	</style>
	
	
	<form:form method="POST" commandName="directory">
		 
		<c:if test="${directory.rootDirMediaType == 'source'}">
			<h2>Source Directory</h2>
		</c:if>
		<c:if test="${directory.rootDirMediaType == 'tv'}">
			<h2>TV Episode Directory</h2>
		</c:if>
		<c:if test="${directory.rootDirMediaType == 'movie'}">
			<h2>Movie Directory</h2>
		</c:if>
		
		<div style="border: solid 1px #F1F1F1; border-radius: 8px;">
			<div style="background-color: #F1F1F1; padding: 3px;height: 20px">
				<form:hidden path="action"/>
		
				<div style="float: right" id="folderMenu">
					<a href="#" id="selectAll" style="font-size: 14px;"> Select All </a><a href="#" id="deselectAll" style="font-size: 14px; display: none;"> Deselect All </a> |
					<a href="#" id="expandAll" style="font-size: 14px"> Expand All </a><a href="#" id="colapseAll" style="font-size: 14px; display: none;"> Colapse All </a> |
					<a href="#" id="deleteFiles">Delete</a> 
					 
					<c:if test="${directory.rootDirMediaType == 'source'}">
						| <a href="#" id="moveManually">Match</a> 
						| <a href="#" id="moveFiles">Move</a>
					</c:if>
					
					<c:if test="${directory.rootDirMediaType == 'tv'}">
						| <!-- <a href="#" id="cleanFiles">Clean</a> --> 
						<select id="cleanOptions">
							<option>Rename Selected Files</option>
							<option>Delete Empty Directories</option>
							<option>Delete Non Media Files</option>
						</select>
										
					</c:if>
					<c:if test="${directory.rootDirMediaType == 'movie'}">
						| <!-- <a href="#" id="cleanFiles">Clean</a>  -->
					</c:if>
				
				</div> 
			</div>
			
			<ul style="list-style: none; padding-left: 0px;">
				<li id="rootLi"> 
					<form:hidden path="path"/>
					<form:hidden path="name"/>
					<form:hidden path="file"/>                           
					<form:hidden path="open"/>
					<img src="/resources/images/folder_vertical_open.png" style="padding-left: 10px;"><c:out value="${directory.path}"/>
				</li>
			</ul>
		</div>
		<br>
		
		<div id="operationErrorDialog" title="Operation error">
		  <p><span id="operationErrorMessage"></span> Please see <a href="/activity">system activity</a> for further details</p>
		</div>
		
		<div id="unspecifiedErrorDialog" title="Unspecified error">
		  <p>An unspecified error occured communicating with the server. It is possible that the mediabeaver website is down.</p>
		</div>
		
		
		<div id="deleteConfirmationDialog" title="Confirm delete">
		  <p>Are you sure that you wish to delete the selected files. This action cannot be undone.</p>
		</div>
		
	</form:form>
		
<%@include file="includes/footer.jsp"%>









































