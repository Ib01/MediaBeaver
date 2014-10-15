<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>


	<script type="text/javascript" >
	
		$(function ()
		{	
			/* $("#Save").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/configuration/save");
					$("form:first").submit();
				}
			}); */
			
			
		}); 

		
	</script>
	
	<h2>Source Directory</h2>
  
	<form:form method="POST" commandName="configuration" id="configForm" class="formLayout">
		
		<c:forEach items="${files}" var="file" varStatus="i">
		
			-- <c:out value="${file.path}" /> <br />
		
		</c:forEach>
		
	</form:form>


		
<%@include file="includes/footer.jsp"%>









































