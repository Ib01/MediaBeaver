<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<ul style="list-style: none">

	<c:forEach items="${CurrentFolder.files}" var="file" varStatus="i">
		<li class="highlightableLi"> 
			<input name="${param.ReferenceString}[${i.index}].path" type="hidden" value="${file.path}">
			<input name="${param.ReferenceString}[${i.index}].name" type="hidden" value="${file.name}">
			<input name="${param.ReferenceString}[${i.index}].file" type="hidden" value="${file.file}">
			<input name="${param.ReferenceString}[${i.index}].open" type="hidden" value="${file.open}" class="openHiddenInput">
		
			<c:if test="${file.selected}">
				<input type="checkbox" name="${param.ReferenceString}[${i.index}].selected" checked="checked" class="selectedCheckbox">
			</c:if>		
			<c:if test="${!file.selected}">
				<input type="checkbox" name="${param.ReferenceString}[${i.index}].selected" class="selectedCheckbox">
			</c:if>
			
			<c:if test="${file.file}">
			
				<c:if test="${file.mediaType == 'Video'}">
						<img src="/resources/images/movies.png">
				</c:if>
				<c:if test="${file.mediaType == 'Music'}">
						<img src="/resources/images/file_extension_m4b.png">
				</c:if>
						
				<c:if test="${file.mediaType == 'Unknown'}">
					<img src="/resources/images/page_white_text.png">
					<!-- <img src="/resources/images/document_24.png"> -->
				</c:if>
				
				
			
				<c:out value="${file.name}"/>
			</c:if>		
			<c:if test="${!file.file}">
				<img src="/resources/images/folder_vertical_open.png">
				<!-- <img src="/resources/images/folder_24.png"> -->
				<span class="folderName"><c:out value="${file.name}"/></span>
			</c:if>
			
			<input type="hidden" value="false" class="operationTried" >
		</li>
		
		<c:if test="${!file.file}">
			<li>
				
				<!-- 
					have to pass directory in request scope as it is an object. looks like each include call however 
					however will have its own file collection (even though we are resetting the file collection here!!).
					if we dont pass ReferenceString as a parameter however it will get longer and longer since it has is not 
					scoped for each include. yup pretty hacky but it works   
				-->
				<c:set var="CurrentFolder" value="${file}" scope="request"/>
				<jsp:include page="Folder.jsp" >
					    <jsp:param name="ReferenceString" value="${param.ReferenceString}[${i.index}].files" />
				</jsp:include>
				
			</li>
		</c:if>
	
	</c:forEach>
	
</ul>














































	
	