<table>
<tr>
<td>
<c:if test="${not empty loginSession}"><a href="user?op=edit&id=${loginSession.user.id}">Edit profile</a> <a href="logout">Log out</a> <a href="options">Options</a></c:if>
<c:if test="${empty loginSession}"><a href="registration">Registration</a> <a href="login">Log in</a></c:if>
</td>
<td><a href="itemlist">Item list</a></td>
</tr>
</table>
