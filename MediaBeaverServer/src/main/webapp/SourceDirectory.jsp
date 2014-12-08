<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<%@include file="includes/header.jsp"%>

	<script type="text/javascript" >
	
		var uiEnabled = true;
		var selectedLi;
		var openAllRoot;
	
		$(function ()
		{	

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
			});
				
			$( "body" ).delegate( ".highlightableLi input:checkbox", "click", function()  
			{
				e.stopPropagation();
			});
				
			$("#moveManually").click(function() 
			{
				if(!uiEnabled) return;
				
				if($('.selectedCheckbox:checked').length > 0)
				{
					$("form:first").attr("action", "/source/serviceMove");
					$("form:first").submit();
					return false;
				}
			});
			
			$("#deleteFiles").click(function() 
			{	
				if(!uiEnabled) return;
				
				$(".operationTried").val("false");
				selectedLi = getNextSelectedLi();
				
				if(selectedLi.length > 0)
				{
					$("#messageBoard").show("slow", function()
					{
						var viewModel = getFileViewModel(selectedLi);
						dissableInterface();
						callDelete(viewModel);
					});
				}
			});
			
			$("#moveFiles").click(function() 
			{
				if(!uiEnabled) return;
				
				$(".operationTried").val("false");
				selectedLi = getNextSelectedFileLi();
				
				if(selectedLi.length > 0)
				{
					$("#messageBoard").show("slow", function()
					{
						var vm = getFileViewModel(selectedLi);
						dissableInterface();
						callMove(vm);
					});
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
				
				e.stopPropagation();
			});
			
			$("#selectAll").click(function(e) 
			{	
				if(!uiEnabled) return;
				
				$("input:checkbox").prop('checked', true);
				
				$("#selectAll").hide();
				$("#deselectAll").show();
			});
			
			$("#deselectAll").click(function(e) 
			{	
				if(!uiEnabled) return;
				
				$("input:checkbox").prop('checked', false);
				
				$("#selectAll").show();
				$("#deselectAll").hide();
			});
			
		}); 
		
		
		//---------------------------------------------------------------------------------//
		//---------------------------------------------------------------------------------//
		//---------------------------------------------------------------------------------//
	
		function dissableInterface()
		{
			uiEnabled = false;
			$(".selectedCheckbox").attr("disabled", "disabled");
			$("#folderMenu a").css("color", "grey");
			$("li").css("color", "grey");
		}
		
		function enableInterface()
		{
			uiEnabled = true;
			$(".selectedCheckbox").removeAttr("disabled");
			$("#folderMenu a").css("color", "");
			$("li").css("color", "");
		}
		
		function callOpenFromRoot(viewModel)
		{
			 doAjaxCall('/source/openFolder', viewModel, openFromRootSuccess, operationError);
		}
		
		function callOpenAll(viewModel)
		{
			 doAjaxCall('/source/openFolder', viewModel, openAllSuccess, operationError);
		}
		
		function callOpen(viewModel)
		{
			 doAjaxCall('/source/openFolder', viewModel, openSuccess, operationError);
		}
		
		function callMove(viewModel)
		{
			 doAjaxCall('/source/moveFile', viewModel, moveSuccess, operationError);
		}
		
		function callDelete(viewModel)
		{
			 doAjaxCall('/source/deleteFile', viewModel, deleteSuccess, operationError);
		}
		
		function openFromRootSuccess(data)
		{
			var source = $("#folderTemplate").html(); 
			var template = Handlebars.compile(source); 
			var s = template(data);
			
			$(selectedLi).find("input[name$='open']").val("true");
			$(selectedLi).after(s);
			
			selectedLi =  getNextUnopenedFolderLiOfRoot();
			if(selectedLi.length > 0)
			{
				var vm = getFileViewModel(selectedLi);
				callOpenAll(vm);
			}
			else
			{
				enableInterface();
			}
		}
		
		function openAllSuccess(data)
		{
			var source = $("#folderTemplate").html(); 
			var template = Handlebars.compile(source); 
			var s = template(data);
			
			$(selectedLi).find("input[name$='open']").val("true");
			$(selectedLi).after(s);
			
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
			}
		}
		
		function openSuccess(data)
		{
			var source = $("#folderTemplate").html(); 
			var template = Handlebars.compile(source); 
			var s = template(data);
			
			$(selectedLi).find("input[name$='open']").val("true");
			$(selectedLi).after(s);
			enableInterface();
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
			}
			
			//show message
			showMessages(data, "moved");
			
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
			}
			
			//show message
			showMessages(data, "deleted");
			
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
			}
		}
		
		function showMessages(data, operation)
		{
			if(data.operationSuccess)
			{
				$("#messageBoard  > div").append("<a href=\"/activity\">" + data.path + "</a> <span style=\"\">"+operation+" successfully<span></br>");
			}
			else
			{
				$("#messageBoard  > div").append("<a href=\"/activity\">" + data.path + "</a> <span style=\"\">"+operation+" unsuccessfully<span></br>");
			}
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
		
		function operationError(data, status, er)
		{
			alert("error");
		}

		function getFileViewModel(li)
		{
			return {
				"path": $(li).find("input:nth-child(1)").val(),
				"name": $(li).find("input:nth-child(2)").val(),
				"file": $(li).find("input:nth-child(3)").val(),
				"open": $(li).find("input:nth-child(4)").val()
			};
			
			//"selected": $(checkbox).parent("li").find("input:nth-child(5)").val()
		}
		
		/* get the first li that has a checked checkbox and an input indicating it is a file */
		function getNextSelectedFileLi()
		{
			return  $(".selectedCheckbox:checked").siblings("input[name$='file'][value='true']").siblings(".operationTried[value='false']").parent("li").first();
		}
		
		/* get next file or folder that does not have a selected parent folder*/
		function getNextSelectedLi()
		{
			//get the first checked li that we have not already tried to move / delete 
			return $('.selectedCheckbox:checked').siblings(".operationTried[value='false']").parent("li").first();
		}
		
		function getNextUnopenedFolderLi()
		{
			return $(".folderName").siblings("input[name$='open'][value='false']").parent("li").first();
		}
		
		function getNextUnopenedFolderLiOfRoot()
		{
			return $(openAllRoot).find(".folderName").siblings("input[name$='open'][value='false']").parent("li").first();
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
					
						<input type="checkbox" name="selected" class="selectedCheckbox">
					
						{{#if file}}
							<img src="/resources/images/document_24.png"> {{name}}
						{{else}}
							<img src="/resources/images/folder_24.png"> <span class="folderName">{{name}}</span>
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
		<h2>Source Directory</h2>
		
		<div style="border: solid 1px #F1F1F1">
			<div style="background-color: #F1F1F1; padding: 3px;height: 20px">
				<form:hidden path="action"/>
				
				<div style="float: right" id="folderMenu">
					<a href="#" id="selectAll" style="font-size: 14px;"> Select All </a><a href="#" id="deselectAll" style="font-size: 14px; display: none;"> Deselect All </a> |
					<a href="#" id="expandAll" style="font-size: 14px"> Expand All </a><a href="#" id="colapseAll" style="font-size: 14px; display: none;"> Colapse All </a> |
					<a href="#" id="deleteFiles">Delete</a> | 
					<a href="#" id="moveManually">Match and Move</a> |
					<a href="#" id="moveFiles">Move</a> 
				</div> 
			</div>
			
			<ul style="list-style: none; padding-left: 0px;">
				<li> 
					<form:hidden path="path"/>
					<form:hidden path="name"/>
					<form:hidden path="file"/>
					<form:hidden path="open"/>
					<img src="/resources/images/folder_24.png" style="padding-left: 10px;"><c:out value="${directory.path}"/> 
				</li>
				<li>
				 
				 	<!-- have to pass directory in request scope as it is an object.--> 
					<c:set var="CurrentFolder" value="${directory}" scope="request"/>
					<jsp:include page="includes/Folder.jsp" >
					    <jsp:param name="ReferenceString" value="files" />
					</jsp:include>
				
				</ul>
			</ul>
		</div>
		
	
		<div style="border: 1px solid #F1F1F1; display:none; " id="messageBoard">
			<div style="background-color: #919191;  margin: 3px; color: white; padding: 5px; ">
				
				
			</div>
		</div>
		
		<br>
		
	</form:form>
		
<%@include file="includes/footer.jsp"%>









































