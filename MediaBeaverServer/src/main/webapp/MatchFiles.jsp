<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

 	<script type="text/javascript" >
	
		$(function ()
		{	
			$(".SelectEpisode").click(function() 
			{
				selectEpisode(this);
			});
			$(".ChangeEpisode").click(function() 
			{
				selectEpisode(this);
			});
		
			$("#Finish").click(function() 
			{
				$("#Finish").attr("class", "dissabledMainButton");
				return ($(".SelectEpisode").length == 0);
			});
			
			
			
			if($(".SelectEpisode").length == 0)
			{
				$("#Finish").attr("class", "mainButton");
			}
			
		}); 
		
		function selectEpisode(caller)
		{
			$("#selectedFile").val($(caller).siblings(".FilePath").val());
			
			$("form:first").attr("action", "/mediaMatcher/SelectEpisode");
			$("form:first").submit();
		}
		
		
		
		

	</script>
	
	<h2>Match files to episodes</h2>
	<p>Match each file to an episode:</p>
	
	<form:form method="POST" commandName="MatchFiles" class="formLayout">
		
		<input type="hidden" name="selectedFile" id="selectedFile"/>
	
	
		<c:forEach items="${MatchFiles.matches}" var="match" varStatus="i">
			<div>
			
				<div style="background-color: #F1F1F1; padding: 3px;">
					<ul class="documentList" style="margin:0px">
						<li>
							<c:out value="${match.file}"></c:out>
						</li>
					</ul>
				</div>
				
				<div class="seriesItemContainer" style="width: 758px; padding: 5px; margin-bottom: 10px; margin-left: auto; margin-right: auto;">
					<input type="hidden" class="FilePath" value="${match.file}"/>
					
					<c:if test="${empty match.episode}">
	                  <a href="#" class="SelectEpisode">Select an episode</a>
	            	</c:if>
					<c:if test="${not empty match.episode}">
						
						<a href="#" class="ChangeEpisode" style="float:right;">Change episode selection</a>
						
						<h2 style="font-weight: bold; font-size: 18px; font-style: italic; margin-top: 0px">
							Episode ${match.episode.episodeNumber}: ${match.episode.episodeName}
						</h2>
						
						<c:if test="${empty match.episode.fileName}">
							<img src="/resources/images/ImageNotSelected.png" style="float:left; margin-right:10px; width: 758px; height: 140px">
						</c:if>
						<c:if test="${!empty match.episode.fileName}">
							<image src="/HotlinkedImage?imgUri=http://www.thetvdb.com/banners/${match.episode.fileName}" style="float:left; margin-right:10px; height: 150px">
						</c:if>
						
						<p style="font-style: italic; margin-top: 0px; height: 150px; overflow-y:auto;">
							${match.episode.overview}
						</p>
						
						<!-- <br style="clear:both;"/>	 -->
	            	</c:if>
            	</div>	
				
			</div>
			
		</c:forEach>
	
		<br>
		<a class="dissabledMainButton" href="/mediaMatcher/matchFiles_Match" id="Finish">Finish</a><a class="mainButton" href="/mediaMatcher/selectSeason" id="Previous">Previous</a>
		<br>
	</form:form> 
	
	
	
<%@include file="includes/footer.jsp"%>









































