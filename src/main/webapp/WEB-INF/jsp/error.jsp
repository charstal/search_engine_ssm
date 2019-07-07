<%--
  Created by IntelliJ IDEA.
  User: shiro
  Date: 19-4-29
  Time: 上午10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>404 错误</title>

    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body class="gray-bg">


<div class="middle-box text-center animated fadeInDown">
    <h1>404</h1>
    <h3 class="font-bold">找不到网页</h3>

    <div class="error-desc">
        ${msg}
<%--        <form class="form-inline m-t" role="form">--%>
<%--            <div class="form-group">--%>
<%--                <input type="text" class="form-control" placeholder="请搜索你的内容">--%>
<%--            </div>--%>
<%--            <button type="submit" class="btn btn-primary">搜索</button>--%>
<%--        </form>--%>
    </div>
</div>

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>
