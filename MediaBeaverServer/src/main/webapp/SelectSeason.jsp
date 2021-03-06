<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			
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
					var number = $(".itemRadio:checked").siblings(".seasonNumber").val();
					$("#selectedSeasonNumber").val(number);
					
					$("form:first").attr("action", "/mediaMatcher/matchFiles");
					$("form:first").submit();
				}
			});
			
			$(".seasonItemContainer").mouseover(function()  
			{
				$(this).css("background-color", "#F1F1F1");
			});
			
			$(".seasonItemContainer").mouseout(function()  
			{
				$(this).css("background-color", "");
			});
			
			$(".seasonItemContainer").click(function()  
			{
				$("#Next").attr("class", "mainButton");
				$(this).find(".itemRadio").prop('checked', true);
			});
			
			
			var seasonNumber = $("#selectedSeasonNumber").val();
			if(seasonNumber > 0)
			{
				$(".seasonNumber[value='"+seasonNumber+"']").siblings(".itemRadio").prop('checked', true);
				$("#Next").attr("class", "mainButton");
			} 
	
		}); 
		
		
	</script>
	
	<h2>Select Season</h2>
  	<c:set var="filesToResolve" value="${filesToResolve}" scope="request"/>
	<jsp:include page="includes/FilesToResolve.jsp" />
  	<p>Select a TV Series Season that these files belong to</p>
  	
	<form:form method="POST" commandName="SelectSeason" class="formLayout">
		
	
		<form:hidden path="selectedSeasonNumber"/>
		
		<c:forEach items="${SelectSeason.banners}" var="banner" varStatus="i">
			
			<div class="seasonItemContainer" style="width: 758px; padding: 5px; margin-bottom: 10px; margin-left: auto; margin-right: auto;">
			
				<h2 style="font-weight: bold; font-size: 18px; font-style: italic; margin-top: 0px">
					<input type="radio" style="margin: 0px;" class="itemRadio" name="selectItem" >
					Season <c:out value="${i.index + 1}"></c:out>
					<input type="hidden" class="seasonNumber" value="${i.index + 1}" />
				</h2>
				
				<c:if test="${empty banner.bannerPath}">
					<img src="/resources/images/ImageNotSelected.png" style="width: 758px; height: 140px">
				</c:if>
				<c:if test="${!empty banner.bannerPath}">
					<image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/${banner.bannerPath}" style="">
				</c:if>
						
				<br style="clear:both;"/>
			</div>
			
		</c:forEach>
		
		
		<br>
		<a class="dissabledMainButton" href="#" id="Next">Next</a><a class="mainButton" href="/mediaMatcher/searchTvSeries" id="Previous">Previous</a>
		<br>
	</form:form>
	
	
	
<%@include file="includes/footer.jsp"%>









































