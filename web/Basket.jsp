<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<body>
<center>
<h4>Basket page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<table border="0">
<tr><td>Name</td><td>Description</td><td>Price</td></tr>
<c:forEach var="item" items="${itemList}">
<tr><td>${item.name}</td><td>${item.stringDescr}</td><td>${item.price}</td><td><a href="basket?op=delete&id=${item.id}">remove</a></td>
</c:forEach></table>
<a href="order?dialog=show">Order these items</a>
</center>
</body>
</html>
