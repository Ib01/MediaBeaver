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
					$("form:first").attr("action", "/configuration/save");
					$("form:first").submit();
				}
			});
			
		}); 

	</script>
	
	<h2>Manually resolve media files using a media service</h2>
  
	<form:form method="POST" commandName="ViewModel" class="formLayout">
		
		<!-- <div class="roundedPanel"> -->
			<p>You have chosen to resolve the following Media files:</p>
			
			<ul>
				<c:forEach items="${ViewModel.filesToResolve}" var="path" varStatus="i">
				 	<li><c:out value="${path}"></c:out></li>
				</c:forEach>
			</ul>
		<!-- </div> -->
		
		
		<!-- <div class="roundedPanel"> -->
			<form:label path="selectedMediaType">These files constitute</form:label>
			
			<form:select path="selectedMediaType">
				<form:option value="A movie which may or may not span multiple files"></form:option>
				<form:option value="1 or more Tv Episodes that are a part of the same Tv Series"></form:option>
			</form:select>
			
			<br>
		<!-- </div> -->
		
		
		<image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/episodes/121361/4161693.jpg">
		
		
		<br>
		<a class="mainButton" href="#" id="Next">Next</a><a class="mainButton" href="#" id="Cancel">Cancel</a>
		<br>
	</form:form>
	
	
	
<%@include file="includes/footer.jsp"%>









































