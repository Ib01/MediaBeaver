<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>


	<script type="text/javascript" >
	
	
		$(function ()
		{	
			$("#enableOpenSubtitles").click(function() 
			{
				$("form:first").attr("action", "/configWizard/openSubtitlesSelectorsEnable");
				$("form:first").submit();
			}); 
			
			$("#Previous").click(function() 
			{
				$("form:first").attr("action", "/configWizard/openSubtitlesSelectorsPrevious");
				$("form:first").submit();
			}); 
			
			$("#Next").click(function() 
			{
				$("form:first").attr("action", "/configWizard/openSubtitlesSelectorsNext");
				$("form:first").submit();
			}); 
		
			setAutoComplete();
		}); 

		
		function setAutoComplete()
		{
			var openSubtitlesFields = [
			'{UserNickName}', 
			'{SubFormat}',
			'{SeriesIMDBParent}',
			'{IDSubtitle}',
			'{IDMovie}',
			'{SubBad}',
			'{UserID}',
			'{ZipDownloadLink}',
			'{SubSize}',
			'{SubFileName}',
			'{SubDownloadLink}',
			'{MovieKind}',
			'{UserRank}',
			'{SubActualCD}',
			'{MovieImdbRating}',
			'{SubAuthorComment}',
			'{SubRating}',
			'{SeriesSeason}',
			'{SubFeatured}',
			'{SubtitlesLink}',
			'{SubHearingImpaired}',
			'{SubHash}',
			'{IDSubMovieFile}',
			'{ISO639}',
			'{MovieFPS}',
			'{SubDownloadsCnt}',
			'{MovieHash}',
			'{SubSumCD}',
			'{SubComments}',
			'{MovieByteSize}',
			'{LanguageName}',
			'{MovieYear}',
			'{SubLanguageID}',
			'{MovieReleaseName}',
			'{SeriesEpisode}',
			'{MovieName}',
			'{MovieTimeMS}',
			'{MatchedBy}',
			'{SubHD}',
			'{MovieNameEng}',
			'{SubAddDate}',
			'{IDMovieImdb}',
			'{IDSubtitleFile}',		                 
			'.normalizeSpace()',
			'.leftPad(size, padStr)',
			'.rightPad(size, padStr)',
			'.capitalizeFully()',
			'.replaceFirst(regex, replacement)',
			'.replaceAll(regex, replacement)',
			'.toUpperCase()',
			'.toLowerCase()',
			'.trim()'];
			
			
			//'.capitalizeFully(delimiters)',
					 
             $('.autoComplete').autocomplete({
            	 lookup: openSubtitlesFields,
                 minChars: 1,
                 delimiter: /\s*/
             });
			 
			 //lookup: countries,
             //	 serviceUrl:'/configWizard/testAjax',
		}
		 

	</script>
	
	<style>
		.detailedListItem{
			border-radius: 8px;
			background-color: #F1F1F1;
			padding: 10px;
			padding-bottom: 15px;
			margin-top: 5px;
			margin-bottom: 5px;
		}
		.formLayout .detailedListItem label{
			width: 150px;
		}
		
		.detailedListItemLevel1Box{
			background-color: white; 
			padding:7px;
			border-radius: 8px;
			margin-bottom: 6px;
		}
		
		.detailedListItem H3{
			color:#1F1F1F;
			margin-top: 2px;
			font-size: 14px;
			font-weight: normal;
		}
	</style>


	

	
	<h2>Config Wizard Step 2: Modify Open Subtitles Selectors</h2>

	<form:form method="POST" commandName="config" id="configForm" class="formLayout">

		<form:checkbox path="openSubtitlesEnabled" id="enableOpenSubtitles"/> Enable Open Subtitles Selector
	
		<form:hidden path="selectedRegExSelectorIndex"/>
		
		<div class="detailedListItem ui-widget">
		
			<c:forEach items="${config.openSubtitlesSelectors}" var="selector" varStatus="i">
				   
				<label><c:out value="${selector.pathTokenName}" /></label>
			
				<form:input path="openSubtitlesSelectors[${i.index}].openSubititleField" style="width: 550px" class="autoComplete"/>
				
				<br/ >
			</c:forEach>
			
		</div>
	 
		<br>
		<br>
		<a class="mainButton" href="#" id="Next">Next</a>
		<a class="mainButton" href="#" id="Previous">Previous</a>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>









































