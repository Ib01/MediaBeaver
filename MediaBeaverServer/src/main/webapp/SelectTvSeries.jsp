<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			$("#Next").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/serviceMover/searchSeries");
					$("form:first").submit();
				}
			});
			
		}); 

	</script>
	
	<h2>Enter Series Name</h2>
  
	<form:form method="POST" commandName="SelectSeries" class="formLayout">
		
	
		<p>You have chosen to resolve the following Media files:</p>
		
		<ul class="documentList">
			<c:forEach items="${filesToResolve}" var="path" varStatus="i">
			 	<li><c:out value="${path}"></c:out></li>
			</c:forEach>
		</ul>
	
		
		<form:label path="series">TV Series Name</form:label>
		<form:input path="series"/>
		<br>
		
		
		
		<!-- <image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/episodes/121361/4161693.jpg"> -->
		
		
		<br>
		<a class="mainButton" href="#" id="Next">Next</a><a class="mainButton" href="/serviceMover" id="Previous">Previous</a>
		<br>
	</form:form>
	
	
	
<%@include file="includes/footer.jsp"%>









































