<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<body>
<center>
<h4>Users page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<table border="0">
<tr><td>ID</td><td>Login</tsd><td>First name</td><td>Second name</td><td>Privileges</td></tr>
<c:forEach var="user" items="${userList}">
<tr><td>${user.id}</td><td>${user.login}</td><td>${user.firstName}</td><td>${user.secondName}</td>
<td><c:choose>
<c:when test="${user.privileges eq 10}">Administrator</c:when>
<c:when test="${user.privileges eq 20}">Manager</c:when>
<c:when test="${user.privileges eq 40}">Customer</c:when>
</c:choose>
 <c:if test="${loginSession.user.userManager}"><a href="<c:url value='/user'><c:param name='op' value='edit' /><c:param name='id' value='${user.id}' /></c:url>">edit</a> <a href="<c:url value='/user'><c:param name='op' value='delete' /><c:param name='id' value='${user.id}' /></c:url>">delete</a></c:if></td></tr>
</c:forEach>
</table><br><br><br>
<c:if test="${(param.offset - 10) >= 0}"><a href="${url}?offset=${param.offset - 10}"><fmt:formatNumber value="${param.offset/10}" maxFractionDigits="0"/></a></c:if> <a href="${url}?offset=${param.offset + 0}"><fmt:formatNumber value="${param.offset/10 + 1}" maxFractionDigits="0"/></a> <c:if test="${(param.offset + 10) < numberOfRecords}"><a href="${url}?offset=${param.offset + 10}"><fmt:formatNumber value="${param.offset/10 + 2}" maxFractionDigits="0"/></a></c:if>
</center>
</body>
</html>
