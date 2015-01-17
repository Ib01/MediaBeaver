<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			$(".MatchEpisode").click(function() 
			{
				$("#selectedEpisodeNumber").val($(this).siblings(".EpisodeNumber").val());
				
				$("form:first").attr("action", "/mediaMatcher/SelectEpisode_Select");
				$("form:first").submit();
				
			});
	
		}); 
		
		//selectSeries
		
	</script>
	
	<h2>Select Episode</h2>
	
	<form:form method="POST" commandName="SelectEpisode" class="formLayout">
		<form:hidden path="selectedEpisodeNumber"/>
		<form:hidden path="selectedFile"/>
	
		<p>Match the file below to an episode:</p>	
		<div style="background-color: #F1F1F1; padding: 3px;">
			<ul class="documentList" style="margin:0px">
				<li>
					<c:out value="${SelectEpisode.selectedFile}"></c:out>
				</li>
			</ul>
		</div>
	
		
		
		<c:forEach items="${SelectEpisode.episodes}" var="episode" varStatus="i">
			
			<div class="seriesItemContainer" style="width: 758px; padding: 5px; margin-bottom: 10px; margin-left: auto; margin-right: auto;">
			
				<input type="hidden" class="EpisodeNumber" value="${episode.episodeNumber}"/>
				<a href="#" style="float:right;" class="MatchEpisode">Match this episode</a>
			
				<h2 style="font-weight: bold; font-size: 18px; font-style: italic; margin-top: 0px">
					Episode ${episode.episodeNumber}: ${episode.episodeName}
				</h2>
				
				<c:if test="${empty episode.fileName}">
					<img src="/resources/images/ImageNotSelected.png" style="float:left; margin-right:10px; width: 758px; height: 140px">
				</c:if>
				<c:if test="${!empty episode.fileName}">
					<image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/${episode.fileName}" style="float:left; margin-right:10px; height: 150px">
				</c:if>
				
				<p style="font-style: italic; margin-top: 0px; height: 150px; overflow-y:auto;">
					${episode.overview}
				</p>
			
			</div>
			
		</c:forEach>
		
		
		<br>
		<a class="mainButton" href="/mediaMatcher/matchFiles" id="Previous">Cancel</a>
		<br>
	</form:form> 
	
	
	
<%@include file="includes/footer.jsp"%>









































