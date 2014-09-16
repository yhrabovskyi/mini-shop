<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<center>
<h4>Item description page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<table border="1px">
<tr><td>Name</td><td>Description</td><td>Price</td><td>Available</td></tr>
<tr><td>${item.name}</td><td>${item.stringDescr}</td><td>${item.price}</td>
<td><c:if test="${item.quantity > 0}"><a href="<c:url value='/basket'><c:param name='op' value='add' /><c:param name='id' value='${item.id}' /></c:url>">buy</a></c:if> <c:if test="${loginSession.user.itemManager}"><a href="<c:url value='/item'><c:param name='op' value='edit' /><c:param name='id' value='${item.id}' /></c:url>">edit</a> <a href="<c:url value='/item'><c:param name='op' value='delete' /><c:param name='id' value='${item.id}' /></c:url>">delete</a></c:if></td></tr>
</c:forEach>
</table>
</center>
</body>
</html>
