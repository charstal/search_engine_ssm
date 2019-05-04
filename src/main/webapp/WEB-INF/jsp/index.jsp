<%@ page import="cn.edu.zucc.caviar.searchengine.core.pojo.User" %><%--
  Created by IntelliJ IDEA.
  User: shiro
  Date: 19-4-29
  Time: 下午4:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>caviar</title>


    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>index</title>

    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body class="top-navigation">

<div id="wrapper">
    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom white-bg">
            <%
                User user = (User) session.getAttribute("USER_SESSION");

                if (user == null) {
                    user = new User();
                    user.setUserId(-1);
                    user.setUserName("index");
                }
            %>
            <nav class="navbar navbar-static-top" role="navigation">
                <%--                <div class="navbar-header">--%>
                <%--                <button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse" class="navbar-toggle collapsed" type="button">--%>
                <%--                <i class="fa fa-reorder"></i>--%>
                <%--                </button>--%>

                <%--                    <a href="#" class="navbar-brand">演示</a>--%>
                <%--                </div>--%>


                <div class="navbar-collapse collapse" id="navbar">
                    <%--                    <ul class="nav navbar-nav">--%>
                    <%--                        <li class="active">--%>
                    <%--                            <a aria-expanded="false" role="button" href="login.html"> 返回登录界面 </a>--%>
                    <%--                        </li>--%>
                    <%--                        <li class="dropdown">--%>
                    <%--                            <a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"> 菜单 <span class="caret"></span></a>--%>
                    <%--                            <ul role="menu" class="dropdown-menu">--%>
                    <%--                                <li><a href="">菜单</a></li>--%>
                    <%--                                <li><a href="">菜单</a></li>--%>
                    <%--                                <li><a href="">菜单</a></li>--%>
                    <%--                                <li><a href="">菜单</a></li>--%>
                    <%--                            </ul>--%>
                    <%--                        </li>--%>

                    <%--                    </ul>--%>
                    <ul class="nav navbar-top-links navbar-left">
                        <li>
                            <div class="row" style="height: 20px;"></div>
                            <div class="row col-lg-offset-0" style="height: 50px ;width: 1500px">
                                <div class="col-lg-10 form-group" style="height: 40px">
                                    <input type="text"  placeholder="请输入搜索内容" class="form-control" id="searchContext">
                                </div>
                                <div>
                                    <button class="btn btn-danger" id="search">搜索</button>
                                </div>
                            </div>

                            <form id="searchFrom" action="" method="GET">
                                    <%--                        <input type="hidden" name="_method" value="GET">--%>
                            </form>


                            <script>
                                $(function () {
                                    $("#search").click(function () {
                                        var keyword = $('#searchContext').val();
                                        var href = "${pageContext.request.contextPath}/search/" + keyword;
                                        $("#searchFrom").attr("action", href).submit();
                                        return false;
                                    });
                                })
                            </script>


                        </li>

                    </ul>


                    <ul class="nav navbar-top-links navbar-right">
                        <li>
                            <a href="${pageContext.request.contextPath}/user/<%=user.getUserId()%>" id="userHomepage">
                                <i class="fa fa-home"></i> <%=user.getUserName()%>
                            </a>

                        </li>
                        <li id="login">
                            <a href="${pageContext.request.contextPath}/session">
                                <i class="fa fa-sign-in"></i> log in
                            </a>
                        </li>

                        <li id="logout">
                            <a class="logout" href="${pageContext.request.contextPath}/session">
                                <i class="fa fa-sign-out"></i> log out
                            </a>

                            <form id="formdelete" action="" method="POST">
                                <input type="hidden" name="_method" value="DELETE">
                            </form>
                        </li>

                        <script>
                            $(function () {
                                var user = "<%=session.getAttribute("USER_SESSION")%>";
                                if (user === "null") {
                                    var logout = document.getElementById("logout");
                                    logout.style.display = 'none';
                                } else {
                                    // var register=document.getElementById("register");
                                    var login = document.getElementById("login");
                                    // register.style.display='none';
                                    login.style.display = 'none';
                                }

                            })
                        </script>

                        <script>

                            $(function () {
                                $(".logout").click(function () {
                                    var href = $(this).attr("href");
                                    $("#formdelete").attr("action", href).submit();
                                    return false;
                                });
                            })

                        </script>
                    </ul>

                </div>
            </nav>
        </div>


        <div class="wrapper wrapper-content ">
            <div class="container-fluid col-lg-8">


                <%--                <script>--%>
                <%--                    function search() {--%>
                <%--                        var searchContext = document.getElementById("searchContext").value;--%>
                <%--                        // console.log(userEmail);--%>
                <%--                        var url = "${pageContext.request.contextPath}/search/" + searchContext;--%>
                <%--                        $.ajax({--%>
                <%--                            url: url,--%>
                <%--                            type: "get",--%>
                <%--                            success: function (data) {--%>
                <%--                                window.location.href=url;--%>
                <%--                            }--%>

                <%--                        });--%>

                <%--                    }--%>
                <%--                </script>--%>

                <div class="row">
                    <div class="col-md-2">
                        <div class="ibox float-e-margins">
                            <img src="${pageContext.request.contextPath}/images/p2.jpg" class="img-preview">
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>一只狐狸呀</h5>
                            </div>
                            <div class="ibox-content">
                                <div class="row">
                                    <p>
                                        一只狐狸呀，它坐在沙丘上
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
            <div class="container-fluid col-lg-4">
                <div class="row">
                    <div class="col-md-2">
                        <div class="ibox float-e-margins">
                            <!--<img src="img/p2.jpg" class="img-preview">-->
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>一只狐狸呀</h5>
                            </div>
                            <div class="ibox-content">
                                <div class="row">
                                    <p>
                                        一只狐狸呀，它坐在沙丘上
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>


<!-- Mainly scripts -->


</body>
</html>
