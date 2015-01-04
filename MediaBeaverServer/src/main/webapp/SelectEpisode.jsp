<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			/* $("#Search").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/serviceMover/searchSeries");
					$("form:first").submit();
				}
			});
			
			$(".itemCheckbox").click(function() 
			{
				$("#Next").attr("class", "dissabledMainButton");
				if($(".itemCheckbox:checked").length == 1)
				{
					$("#Next").attr("class", "mainButton");
				}
				
			});
			
			
			 */
			
			/* $("#Next").click(function(e) 
			{
				if($("#Next").attr("class") == "mainButton")
				{
					var number = $(".itemRadio:checked").siblings(".seasonNumber").val();
					$("#selectedSeasonNumber").val(number);
					
					$("form:first").attr("action", "/serviceMover/selectSeries");
					$("form:first").submit(); 
				}
			}); */
	
		}); 
		
		//selectSeries
		
	</script>
	
	<h2>Select Episode</h2>
  	<c:set var="filesToResolve" value="${filesToResolve}" scope="request"/>
	<jsp:include page="includes/FilesToResolve.jsp" />
	<p>Match the above files to the TV Episodes below</p>
	
	
	<form:form method="POST" commandName="SelectEpisode" class="formLayout">
		<form:hidden path="selectedEpisodeId"/>
		
		<c:forEach items="${SelectEpisode.episodes}" var="episodes" varStatus="i">
			
			<c:if test="${empty episodes.fileName}">
				<img src="/resources/images/ImageNotSelected.png" style="left; margin: 3px; width: 758px; height: 140px">
			</c:if>
			<c:if test="${!empty episodes.fileName}">
				<image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/${episodes.fileName}" style="float:left; margin: 3px; height: 150px">
			</c:if>
			
			
			
			
			<p style="font-weight: bold; font-size: 18px; font-style: italic;margin-top: 0px">
				Episode ${episodes.episodeNumber}: ${episodes.episodeName}
			</p>
			<p style="font-style: italic;margin-top: 0px">
				${episodes.overview}
			</p>
			<br style="clear:both;"/>	
			
			
	
	<%-- 		<div>
				<p style="font-weight: bold; font-size: 18px; font-style: italic;margin-top: 0px">
					<input type="radio" style="margin: 0px;" class="itemRadio" name="selectItem" >
					Season <c:out value="${i.index + 1}"></c:out>
					<input type="hidden" class="seasonNumber" value="${i.index + 1}" />
				</P>
		
				<image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/${banner.bannerPath}" style="float:right; margin: 3px;">
				<br style="clear:both;"/>
			</div>
	 --%>		
		</c:forEach>
		
		
		<br>
		<a class="dissabledMainButton" href="#" id="Next">Next</a><a class="mainButton" href="/serviceMover/selectEpisode_Back" id="Previous">Previous</a>
		<br>
	</form:form> 
	
	
	
<%@include file="includes/footer.jsp"%>









































