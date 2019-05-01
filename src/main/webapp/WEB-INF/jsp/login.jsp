<%--
  Created by IntelliJ IDEA.
  User: shiro
  Date: 19-4-29
  Time: 上午9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user" method="post" >
        账&nbsp;号：<input id="registerId" type="text" name="registerId" />
        <br /><br />
        密&nbsp;码：<input id="password" type="password" name="password" />
        <br /><br />
        gender：<input id="gender" type="text" name="gender" />
        <br /><br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" value="register" />
    </form>
</body>
</html>
