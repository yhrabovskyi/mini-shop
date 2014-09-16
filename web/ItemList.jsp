<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<body>
<center>
<h4>Items page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<center><table>
<tr><td>Name</tsd><td>Description</td><td>Price</td><td>Available</td></tr>
<c:forEach var="item" items="${itemList}">
<tr><td>${item.name}</td><td>${item.stringDescr}</td><td>${item.price}</td>
<td><c:if test="${(item.quantity > 0) && (not empty loginSession.user.basket)}"><a href="<c:url value='/basket'><c:param name='op' value='add' /><c:param name='id' value='${item.id}' /></c:url>">buy</a></c:if> <c:if test="${loginSession.user.itemManager}"><a href="<c:url value='/item'><c:param name='op' value='edit' /><c:param name='id' value='${item.id}' /></c:url>">edit</a> <a href="<c:url value='/item'><c:param name='op' value='delete' /><c:param name='id' value='${item.id}' /></c:url>">delete</a></c:if></td></tr>
</c:forEach>
</table><br><br><br>
<c:if test="${(param.offset - 10) >= 0}"><a href="${url}?offset=${param.offset - 10}"><fmt:formatNumber value="${param.offset/10}" maxFractionDigits="0"/></a></c:if> <a href="${url}?offset=${param.offset + 0}"><fmt:formatNumber value="${param.offset/10 + 1}" maxFractionDigits="0"/></a> <c:if test="${(param.offset + 10) < numberOfRecords}"><a href="${url}?offset=${param.offset + 10}"><fmt:formatNumber value="${param.offset/10 + 2}" maxFractionDigits="0"/></a></c:if>
</center>
</body>
</html>
