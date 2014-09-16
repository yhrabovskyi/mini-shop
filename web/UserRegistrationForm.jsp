<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<center>
<h4>Registration page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<c:if test="${not empty errMsg}">
<font color="red"><c:out value="${errMsg}" /></font>
</c:if>
<br>
<form method="POST" action="${url}">
<table border="1">
<tr><td>Username:</td><td><input type="text" name="userLogin"></td></tr>

<c:if test="${loginSession.user.userManager}">
<tr><td>Privileges:</td><td><select name="privileges">
	<option value="10">Administrator</option>
	<option value="20">Manager</option>
</select></td></tr></c:if>

<tr><td>Password:</td><td><input type="password" name="userPassword"></td></tr>
<tr><td>Confirm password:</td><td><input type="password" name="userPassword"></td></tr>
<tr><td>First Name:</td><td><input type="text" name="name"></td></tr>
<tr><td>Second Name:</td><td><input type="text" name="name"></td></tr>
</table>
<input type="SUBMIT" value="Send">
</form>
</body>
</html>
