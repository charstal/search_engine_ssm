<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.edu.zucc.caviar.searchengine.core.pojo.User" %><%--
  Created by IntelliJ IDEA.
  User: shiro
  Date: 19-5-2
  Time: 下午8:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${note.title}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>

    <!-- Mainly scripts -->
    <script src="${pageContext.request.contextPath}/js/carousel.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/star-rating.js" type="text/javascript"></script>

</head>
<body class="top-navigation">
<style>
    .carousel .item {
        height: 350px;
        background-color: #777;
    }

    .carousel-inner > .item > img {
        position: absolute;
        top: 0;
        left: 0;
        min-width: 100%;
        height: 500px;
    }
</style>
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
                        </li>

                    </ul>


                    <ul class="nav navbar-top-links navbar-right">
                        <li>
                            <a href="/index">
                                <i class="fa fa-search"></i> search index
                            </a>
                        </li>
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


        <div class="wrapper wrapper-content">
            <div class="row">
                <div class="container-fluid col-lg-4 ">
                    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                            <c:forEach var="i" begin="1" end="${note.imageUrls.size() - 1}">
                                <li data-target="#carousel-example-generic" data-slide-to="${i}"></li>
                                <%--                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>--%>
                                <%--                            <li data-target="#carousel-example-generic" data-slide-to="2"></li>--%>
                            </c:forEach>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner">
                            <div class="item active ">
                                <img src="${note.imageUrls[0]}" class="img-responsive" alt="First slide">
                            </div>
                            <c:forEach var="i" begin="1" end="${note.imageUrls.size() - 1}">
                                <div class="item">
                                    <img src="${note.imageUrls[i]}" class="img-responsive">
                                </div>
                                <%--                            <div class="item">--%>
                                <%--                                <img src="${pageContext.request.contextPath}/images/p3.jpg" class="img-responsive" alt="Second slide">--%>
                                <%--                            </div>--%>
                                <%--                            <div class="item">--%>
                                <%--                                <img src="${pageContext.request.contextPath}/images/p4.jpg" class="img-responsive" alt="Third slide">--%>
                                <%--                            </div>--%>

                            </c:forEach>
                        </div>

                        <!-- Controls -->
                        <a class="left carousel-control" href="#carousel-example-generic" role="button"
                           data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" role="button"
                           data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>

            <br>
            <div class="row">
                <div class="container-fluid col-lg-8">
                    <div style="height: 30px"></div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h1>${note.title}</h1>
                                </div>
                                <div class="ibox-content">
                                    <div class="row">
                                        <p>
                                            ${note.content}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div>

                        <div class="ibox">
                            <textarea id="content" class="form-control" style="width: 75%" rows="3"
                                      placeholder="参与评论，即有现金红包大奖等你来拿"></textarea>

                            <input id="input-id" type="text" class="rating" data-size="lg">
                            <button id="comment-commit" class="btn btn-default" type="submit">提交</button>
                        </div>

                        <div id="comment">
                            <h3>笔记评论</h3>
                            <div class="row">
                                <c:forEach items="${comments}" var="comment">
                                    <%--                                    <div class="col-md-8">--%>
                                    <%--                                        <div id="comment-info" class="row">--%>
                                    <%--                                            <div id="comment-user" class="col-md-8">--%>
                                    <%--                                                <div id="avatar" class="col-md-2">--%>
                                    <%--                                                    <img src="${pageContext.request.contextPath}/images/avatar/avatar.jpeg" class="img-circle" height="60px" width="60px">--%>
                                    <%--                                                </div>--%>
                                    <%--                                                <div id="user-info" class="col-md-2">--%>
                                    <%--                                                    <h4>--%>
                                    <%--                                                        <a href="#">jiangDoor</a>--%>
                                    <%--                                                    </h4>--%>
                                    <%--                                                    <p>07-02</p>--%>
                                    <%--                                                </div>--%>
                                    <%--                                            </div>--%>
                                    <%--                                            <div id="comment-star" class="col-md-4">--%>
                                    <%--                                                <span id="like" class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>--%>
                                    <%--                                                <span id="like-sum">277</span>--%>
                                    <%--                                            </div>--%>
                                    <%--                                        </div>--%>
                                    <%--    --%>
                                    <%--                                        <p class="col-md-12">--%>
                                    <%--                                            这是一条评论，现在随便写一点当作评论，如果写的不好，自己改。--%>
                                    <%--                                        </p>--%>
                                    <%--    --%>
                                    <%--                                    </div>--%>

                                    <div class="col-md-8">
                                        <div class="row comment-info">
                                            <div class="col-md-8 comment-user">
                                                <div class="col-md-2 avatar">
                                                    <img src="${pageContext.request.contextPath}/${comment.avatarUrl}"
                                                         class="img-circle" height="60px" width="60px">
                                                </div>
                                                <div class="col-md-2 user-info">
                                                    <h4>
                                                        <a href="/user/${comment.userId}">${comment.userName}</a>
                                                    </h4>
                                                    <p>${comment.date}</p>
                                                </div>
                                            </div>
                                            <div class="col-md-4 comment-star">
                                                <span class="glyphicon glyphicon-star-empty like"
                                                      aria-hidden="true"></span>
                                                <span class="like-sum">${comment.score}</span>
                                            </div>
                                        </div>

                                        <p class="col-md-12">
                                                ${comment.content}
                                        </p>

                                    </div>
                                </c:forEach>


                            </div>
                        </div>
                    </div>


                    <script>
                        $("#input-id").rating({
                            min: 0,
                            max: 5,
                            step: 0.5,
                            size: 'lg',
                            showClear: false,
                            starCaptions: {
                                0: "",
                                0.5: '半星',
                                1: '一星',
                                1.5: '一星半',
                                2: '两星',
                                2.5: '两星半',
                                3: '三星',
                                3.5: '三星半',
                                4: '四星',
                                4.5: '四星半',
                                5: '五星'
                            }
                        });
                    </script>

                    <script>
                        $("#comment-commit").click(function () {
                            var score = $("#input-id").val();
                            var content = $("#content").val();
                            if (score != null && (content != null || content != "")) {
                                $.ajax({
                                    async: false,
                                    url: "/comment",
                                    method: "post",
                                    contentType: "application/json; charset=UTF-8",
                                    data: JSON.stringify({score: score, content: content, noteId: "${note.docId}"}),
                                    dataType: "text",
                                    success: function (res) {
                                        if (res == "success") {
                                            alert("success！");
                                        } else {
                                            alert("login first！");
                                            window.location.href = "/session";
                                        }
                                        window.location.reload();
                                    }
                                })
                            }
                        })
                    </script>

<%--                    <script>--%>
<%--                        function like() {--%>
<%--                            var like_sum = $("#like-sum").text();--%>
<%--                            if ($(this).hasClass("glyphicon glyphicon-star")) {--%>
<%--                                $(this).removeClass("glyphicon glyphicon-star");--%>
<%--                                $(this).addClass("glyphicon glyphicon-star-empty");--%>
<%--                                $('#like-sum').text(Number(like_sum) - 1)--%>
<%--                            } else {--%>
<%--                                $(this).removeClass("glyphicon glyphicon-star-empty");--%>
<%--                                $(this).addClass("glyphicon glyphicon-star");--%>
<%--                                $('#like-sum').text(Number(like_sum) + 1)--%>
<%--                            }--%>

<%--                        }--%>
<%--                    </script>--%>
                </div>
                <div class="container-fluid col-lg-4">
                    <div class="col-md-4">
                        <div class="ibox float-e-margins">
                            <!--<img src="img/p2.jpg" class="img-preview">-->
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="ibox float-e-margins">
                            <%--                            <div class="ibox-title">--%>
                            <%--                                <h5>一只狐狸呀</h5>--%>
                            <%--                            </div>--%>
                            <%--                            <div class="ibox-content">--%>
                            <%--                                <div class="row">--%>
                            <%--                                    <p>--%>
                            <%--                                        一只狐狸呀，它坐在沙丘上--%>
                            <%--                                    </p>--%>
                            <%--                                </div>--%>
                            <%--                            </div>--%>

                            <c:forEach items="${similarNote}" var="note">
                                <div class="ibox-title">
                                    <a href="/note/${note.docId}"><h5>${note.title}</h5></a>
                                </div>
                                <div class="ibox-content">
                                    <div class="row">
                                        <a href="/note/${note.docId}">
                                            <img class="img-preview" src="${note.imageUrls[0]}">
                                        </a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>


        </div>

    </div>
</div>


</body>
</html>
