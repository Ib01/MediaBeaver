<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="includes/header.jsp"%>
	
	<script type="text/javascript" >
	
	
		$(function ()
		{	
			/* $("#addExpression").click(function() 
			{
				submitRegExSelectorChange("/config/addRegExSelector", this);
			});
			*/
			
			
			$(".deleteConfig").click(function() 
			{
				var id = $(this).parent().find("#configId").val();
				
				alert(id);
				
				getAjax("/configList/deleteConfig", {"id": id}, deleteSuccess);
				
			}); 
			
			
			
		});
		
		
		function deleteSuccess(data)
		{
			alert("adf");
		}
		
		
		
		
		
		/* function submitItem(url, caller)
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
		 */
		
	</script>
	
	
	<h2>Media Configuration Items</h2>
	
	<form:form method="POST" action="/config/save" commandName="configList"
		class="formLayout">
		
		
		<div class="shadowBox">
			<div class="shadowBoxHeader">Configuration Items 
		   		<span style="float: right;" class="shadowBoxHelp">
			   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
			   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
		   		</span>
	   		</div>
		</div>
		<br />
		
		<div class="shadowBox">
		
			<table style="width:100%">
				<tr>
					<td align="left" width="350px" valign="top">Description</td>
					<td align="right" width="80px" valign="top">Action</td>
				</tr>
			
				<c:forEach items="${configList}" var="config" varStatus="i">
					<tr>
						<td align="left" valign="top"><c:out value="${config.description}" /></td>
						<td align="right" valign="top">
							<a href="/config?id=${config.id}" >edit</a> | 
							<a href="#" class="deleteConfig">delete</a><input type="hidden" id="configId" value="${config.id}" />
						</td>
					</tr>
				
				</c:forEach>
		    </table>
			
		</div>
		
		
		<input type="button" value="Add New Media Config" onclick="window.location.replace('/config');" style="width: 150"/>
		
	</form:form> 
	

<%@include file="includes/footer.jsp"%>












































