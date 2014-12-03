<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>

<%@include file="includes/header.jsp"%>

	<script type="text/javascript" >
	
		var dontCheck = false;
		var dissabled = false;
		var selectedLi;
	
		$(function ()
		{	

			$(".highlightableLi").mouseover(function() 
			{
				$(this).css("background-color", "#F1F1F1");
			});
			$(".highlightableLi").mouseout(function() 
			{
				$(this).css("background-color", "");
			});
			$(".highlightableLi").click(function() 
			{
				if(!dontCheck)
				{
					$(this).find("input:checkbox").prop(
							'checked', 
							!$(this).find("input:checkbox").is(':checked')
					);
				}
				dontCheck = false;
			});
			
			$(".highlightableLi input:checkbox").click(function() 
			{
				dontCheck = true;
			});
			
			
			$(".folderName").mouseover(function() 
			{
				$(this).css("text-decoration", "underline");
			});
			$(".folderName").mouseout(function() 
			{
				$(this).css("text-decoration", "");
			});
			$(".folderName").click(function() 
			{				
				if($(this).parent("li").find(".openHiddenInput").val() == "true")
					$(this).parent("li").find(".openHiddenInput").val("false");
				else
					$(this).parent("li").find(".openHiddenInput").val("true");
				
				$("form:first").attr("action", "/source");
				$("form:first").submit();
				
				return false;
			});
			
			$("#moveManually").click(function() 
			{	
				if($('.selectedCheckbox:checked').length > 0)
				{
					$("form:first").attr("action", "/source/serviceMove");
					$("form:first").submit();
					return false;
				}
			});
			
			$("#openAll1").click(function() 
			{	
				submitOpenOrSelectAll("open all");
			});

			$("#selectAll1").click(function() 
			{	
				submitOpenOrSelectAll("select all");
			});
			
			
			function submitOpenOrSelectAll(action)
			{
				$("#action").val(action);
				
				$("form:first").attr("action", "/source");
				$("form:first").submit();
			}
			
			$("#deleteFiles").click(function() 
			{	
				$(".operationFailed").val("false");
				
				if($('.selectedCheckbox:checked').length > 0)
				{
					$("#messageBoard").show("slow", function()
					{
						//dissableInterface();
						selectedLi = getNextDeleteableLi();
						var viewModel = getFileViewModel(selectedLi);
						callDelete(viewModel);
					});
				}
			});
			
			$("#moveFiles").click(function() 
			{
				$(".operationFailed").val("false");
				selectedLi = getNextSelectedFileLi();
				
				if(selectedLi.length > 0)
				{
					$("#messageBoard").show("slow", function()
					{
						var vm = getFileViewModel(selectedLi);
						callMove(vm);
					});
				}
					
			});
	
			
			
			
		}); 
		
		
		//---------------------------------------------------------------------------------//
		//---------------------------------------------------------------------------------//
		//---------------------------------------------------------------------------------//
		
		
		function callMove(viewModel)
		{
			 doAjaxCall('/source/moveFile', viewModel, moveSuccess, operationError);
		}
		
		function callDelete(viewModel)
		{
			 doAjaxCall('/source/deleteFile', viewModel, deleteSuccess, operationError);
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
				$(selectedLi).find(".operationFailed").val("true");
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
			
			//show message
			showMessages(data, "deleted");
			
			//delete next selected item
			selectedLi = getNextDeleteableLi();
			if(selectedLi)
			{
				var viewModel = getFileViewModel(selectedLi);
				callDelete(viewModel);
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
		
		function dissableInterface()
		{
			$(".selectedCheckbox").attr("disabled", "disabled");
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
			//TODO: MAKE THIS MORE EFFICIENT
			
			var selectedFileLis = $(".selectedCheckbox:checked").parent("li").find("input[name$='file'][value='true']").parent("li");
			var previouslyUnprocessed = $(selectedFileLis).find(".operationFailed[value='false']").parent("li").first();
			
			return previouslyUnprocessed;
		}
		
		
		/* get next file or folder that does not have a selected parent folder*/
		function getNextDeleteableLi()
		{
			//TODO: MAKE THIS MORE EFFICIENT
			
			var obj;
			$('.selectedCheckbox:checked').each(
				function( index ) 
				{
					var parentFolderSelected = $(this).parent("li").parent("ul").parent("li").prev("li").find(".selectedCheckbox:checked").length;
					
					if(!parentFolderSelected)
					{
						obj = $(this).parent("li");
						return false; //breaks the for loop 
					}
				}
			);
			
			return obj;
		}
		
	</script>
	
	<style>
		.highlightableLi{
			cursor: hand;			
		}
		
		#messageBoard a:link {color:#FFFFFF;text-decoration:underline;}
		#messageBoard a:visited {color:#FFFFFF;text-decoration:underline;}  
		#messageBoard a:hover {color:#FFFFFF;text-decoration:underline;}  
		#messageBoard a:active {color:#FFFFFF;text-decoration:underline;} 
	</style>
	
	
	<form:form method="POST" commandName="directory">
		<h2>Source Directory</h2>
		
		<div style="border: solid 1px #F1F1F1">
			<div style="background-color: #F1F1F1; padding: 3px;">
				<form:checkbox path="openAll" label="Expand All" /> |
				<form:checkbox path="selectAll" label="Select All"/>
				<form:hidden path="action"/>
				
				<div style="float: right">
					<a href="#" id="deleteFiles">Delete</a> | 
					<a href="#" id="moveManually">Move Manually</a> |
					<a href="#" id="moveFiles">Move Automatically</a> 
					
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









































