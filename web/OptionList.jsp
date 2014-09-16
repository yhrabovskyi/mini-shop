<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<center>
<h4>Option list page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<table border="0">
<c:forEach var="option" items="${optionList}"><tr><td><a href="${option.url}">${option.name}</a></td></tr></c:forEach>
</table>
</center>
</body>
</html>
