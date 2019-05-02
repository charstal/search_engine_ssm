<%--
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
</head>
<body class="top-navigation">

<div id="wrapper">
    <div id="page-wrapper" class="gray-bg">
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

        <div class="wrapper wrapper-content ">
            <div class="container-fluid col-lg-8">
                <div class="row" style="height: 50px">
                    <div class="col-lg-10 form-group" style="height: 30px">
                        <label>
                            <input type="text" placeholder="请输入搜索内容" class="form-control">
                        </label>
                    </div>
                    <div>
                        <button class="btn btn-danger">搜索</button>
                    </div>
                </div>
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
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>



</body>
</html>
