<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="includes/header.jsp"%>


	<script type="text/javascript" >
	
		$(function ()
		{	
			$("#Save").click(function() 
			{
				if($("form:first").validationEngine('validate'))
				{
					$("form:first").attr("action", "/configuration/save");
					$("form:first").submit();
				}
			});
			
			/* $("#Cancel").click(function() 
			{
				$("form:first").attr("action", "/configWizard/configCancel");
				$("form:first").submit();
			}); */
			
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
			'.leftPad("size", "padStr")',
			'.rightPad("size", "padStr")',
			'.capitalizeFully()',
			'.replaceFirst("regex", "replacement")',
			'.replaceAll("regex", "replacement")',
			'.toUpperCase()',
			'.toLowerCase()',
			'.substring("fromIndex","toIndex")',
			'.trim()'];
			
             $('.autoComplete').autocomplete({
            	 lookup: openSubtitlesFields,
                 minChars: 1,
                 delimiter: /\s*/
             });
			
		}
	</script>
	
	<h2>Configuration</h2>

	<form:form method="POST" commandName="configuration" id="configForm" class="formLayout">
		<form:hidden path="id"/>

		<div class="roundedPanel">
			<form:label path="sourceDirectory">Source Directory</form:label>
			<form:input path="sourceDirectory" style="width: 550px" class="autoComplete validate[required]"/>
			<br>
		</div>
		
		<div class="roundedPanel">
			<form:label path="tvRootDirectory">Tv Root</form:label>
			<form:input path="tvRootDirectory" style="width: 550px" class="validate[required]"/>
			<br>
			<form:label path="episodePath">Episode Path</form:label>
			<form:textarea path="episodePath" style="width: 550px; height: 50px" class="autoComplete validate[required]"/>
			<br>
		</div>
		
		<div class="roundedPanel">
			
			<form:label path="movieRootDirectory">Movie Root</form:label>
			<form:input path="movieRootDirectory" style="width: 550px" class="validate[required]"/>
			<br>
			<form:label path="moviePath">Movie Path</form:label>
			<form:textarea path="moviePath" style="width: 550px; height: 50px" class="autoComplete validate[required]"/>
			<br>
		</div>
	 
		 <div class="roundedPanel">
		 	<form:label path="videoExtensionFilter">Video Extension Filter</form:label>
		 	<form:textarea path="videoExtensionFilter" style="width: 550px; height: 100px" class="validate[required]"/>
		 	<br>
		 </div>
	 
		<br>
		<br>
		<a class="mainButton" href="#" id="Save">Save</a>
		<a class="mainButton" href="#" id="Cancel">Cancel</a>
		<br>
		<br>
		
	</form:form>
	
<%@include file="includes/footer.jsp"%>









































