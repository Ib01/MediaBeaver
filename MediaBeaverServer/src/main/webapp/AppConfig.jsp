<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="includes/header.jsp" %>
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







<script type="text/javascript" >

	$(function ()
	{
		
		
	});
        
</script>


<h2>Media Item Configuration</h2>

<form:form method="POST" action="/config/save" commandName="config">
   
   <div class="shadowBox">
		<table>
			<tr>
			    <td class="formLableCell"><form:label path="name">Media Name</form:label></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
			    <td class="formLableCell"><form:label path="targetDirectory">Target Directory</form:label></td>
				<td><form:input path="targetDirectory" style="width:500px" /></td>
		   	</tr>
		</table>
   </div>
   
   

	  
	<br>
	<br>
	<br>  
	<input type="submit" value="Submit"/>
</form:form>


${config.name}

<%@include file="includes/footer.jsp" %>












































