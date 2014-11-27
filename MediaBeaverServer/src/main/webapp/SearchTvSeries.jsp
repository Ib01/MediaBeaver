<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			$("#Search").click(function() 
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
  
	<form:form method="POST" commandName="SearchSeries" class="formLayout">

		<c:set var="filesToResolve" value="${filesToResolve}" scope="request"/>
		<jsp:include page="includes/FilesToResolve.jsp" />
		<br>
	
		
		<form:label path="searchText">TV Series Name</form:label>
		<form:input path="searchText"/><a class="button" href="#" id="Search">Search</a>
		<br>
		<br>
		
		

		
		<c:forEach items="${SearchSeries.searchResults}" var="result" varStatus="i">

			
			<image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/${result.banner}" style="float:right; margin: 3px;">
			<p style="font-weight: bold; font-size: 18px; font-style: italic;margin-top: 0px"><form:checkbox path="" value="" style="margin: 0px;"/> 
			<c:out value="${result.seriesName}"></c:out></p>
			<p style="font-style: italic; margin: 0px">First aired: <c:out value="${result.firstAired}"></c:out></p>
			<p style="font-style: italic; margin: 0px">Network :<c:out value="${result.network}"></c:out></p>
			<p style="font-style: italic; margin: 0px">IMDB Id :<c:out value="${result.imdbId}"></c:out></p>
			
			<br />
			<p style="font-style: italic;">Overview</p>
			<p style="font-style: italic; margin-top: 0px"><c:out value="${result.overview}"></c:out></p>
			
			
			
			<br style="clear:both;"/>
			
			
			
			
			<br style="clear:both;"/>
			<br style="clear:both;"/>
			
			<%-- <table>
				<tr>
					<td><image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/${result.banner}"></td>
				</tr>
			</table>
			 --%>
		
		
		</c:forEach>
		
		<!-- searchResults -->
		
		
		
		
		
<!-- http://www.thetvdb.com/api/FA86CE5B6769E616/series/121361/banners.xml 
		
http://www.thetvdb.com/banners/episodes/121361/4161693.jpg -->

		
		<!-- <image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/episodes/121361/4161693.jpg"> -->
		
		
		<br>
		<a class="mainButton" href="#" id="Next">Next</a><a class="mainButton" href="/serviceMover" id="Previous">Previous</a>
		<br>
	</form:form>
	
	
	
<%@include file="includes/footer.jsp"%>









































