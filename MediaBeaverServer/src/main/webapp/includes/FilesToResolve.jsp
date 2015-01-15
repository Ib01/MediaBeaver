<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<p>Attempting to resolve the following Media files:</p>
<div style="background-color: #F1F1F1; padding: 3px;">
	<ul class="documentList" style="margin:0px">
		<c:forEach items="${filesToResolve}" var="path" varStatus="i">
		 	<li style="vertical-align: middle;"><c:out value="${path}"></c:out></li>
		</c:forEach>
	</ul>
</div>