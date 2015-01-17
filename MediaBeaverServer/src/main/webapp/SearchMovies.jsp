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
					$("form:first").attr("action", "/mediaMatcher/SearchMovies_Search");
					$("form:first").submit();
				}
			});
			
			/* $("#Search").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/mediaMatcher/searchTvSeries_Search");
					$("form:first").submit();
				}
			});
			
			$(".itemRadio").click(function() 
			{
				$("#Next").attr("class", "dissabledMainButton");
				if($(".itemRadio:checked").length == 1)
				{
					$("#Next").attr("class", "mainButton");
				}
				
			});
			
			$("#Next").click(function(e) 
			{
				if($("#Next").attr("class") == "mainButton")
				{
					var id = $(".itemRadio:checked").siblings(".itemId").val();
					$("#selectedSeriesId").val(id);
					
					$("form:first").attr("action", "/mediaMatcher/selectSeason");
					$("form:first").submit();
				}
			});
			
			$(".seriesItemContainer").mouseover(function()  
			{
				$(this).css("background-color", "#F1F1F1");
			});
			
			$(".seriesItemContainer").mouseout(function()  
			{
				$(this).css("background-color", "");
			});
			
			$(".seriesItemContainer").click(function()  
			{
				$("#Next").attr("class", "mainButton");
				$(this).find(".itemRadio").prop('checked', true);
			});
			
			var seriesId = $("#selectedSeriesId").val();
			if(seriesId > 0)
			{
				$(".itemId[value='"+seriesId+"']").siblings(".itemRadio").prop('checked', true);
				$("#Next").attr("class", "mainButton");
			}  */
	
		}); 
		
	</script>
	
	<h2>Search for Movie</h2>
	<c:set var="filesToResolve" value="${filesToResolve}" scope="request"/>
	<jsp:include page="includes/FilesToResolve.jsp" />
	<p>Search for a Movie that these files belong to:</p>
	
	
	<form:form method="POST" commandName="SearchMovies" class="formLayout">
		<%-- <form:hidden path="selectedSeriesId"/> --%>
		<form:label path="movieName">Movie Name</form:label>
		<form:input path="movieName"/> 
		<br>
		<form:label path="movieYear">Movie Year</form:label>
		<form:input path="movieYear"/>
		
		<a class="button" href="#" id="Search">Search</a>
		<br>
		<br>

<%-- 		<c:forEach items="${SearchSeries.searchResults}" var="result" varStatus="i">
			
			<div class="seriesItemContainer" style="width: 758px; padding: 5px; margin-bottom: 10px; margin-left: auto; margin-right: auto;">
				
				<div style="float:right; font-style: italic; font-weight: bold;">
					<span style="margin-bottom: 0px">First aired: <c:out value="${result.firstAired}"></c:out></span> | 
					<span style="margin: 0px">Network :<c:out value="${result.network}"></c:out></span> | 
					<span style="margin: 0px">IMDB Id :<c:out value="${result.imdbId}"></c:out></span>
				</div>
				
				<h2 style="font-weight: bold; font-size: 18px; font-style: italic; margin-top: 0px">
					<input type="radio" style="margin: 0px;" class="itemRadio" name="selectItem" >
					
					<c:out value="${result.seriesName}"></c:out>
					<input type="hidden" class="itemId" value="${result.id}" />
				</h2>
				
				 
				<c:if test="${empty result.banner}">
					<img src="/resources/images/ImageNotSelected.png" style="width: 758px; height: 140px">
				</c:if>
				<c:if test="${!empty result.banner}">
					<image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/${result.banner}" style="">
				</c:if>
				
				<p style="font-style: italic;">Overview</p>
				<p style="font-style: italic; margin-top: 0px"><c:out value="${result.overview}"></c:out></p>
			</div>
			
		
		</c:forEach> --%>
			
		<br>
		<a class="dissabledMainButton" href="#" id="Next">Next</a><a class="mainButton" href="/mediaMatcher" id="Previous">Previous</a>
		<br>
	</form:form>
	
	
	
<%@include file="includes/footer.jsp"%>









































