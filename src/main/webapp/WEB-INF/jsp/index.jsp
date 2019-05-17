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

                <div class="navbar-collapse collapse" id="navbar">

                    <ul class="nav navbar-top-links navbar-left">
                        <li>
                            <div class="row" style="height: 20px;"></div>
                            <div class="row col-lg-offset-0" style="height: 50px ;width: 1500px">
                                <div class="col-lg-10 form-group" style="height: 40px">
                                    <input type="text" placeholder="请输入搜索内容" class="form-control" id="searchContext">
                                </div>
                                <div>
                                    <button class="btn btn-danger" id="search">搜索</button>
                                </div>
                            </div>

                            <script>
                                $('#search').on("click", function () {
                                    var keyword = $('#searchContext').val();
                                    $.ajax({
                                        url: "${pageContext.request.contextPath}/search/" + keyword,
                                        type: "get",
                                        contentType: "application/json; charset=UTF-8",
                                        // dataType: "json",
                                        success: function (res) {
                                            alert(res);
                                        }
                                    });
                                });
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
                <div class="row">
                    <div class="row">
                        <div class="col-md-2">
                            <div class="ibox float-e-margins">
                                <a href="#">
                                    <img src="${pageContext.request.contextPath}/images/avatar/p2.jpg" class="img-preview">
                                </a>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="ibox float-e-margins">
                                <h3><a href="#">西湖</a></h3>
                                <span style="color:grey">2019年5月15日 - </span>
                                西湖，位于浙江省杭州市西湖区龙井路1号，杭州市区西部，景区总面积49平方千米，汇水面积为21.22平方千米，湖面面积为6.38平方千米。
                                西湖南、西、北三面环山，湖中白堤、苏堤、杨公堤、赵公堤将湖面分割成若干水面。西湖的湖体轮廓呈近椭圆形，湖底部较为平坦。湖泊天然地表水源是金沙涧、龙泓涧、赤山涧（慧因涧）、长桥溪四条溪流。西湖地处中国东南丘陵边缘和亚热带北缘
                                ...
                                <div>
                                    <a href="#">收藏</a>
                                    <a href="#">点赞</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <div class="ibox float-e-margins">
                                <a href="#">
                                    <img src="${pageContext.request.contextPath}/images/avatar/p2.jpg" class="img-preview">
                                </a>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="ibox float-e-margins">
                                <h3><a href="#">西湖</a></h3>
                                <span style="color:grey">2019年5月15日 - </span>
                                西湖，位于浙江省杭州市西湖区龙井路1号，杭州市区西部，景区总面积49平方千米，汇水面积为21.22平方千米，湖面面积为6.38平方千米。
                                西湖南、西、北三面环山，湖中白堤、苏堤、杨公堤、赵公堤将湖面分割成若干水面。西湖的湖体轮廓呈近椭圆形，湖底部较为平坦。湖泊天然地表水源是金沙涧、龙泓涧、赤山涧（慧因涧）、长桥溪四条溪流。西湖地处中国东南丘陵边缘和亚热带北缘
                                ...
                                <div>
                                    <a href="#">收藏</a>
                                    <a href="#">点赞</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
            <div class="container-fluid col-lg-4">
                <div class="row">
                    <div class="col-md-8">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>西湖</h5>
                            </div>
                            <div class="ibox-content">
                                <div class="row">
                                    <a href="#">
                                        <img src="${pageContext.request.contextPath}/images/avatar/p3.jpg" class="img-preview">
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>西湖</h5>
                            </div>
                            <div class="ibox-content">
                                <div class="row">
                                    <a href="#">
                                        <img src="${pageContext.request.contextPath}/images/avatar/p3.jpg" class="img-preview">
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>



</body>
</html>
