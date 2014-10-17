<%-- <%@ tag body-content="empty" %> --%>
<%@ attribute name="folder" type="java.io.File" required="true"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:out value="${folder.file}"/>

 <%-- <c:forEach var="file" items="${folder.files}">
 
 
 
     <c:out value="${file.name}"/>
     <myTags:folderGroups list="${folderGroup.subGroups}"/>
 
 </c:forEach>
 --%>