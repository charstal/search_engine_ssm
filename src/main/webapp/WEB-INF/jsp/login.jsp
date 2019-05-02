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
    <meta charset="UTF-8">
    <title>login</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
</head>
<body>
<div class="gray-bg" id="mainView">
    <div class="row border-bottom white-bg">
        <nav class="navbar navbar-static-top" role="navigation">
            <!--<div class="navbar-header">-->
            <!--<button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse" class="navbar-toggle collapsed" type="button">-->
            <!--<i class="fa fa-reorder"></i>-->
            <!--</button>-->
            <!--<a href="#" class="navbar-brand">演示</a>-->
            <!--</div>-->
            <div class="navbar-collapse collapse" id="navbar">
                <!--<ul class="nav navbar-nav">-->
                <!--&lt;!&ndash;<li class="active">&ndash;&gt;-->
                <!--&lt;!&ndash;<a aria-expanded="false" role="button" href="login.html"> 返回登录界面 </a>&ndash;&gt;-->
                <!--&lt;!&ndash;</li>&ndash;&gt;-->
                <!--<li class="dropdown">-->
                <!--<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"> 菜单 <span class="caret"></span></a>-->
                <!--<ul role="menu" class="dropdown-menu">-->
                <!--<li><a href="">菜单</a></li>-->
                <!--<li><a href="">菜单</a></li>-->
                <!--<li><a href="">菜单</a></li>-->
                <!--<li><a href="">菜单</a></li>-->
                <!--</ul>-->
                <!--</li>-->

                <!--</ul>-->
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <a href="${pageContext.request.contextPath}/session">
                            <i class="fa fa-sign-out"></i> 登录
                        </a>
                        <!--<a href="login.html">-->
                        <!--<i class="fa fa-sign-out"></i> 登录-->
                        <!--</a>-->
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <form class="form-horizontal container-fluid center-block"  style="height: 1500px" id="loginForm" method="post">
        <img src="${pageContext.request.contextPath}/images/p3.jpg">
        <img src="${pageContext.request.contextPath}/images/p2.jpg">
        <img src="${pageContext.request.contextPath}/images/p4.jpg">
        <div style="height: 30px">
        </div>
        <div class="form-group center-block">
            <label for="inputEmail" class="col-sm-2 control-label">Email</label>
            <div class="col-sm-4">
                <input type="email" class="form-control" id="inputEmail" placeholder="Email" required autofocus>
            </div>
        </div>
        <div class="form-group">
            <label for="inputPassword" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="inputPassword" placeholder="Password" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="remember-me"> Remember me
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default" id="btn-signin">Sign in</button>
            </div>
        </div>

    </form>

    <script type="text/javascript">
        // 初始化登录窗口
        (function initSignin() {
            if ($.cookie('bit') === 'true') {
                $('#remember-me').attr('checked', 'checked');
                $('#inputEmail').val($.cookie('username'));
                $('#inputPassword').val($.cookie('password'));
            }

            $('#btn-signin').on('click', function() {
                var username = $('#inputEmail').val();
                var password = $('#inputPassword').val();
                $.ajax({
                    async:false,
                    url: "${pageContext.request.contextPath}/session",
                    type: "post",
                    data: JSON.stringify({registerId: username, password: password}),
                    contentType: "application/json; charset=UTF-8",
                    dataType: "json",
                    success: function(res) {
                        if (res.userId !== undefined) {
                            if ($('#remember-me').is(':checked')) {
                                $.cookie('username', username, {
                                    expires: 7
                                });
                                $.cookie('password', password, {
                                    expires: 7
                                });
                                $.cookie('bit', 'true', {
                                    expires: 7
                                });
                            } else {
                                $.removeCookie('username');
                                $.removeCookie('password');
                                $.removeCookie('bit');
                            }
                        } else {
                            alert('登录失败：' + res.error !== undefined ? res.error : '未知错误！');
                        }
                    },
                    error: function(error) {
                        alert(error.statusText + "(" + error.status + ")")
                    }
                });
                return false;
            })
        })()

    </script>

    <script type="text/javascript">
<%--        function login() {--%>
<%--            var userEmail = document.getElementById("inputEmail").value;--%>
<%--            var password = document.getElementById("inputPassword").value;--%>
<%--            // console.log(userEmail);--%>
<%--            if(userEmail === undefined || userEmail === null || password === undefined || password === null || password.length < 5) {--%>

<%--                alert("enter password");--%>
<%--                return false;--%>

<%--            }--%>
<%--            $.ajax({--%>
<%--                async:false,--%>
<%--                url: "${pageContext.request.contextPath}/session",--%>
<%--                type: "post",--%>
<%--                data: JSON.stringify({registerId: userEmail, password: password}),--%>
<%--                contentType: "application/json; charset=UTF-8",--%>
<%--                dataType: "json",--%>
<%--                success: function(data) {--%>
<%--                    console.log(data);--%>
<%--                    if(data != null) {--%>
<%--                        alert("你输入的用户名为：" + data.registerId + "密码为：" + data.gender);--%>
<%--                        reRenderView();--%>
<%--                    }--%>

<%--                }--%>
<%--            });--%>
<%--            return false;--%>
<%--        }--%>

    </script>


    <script>
        function reRenderView() {
            var deleteFrom = document.getElementById("loginForm");
            deleteFrom.parentNode.removeChild(deleteFrom);
        }

    </script>
</div>

</body>
</html>
