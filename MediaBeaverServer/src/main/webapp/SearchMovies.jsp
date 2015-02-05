<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="includes/header.jsp"%>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			
			$("#Search").click(function() 
			{
				$("#currentPage").val("1");
				$("form:first").attr("action", "/mediaMatcher/SearchMovies_Search");
				$("form:first").submit();
			});
			
			$("#nextRecord").click(function() 
			{
				var currentPage = parseInt($("#currentPage").val());
				var totalPages = parseInt($("#totalPages").val());
				
				if(currentPage == totalPages)
					return;
				
				$("#currentPage").val(++currentPage);
				$("form:first").attr("action", "/mediaMatcher/SearchMovies_Search");
				$("form:first").submit();			
			});
			
			$("#previousRecord").click(function() 
			{
				var currentPage = parseInt($("#currentPage").val());
				var totalPages = parseInt($("#totalPages").val());
				
				if(currentPage == 1)
					return;
				
				$("#currentPage").val(--currentPage);
				$("form:first").attr("action", "/mediaMatcher/SearchMovies_Search");
				$("form:first").submit();				
			});
			
			$("#Next").click(function() 
			{
				if($(".itemRadio:checked").length == 1)
				{
					$("#Next").attr("class", "dissabledMainButton");
					var id = $(".itemRadio:checked").siblings(".itemId").val();
					$("#selectedMovieId").val(id);
					
					$("form:first").attr("action", "/mediaMatcher/SearchMovies_Select");
					$("form:first").submit();
				}
			});
			
			
			$(".movieContainer").mouseover(function()  
			{
				$(this).css("background-color", "#F1F1F1");
			});
			
			$(".movieContainer").mouseout(function()  
			{
				$(this).css("background-color", "");
			});
			
			$(".movieContainer").click(function()  
			{
				$("#Next").attr("class", "mainButton");
				$(this).find(".itemRadio").prop('checked', true);
			});
			
			
		}); 
		
	</script>
	
	<h2>Search for Movie</h2>
	<c:set var="filesToResolve" value="${filesToResolve}" scope="request"/>
	<jsp:include page="includes/FilesToResolve.jsp" />
	<p>Search for a Movie that these files belong to:</p>
	
	
	<form:form method="POST" commandName="SearchMovies" class="formLayout">
		<form:hidden path="selectedMovieId"/>
		
		<form:label path="movieName">Movie Name</form:label>
		<form:input path="movieName"/> 
		<br>
		<form:label path="movieYear">Movie Year</form:label>
		<form:input path="movieYear"/>
		
		<a class="button" href="#" id="Search">Search</a>
		<br>
		<br>

		<c:forEach items="${SearchMovies.searchResults}" var="movie" varStatus="i">
			
			<div class="movieContainer" style="width: 758px; padding: 5px; margin-bottom: 10px; margin-left: auto; margin-right: auto;">
			
				<c:if test="${empty movie.posterPath}">
					<img src="/resources/images/ImageNotSelected.png" style="width: 185px; height:278px; margin-right:10px; float: left;">
				</c:if>
				<c:if test="${!empty movie.posterPath}">
					<image src="${SearchMovies.baseImageUrl}${movie.posterPath}" style=" float: left; margin-right:10px;">
				</c:if>
			
				<h2 style="font-weight: bold; font-size: 18px; font-style: italic; margin-top: 0px">
					<input type="radio" style="margin: 0px;" class="itemRadio" name="selectItem" >
					
					<c:out value="${movie.title}"></c:out> (<c:out value="${movie.releaseDate}"></c:out>)
					<input type="hidden" class="itemId" value="${movie.id}" />
				</h2>
				
				<p style="font-style: italic;">Overview</p>
				<p style="font-style: italic; margin-top: 0px; height: 185px; overflow-y:auto;">
					<c:out value="${movie.overview}"></c:out>
				</p>
			
				<br style="clear:both;"/>
				
			</div>
			
		
		</c:forEach>
		
		<form:hidden path="currentPage"/>
		<form:hidden path="totalPages"/>
		
		<c:if test="${fn:length(SearchMovies.searchResults) gt 1}">
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<td>
						<a href="#" id="previousRecord" style="font-size: 36px; font-weight: bold; vertical-align:bottom; width: 50px">&#8656; </a>
					</td>
					<td style="vertical-align: middle; font-style: italic;">
						Showing page <c:out value="${SearchMovies.currentPage}"></c:out> 
						of <c:out value="${SearchMovies.totalPages}"></c:out>
					</td>
					<td>
						<a href="#" id="nextRecord" style="font-size: 36px; font-weight: bold; vertical-align:bottom; width: 50px"> &#8658;</a>
					</td>
				</tr>
			</table>
		</c:if>
			
		<br>
		<%-- <a class="dissabledMainButton" href="#" id="Next">
			<c:if test="${fn:length(filesToResolve) gt 1}">
				Next	
			</c:if>
			<c:if test="${fn:length(filesToResolve) == 1}">
				Finish	
			</c:if>
		</a><a class="mainButton" href="/mediaMatcher" id="Previous">Previous</a> --%>
		
		<a class="dissabledMainButton" href="#" id="Next">Finish</a><a class="mainButton" href="/mediaMatcher" id="Previous">Previous</a>
		
		
		<br>
	</form:form>
	
	
	
<%@include file="includes/footer.jsp"%>









































