<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

	<myTags:ErrorDisplay modelObject="configuration"/>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			$("#Next").click(function() 
			{  
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/mediaMatcher/searchMedia");
					$("form:first").submit();
				}
			});
			
		}); 

	</script>
	
	<h2>Select Media Type</h2>
  
	<form:form method="POST" commandName="SelectMediaType" class="formLayout">
		
		<c:set var="filesToResolve" value="${filesToResolve}" scope="request"/>
		<jsp:include page="includes/FilesToResolve.jsp" />
		<br>
	
	
		<form:label path="selectedMediaType">These files constitute</form:label>
		<form:select path="selectedMediaType">
			<form:option value="A movie which may or may not span multiple files"></form:option>
			<form:option value="1 or more Tv Episodes that are a part of the same Tv Series"></form:option>
		</form:select>
		<br>
	
		<!-- <image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/episodes/121361/4161693.jpg"> -->
		
		
		<br>
		<a class="mainButton" href="#" id="Next">Next</a><a class="mainButton" href="/source" id="Cancel">Cancel</a>
		<br>
	</form:form>
	
	
	
<%@include file="includes/footer.jsp"%>









































