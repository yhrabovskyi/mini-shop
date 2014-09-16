<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<center>
<h4>Login page</h4><br><br><br>
<%@ include file="Status.jsp" %>

<c:if test="${not empty errMsg}">
<font color="red"><c:out value="${errMsg}" /></font>
</c:if>

<form action="${url}" method="POST">
<table border="1">
<tr><td>Username:</td><td><input type="text" name="userLogin"></td></tr>
<tr><td>Password:</td><td><input type="password" name="userPassword"></td></tr>
</table>
<input type="SUBMIT" value="Send">
</form>
</center>
</body>
</html>
