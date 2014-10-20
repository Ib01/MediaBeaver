<%-- <%@ tag body-content="empty" %> --%>
<%@ attribute name="folder" type="com.ibus.mediabeaver.server.viewmodel.FileViewModel" required="true"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%-- 	<c:out value="${folder.path}"/> --%>
	
	
	
	
	<ul style="list-style: none">
	
		<c:forEach items="${folder.files}" var="file" varStatus="i">
	
			<c:if test="${file.file}">
				<li>
					<input type="checkbox" id="selectAll"><img src="/resources/images/document_24.png"><c:out value="${file.path}" />
				</li>
			</c:if>
			
			<c:if test="${!file.file}">
				<li>
					<input type="checkbox" id="selectAll"><img src="/resources/images/folder_24.png"><c:out value="${folder.path}" />
				</li>
				<li>
					<myTags:Folder folder="${file}"/>
				</li>
			</c:if>
		
		</c:forEach>
		
	</ul>
	
	
	<%-- <table style="width: 980px">
		<tr>
			<td style="vertical-align: bottom; width :30px;">
				<input type="checkbox" id="selectAll">
			</td>
			<td style="vertical-align: bottom; width :30px;">	
				<img src="/resources/images/folder_24.png">
			</td>
			<td style="vertical-align: bottom" class="selectableRow" >	
				<c:out value="${folder.path}" />
			</td>
		</tr>
	</table> --%>
	
	
	<%-- <c:if test="${!folder.file}"><img src="/resources/images/folder_24.png"></c:if> --%>
	
	 
	<%-- <table style="width: 950px; margin-left: 32px;">	
	
		<c:forEach items="${folder.files}" var="file" varStatus="i">
			<tr>
				<td style="vertical-align: bottom; text-align: center; width :10px; font-size: 24px">
					<c:if test="${file.file}"><span style="font-size: 28px">-</span></c:if>
					<c:if test="${!file.file}"><span style="font-size: 20px">+</span></c:if>
				</td>
				<td style="vertical-align: bottom; width :18px;" class="selectedColumn">
					<form:checkbox path="files[${i.index}].selected" class="selectedCheckbox"/>
				</td>
				
				<td style="vertical-align: bottom; width :30px;">	
					<c:if test="${file.file}"><img src="/resources/images/document_24.png"></c:if>
					<c:if test="${!file.file}"><img src="/resources/images/folder_24.png"></c:if>
				</td>
				
				<td style="vertical-align: bottom" class="selectableRow">	
					<c:if test="${file.file}"><c:out value="${file.name}" /></c:if>
					<c:if test="${!file.file}"><span class="folderName"><c:out value="${file.name}" /></span></c:if>
				
					<form:hidden path="files[${i.index}].path" class="filePath"/>				
					<form:hidden path="files[${i.index}].file" class="isFile"/>
				</td>
			</tr>
		</c:forEach>
		
		
	</table> --%>
 
 
    
    
    <%-- <myTags:folderGroups list="${folderGroup.subGroups}"/> --%>
 
 
