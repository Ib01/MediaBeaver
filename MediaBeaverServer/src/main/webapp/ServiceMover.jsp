<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			$(".manualMoveButton").click(function() 
			{
				$("#selectedPath").val($(this).siblings(".sourcePath").val());
				
				$("form:first").attr("action", "/activity/manualMove");
				$("form:first").submit(); 
				
			});
		}); 

	</script>
	
	<h2>System Activity</h2>
  
	<form:form method="POST" commandName="activity" class="formLayout">
		
		
		
	</form:form>
	
	
	
<%@include file="includes/footer.jsp"%>









































