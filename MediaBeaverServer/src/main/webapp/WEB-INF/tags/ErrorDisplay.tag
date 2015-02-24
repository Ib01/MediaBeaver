<%@ attribute name="modelObject" type="java.lang.String" required="true"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	...
	
<spring:hasBindErrors name="${modelObject}">
		
	<c:out value='<script type="text/javascript" >' escapeXml='false'/>
	<c:out value='$(function (){' escapeXml='false'/>
	
       <c:forEach var="error" items="${errors.allErrors}">
           	
       		$('#<c:out value="${error.field}" />').validationEngine('showPrompt', '<c:out value="${error.defaultMessage}" />', '', 'topRight', true);
    			
       </c:forEach>
      
      <c:out value='});' escapeXml='false'/>
      <c:out value='</script>' escapeXml='false'/>
      
</spring:hasBindErrors>
	
	
	
	
	
 
