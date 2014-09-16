<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<center>
<h4>Error page</h4><br><br><br>
<%@ include file="Status.jsp" %>
<font color="red"><c:out value="${errMsg}" /></font>
</form>
</center>
</body>
</html>
