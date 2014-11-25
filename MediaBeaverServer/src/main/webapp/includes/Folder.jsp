<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<ul style="list-style: none">

	<c:forEach items="${FolderIncludeCurrentFolder.files}" var="file" varStatus="i">

		<li class="highlightableLi"> 
			<input name="${FolderIncludeReferenceString}[${i.index}].path" type="hidden" value="${file.path}">
			<input name="${FolderIncludeReferenceString}[${i.index}].name" type="hidden" value="${file.name}">
			<input name="${FolderIncludeReferenceString}[${i.index}].file" type="hidden" value="${file.file}">
			<input name="${FolderIncludeReferenceString}[${i.index}].open" type="hidden" value="${file.open}" class="openHiddenInput">
		
			<c:if test="${file.selected}">
				<input type="checkbox" name="${FolderIncludeReferenceString}[${i.index}].selected" checked="checked" class="selectedCheckbox">
			</c:if>		
			<c:if test="${!file.selected}">
				<input type="checkbox" name="${FolderIncludeReferenceString}[${i.index}].selected" class="selectedCheckbox">
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
				<c:set var="FolderIncludeCurrentFolder" value="${file}" scope="request"/>
				<c:set var="FolderIncludeReferenceString" value="${FolderIncludeReferenceString}[${i.index}].files" scope="request"/>
			
				<jsp:include page="Folder.jsp"></jsp:include>
			</li>
		</c:if>
	
	</c:forEach>
	
</ul>
	
	