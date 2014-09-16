<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<center>
<h4>User edit page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<c:if test="${not empty errMsg}">
<center><font color="red"><c:out value="${errMsg}" /></font></center>
</c:if>

<form method="POST" action="${url}">
<table border="1">
<input type="hidden" name="op" value="${param.op}">
<input type="hidden" name="id" value="${param.id}">
<tr><td>Username:</td><td>${user.login}</td></tr>
<tr><td>ID:</td><td>${user.id}</td></tr>
<tr><td>Password:</td><td><input type="password" name="userPassword"></td></tr>
<tr><td>Confirm password:</td><td><input type="password" name="userPassword"></td></tr>
<tr><td>First Name:</td><td><input type="text" name="name" value="${user.firstName}"></td></tr>
<tr><td>Second Name:</td><td><input type="text" name="name" value="${user.secondName}"></td></tr>
</table>
<input type="SUBMIT" value="Send">
</form>
<center>
</body>
</html>
