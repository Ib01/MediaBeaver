<%-- <%@ tag body-content="empty" %> --%>
<%@ attribute name="folder" type="com.ibus.mediabeaver.server.viewmodel.FileViewModel" required="true"%>
<%@ attribute name="parentObject" type="java.lang.String" required="true"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


	<ul style="list-style: none">
	
		<c:forEach items="${folder.files}" var="file" varStatus="i">
	
			<li class="highlightableLi"> 
				<input name="${parentObject}[${i.index}].path" type="hidden" value="${file.path}">
				<input name="${parentObject}[${i.index}].name" type="hidden" value="${file.name}">
				<input name="${parentObject}[${i.index}].file" type="hidden" value="${file.file}">
				<input name="${parentObject}[${i.index}].open" type="hidden" value="${file.open}" class="openHiddenInput">
			
				<c:if test="${file.selected}">
					<input type="checkbox" name="${parentObject}[${i.index}].selected" checked="checked">
				</c:if>		
				<c:if test="${!file.selected}">
					<input type="checkbox" name="${parentObject}[${i.index}].selected">
				</c:if>
				
				<c:if test="${file.file}">
					<img src="/resources/images/document_24.png">
					<c:out value="${file.name}"/>
				</c:if>		
				<c:if test="${!file.file}">
					<img src="/resources/images/folder_24.png">
					<span class="folderName"><c:out value="${file.name}"/></span>
				</c:if>
				
			</li>
			
			<c:if test="${!file.file}">
				<li>
					<myTags:Folder folder="${file}" parentObject="${parentObject}[${i.index}].files"/>
				</li>
			</c:if>
		
		</c:forEach>
		
	</ul>
	
	
	
	
	
	
 
