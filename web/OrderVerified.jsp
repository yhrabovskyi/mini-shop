<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<center>
<h4>Basket page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<c:if test="${param.dialog eq 'show'}">
<c:forEach var="item" items="${itemList}">
${item.name}<br></c:forEach>
Do you really want order these items?<br>
<a href="${url}?dialog=yes">Yes</a>, <a href="${url}?dialog=no">No</a>
</c:if>
<c:if test="${not empty errMsg}">${errMsg}</c:if>
<c:if test="${(param.dialog eq 'yes') and (empty errMsg)}">Order is accepted.</c:if>
<c:if test="${param.dialog eq 'no'}">Order is not accepted.</c:if>
</center>
</body>
</html>
