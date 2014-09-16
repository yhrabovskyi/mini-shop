<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<center>
<h4>Item management page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<c:if test="${not empty errMsg}">
<font color="red"><c:out value="${errMsg}" /></font>
</c:if>

<form method="POST" action="${url}">
<table border="1">
<input type="hidden" name="op" value="${param.op}">
<input type="hidden" name="id" value="${param.id}">
<tr><td>Name:</td><td><input type="text" name="name" value="${item.name}"></td></tr>
<tr><td>Price:</td><td><input type="text" name="price" value="${item.price}"></td></tr>
<tr><td>Quantity:</td><td><input type="text" name="quantity" value="${item.quantity}"></td></tr>
<tr><td>Description:</td><td><textarea name="description" rows="10" cols="30">${item.stringDescr}</textarea></td></tr>
</table>
<input type="SUBMIT" value="Send">
</form>
</center>
</body>
</html>
