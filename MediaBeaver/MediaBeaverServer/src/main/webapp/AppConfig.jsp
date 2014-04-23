<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="includes/header.jsp"%>
<!-- 
Operation Description: e.g. move all movies
Target Directory: \bla\movies

Action
	- Delete
	- move

Media Type: 
	- Movie
	- Tv Episode
	- Song

if(delete)
{
	content selection:
		+ extension
		+ reg ex
		+ file 
		+ folder
		+ empty folder
}
else
{
	content selection:
		+ extension
		+ reg ex
	
	Naming Service (used to confirm media)  
		+TMDB
		+TVDB
	
	destination folder: eg {Tv Series}\Season {Season number} 
}
-->


	<script type="text/javascript">
		$(function() {
	
		});
	</script>
	


<h2>Media Item Configuration</h2>

<form:form method="POST" action="/config/save" commandName="config"
	class="formLayout">

	<div class="shadowBox">
	
		<form:label path="description">Description</form:label>
		<form:input path="description" style="width: 550px" />
		<br>

		<form:label path="action">Action</form:label>
		<form:select path="action">
			<form:options items="${actions}" />
		</form:select>
		<br>
		
		<form:label path="sourceDirectory">Source Directory</form:label>
		<form:input path="sourceDirectory" style="width: 550px" />
		<br>
		
	</div>

	<br>
	
	<div class="shadowBox">
		<div class="shadowBoxHeader">Configuration Variables 
	   		<span style="float: right;" class="shadowBoxHelp">
		   		<span style="font-size: 18px; font-weight: bold;  color: #FF8A00;">? </span>
		   		<span style="font-size: 12px; font-weight: bold;  color: white;"><span style="font-size: 16px; font-weight: bold; color: white;">H</span>elp</span>
	   		</span>
   		</div>
	
		<c:forEach var="item" items="${config.configVariables}">
			
		
			${item.name}<br>
		</c:forEach>
	
	
	</div>


	<br>
	<br>
	<br>
	<input type="submit" value="Submit" />
</form:form>


<%-- ${config.name} --%>

<%@include file="includes/footer.jsp"%>












































