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
					$("form:first").attr("action", "/serviceMover/searchTvSeries_Search");
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
					
					$("form:first").attr("action", "/serviceMover/searchTvSeries_Next");
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
			} 
	
		}); 
		
		//selectSeries
		
	</script>
	
	<h2>Enter Series Name</h2>
	<c:set var="filesToResolve" value="${filesToResolve}" scope="request"/>
	<jsp:include page="includes/FilesToResolve.jsp" />
	<p>Search for a TV Series that these files belong to</p>
	
	
	<form:form method="POST" commandName="SearchSeries" class="formLayout">
		<form:hidden path="selectedSeriesId"/>
		<form:label path="searchText">TV Series Name</form:label>
		<form:input path="searchText"/><a class="button" href="#" id="Search">Search</a>
		<br>
		<br>

		<c:forEach items="${SearchSeries.searchResults}" var="result" varStatus="i">
			<div class="seriesItemContainer">
				 
				<c:if test="${empty result.banner}">
					<img src="/resources/images/ImageNotSelected.png" style="float:right; margin: 3px; width: 758px; height: 140px">
				</c:if>
				<c:if test="${!empty result.banner}">
					<image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/${result.banner}" style="float:right; margin: 3px;">
				</c:if>
				
				<p style="font-weight: bold; font-size: 18px; font-style: italic;margin-top: 0px">
					<input type="radio" style="margin: 0px;" class="itemRadio" name="selectItem" >
					
					<c:out value="${result.seriesName}"></c:out>
					<input type="hidden" class="itemId" value="${result.id}" />
				</p>
				
				<p style="font-style: italic; margin: 0px">First aired: <c:out value="${result.firstAired}"></c:out></p>
				<p style="font-style: italic; margin: 0px">Network :<c:out value="${result.network}"></c:out></p>
				<p style="font-style: italic; margin: 0px">IMDB Id :<c:out value="${result.imdbId}"></c:out></p>
				
				<br />
				<p style="font-style: italic;">Overview</p>
				<p style="font-style: italic; margin-top: 0px"><c:out value="${result.overview}"></c:out></p>
			</div>
			
			<br style="clear:both;"/>
			<br style="clear:both;"/>
			<br style="clear:both;"/>
		</c:forEach>
			
		<br>
		<a class="dissabledMainButton" href="#" id="Next">Next</a><a class="mainButton" href="/serviceMover" id="Previous">Previous</a>
		<br>
	</form:form>
	
	
	
<%@include file="includes/footer.jsp"%>









































