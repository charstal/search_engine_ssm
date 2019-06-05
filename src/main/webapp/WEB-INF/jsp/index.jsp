<%@ page import="cn.edu.zucc.caviar.searchengine.core.pojo.User" %><%--
  Created by IntelliJ IDEA.
  User: shiro
  Date: 19-4-29
  Time: 下午4:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-page.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
</head>

<body class="top-navigation">

<div id="wrapper">
<%--    slimScroll--%>
    <div id="page-wrapper" class="white-bg slimScrollDiv">
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
                                var searchEvent = function () {
                                    var keyword = $('#searchContext').val();
                                    if(keyword === null) {
                                        return ;
                                    }
                                    var search = "search/" + keyword;
                                    $.ajax({
                                        url: "/" + search,
                                        type: "get",
                                        contentType: "application/json; charset=UTF-8",
                                        dataType: "json",
                                        success: function (res) {
                                            var spellCheckView = document.getElementById("checkSpellView");
                                            spellCheckView.style.display = "none";

                                            var documentSet = res["documentSet"];
                                            var noteCount = res["documentNumber"];
                                            var spellCheckList = res["spellCheckList"];
                                            var recommendDocumentSet = res["recommendDocumentSet"];



                                            if(noteCount !== null) {
                                                $("#page").Page({
                                                    totalPages: noteCount / 10 //分页总数
                                                });
                                                if(noteCount < 50) {
                                                    spellCheck(spellCheckList);
                                                    addEventForSpell();
                                                }
                                            }


                                            console.log("documentNumber:" + noteCount);
                                            render(documentSet);
                                            renderRecommend(recommendDocumentSet);
                                            addEventForLikeAndCollect();


                                            var searchNumber = document.getElementById("searchNumber");
                                            searchNumber.innerHTML = "搜到的当前相关页面约为：" + noteCount + "个";
                                            searchNumber.style.display = "block";

                                            sessionStorage.setItem("lastJson", JSON.stringify(documentSet));
                                            sessionStorage.setItem("lastUrl", search);
                                            sessionStorage.setItem("keyword", keyword);
                                            sessionStorage.setItem("searchResultCount", noteCount);
                                            sessionStorage.setItem("recommendDoc", JSON.stringify(recommendDocumentSet));


                                        }
                                    });
                                };
                                $('#search').on("click", searchEvent);
                                $(document).keydown(function (event) {
                                    if(event.keyCode === 13) {
                                        searchEvent();
                                    }
                                })
                            </script>

                            <script>
                                function render(res) {
                                    var page = "";

                                    for(var item in res) {
                                        console.log(res[item]);
                                        page += "<div class=\"row\">";
                                        page += "<div class=\"col-md-2\">";
                                        page += "<div class=\"ibox float-e-margins\">";
                                        page += "<a href=\"" + "${pageContext.request.contextPath}/note/" + res[item].docId + "\">";
                                        page += "<img src=\"" + res[item].imageUrls[0] + "\"" + "class=\"img-preview\">";
                                        page += "</a>";
                                        page += "</div>";
                                        page += "</div>";

                                        page += "<div class=\"col-md-8\">";
                                        page += "<div class=\"ibox float-e-margins\">";
                                        // title
                                        // var contentSplit = res[item].content.split(/[\s\n]/);
                                        page += "<h3><a href=\"" + "${pageContext.request.contextPath}/note/" + res[item].docId + "\">" + res[item].title + "</a></h3>";

                                        var date = res[item].publishDate.split("T");
                                        page += "<span style=\"color:grey\">" + date[0] + " - </span>";
                                        page += res[item].content;
                                        page += "<div>";

                                        page += "<a name=\"collect\" href=\"" + "${pageContext.request.contextPath}/collect/" + res[item].docId + "\"> 收藏 </a>";
                                        page += "<a name=\"like\" href=\"" + "${pageContext.request.contextPath}/like/" + res[item].docId + "\"> 点赞 </a>";
                                        page += "</div>";
                                        page += "</div>";
                                        page += "</div>";
                                        page += "</div>";

                                    }

                                    var content = document.getElementById("content");
                                    content.innerHTML = page;

                                }
                            </script>

                            <script>
                                function addEventForLikeAndCollect() {
                                    var collect = document.getElementsByName("collect");
                                    var like = document.getElementsByName("like");
                                    for (var i = 0; i < collect.length; ++i) {
                                        collect[i].addEventListener("click", function (event) {
                                            $.ajax({
                                                async:false,
                                                url: this.href,
                                                type: "get",
                                                dataType:"text",
                                                success: function (res) {
                                                    console.log(res);
                                                    if(res === "success")
                                                        alert("success");
                                                    else {
                                                        alert(res);
                                                        window.location.href = "http://localhost:8080/session";
                                                    }
                                                }

                                            });
                                            event.preventDefault();
                                        });
                                    }

                                    for (var l = 0; l < like.length; ++l) {
                                        like[l].addEventListener("click", function (event) {
                                            $.ajax({
                                                async:false,
                                                url: this.href,
                                                type: "get",
                                                success: function (res) {
                                                    console.log(res);
                                                    if(res === "success")
                                                        alert("success");
                                                    else {
                                                        alert(res);
                                                        window.location.href = "http://localhost:8080/session";
                                                    }
                                                }
                                            });
                                            event.preventDefault();
                                        });
                                    }
                                }
                            </script>

                            <script>
                                function spellCheck(data) {
                                    var content = "";
                                    content += "<span>";
                                    content += "你要搜的是不是这个：";
                                    content += "</span>";
                                    for(var item in data) {
                                        content += "&nbsp;";
                                        content += "<a name=\"spellCheck\" href=\"" +
                                            "${pageContext.request.contextPath}/search/" + data[item] + "\">" + data[item] + "</a>"
                                    }
                                    var spellCheckView = document.getElementById("checkSpellView");
                                    spellCheckView.style.display="block";

                                    spellCheckView.innerHTML = content;
                                }
                            </script>

                            <script>
                                function addEventForSpell() {
                                    var spellCheckKeyWords = document.getElementsByName("spellCheck");
                                    for (var i = 0; i < spellCheckKeyWords.length; ++i) {
                                        spellCheckKeyWords[i].addEventListener("click", function (event) {
                                            $('#searchContext').val(this.textContent);
                                            searchEvent();
                                            event.preventDefault();
                                        });
                                    }
                                }
                            </script>

                            <script>
                                function renderRecommend(res) {
                                    var page = "";

                                    for(var item in res) {
                                        page += "<div class='col-md-8'>";
                                        page += "<div class=\"ibox float-e-margins\">";
                                        page += "<div class=\"ibox-title\">";
                                        page += "<h5>" + "<a href=\"" + "${pageContext.request.contextPath}/note/" + res[item].docId + "\">" + res[item].title + "</a>" + "</h5>";
                                        page += "</div>";
                                        page += "<div class=\"ibox-content\">";
                                        page += "<div class=\"row\">";
                                        page += "<a href=\"" + "${pageContext.request.contextPath}/note/" + res[item].docId + "\">";
                                        page += "<img src=\"" + res[item].imageUrls[0] + "\" class=\"img-preview\">";
                                        page += "</a>";
                                        page += "</div>";
                                        page += "</div>";
                                        page += "</div>";
                                        page += "</div>";
                                    }
                                    var recommendView = document.getElementById("recommendView");
                                    recommendView.innerHTML = page;

                                }
                            </script>
                        </li>
                    </ul>


                    <ul class="nav navbar-top-links navbar-right">
                        <li>
                            <a href="/">
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


        <div class="wrapper wrapper-content ">
            <div class="container-fluid col-lg-8">
                <div id="checkSpellView" class="display: none; row"></div>
                <div id="searchNumber" class="display: none; row"></div>
                <div id="content" class="row">
                    <div class="row">
<%--                        <div class="col-md-2">--%>
<%--                            <div class="ibox float-e-margins">--%>
<%--                                <a href="#">--%>
<%--                                    <img src="${pageContext.request.contextPath}/images/avatar/p2.jpg" class="img-preview">--%>
<%--                                </a>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <div class="col-md-8">--%>
<%--                            <div class="ibox float-e-margins">--%>
<%--                                <h3><a href="#">西湖</a></h3>--%>
<%--                                <span style="color:grey">2019年5月15日 - </span>--%>
<%--                                西湖，位于浙江省杭州市西湖区龙井路1号，杭州市区西部，景区总面积49平方千米，汇水面积为21.22平方千米，湖面面积为6.38平方千米。--%>
<%--                                西湖南、西、北三面环山，湖中白堤、苏堤、杨公堤、赵公堤将湖面分割成若干水面。西湖的湖体轮廓呈近椭圆形，湖底部较为平坦。湖泊天然地表水源是金沙涧、龙泓涧、赤山涧（慧因涧）、长桥溪四条溪流。西湖地处中国东南丘陵边缘和亚热带北缘--%>
<%--                                ...--%>
<%--                                <div>--%>
<%--                                    <a href="#">收藏</a>--%>
<%--                                    <a href="#">点赞</a>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                    </div>
                </div>
                <div id="page"></div>

                <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-page.js"></script>
                <script type="text/javascript">
                    $(function () {
                        $("#page").Page({
                            totalPages: 1, //分页总数
                            liNums: 7, //分页的数字按钮数(建议取奇数)
                            activeClass: 'activP', //active 类样式定义
                            callBack: function (page) {
                                var oldPage = sessionStorage.getItem("page");
                                var oldPath = "/search/" + sessionStorage.getItem("keyword");
                                var path = oldPath + "?page=" + page;
                                console.log(path);
                                if(oldPage === null || oldPage !== page) {
                                    $.ajax({
                                        async:false,
                                        url: path,
                                        type: "get",
                                        contentType: "application/json; charset=UTF-8",
                                        dataType: "json",
                                        success: function (res) {
                                            var data = res["documentSet"];
                                            var recommendDocumentSet = res["recommendDocumentSet"];

                                            render(data);
                                            renderRecommend(recommendDocumentSet);
                                            addEventForLikeAndCollect();

                                            sessionStorage.setItem("lastJson", JSON.stringify(data));
                                            sessionStorage.setItem("lastUrl", path);
                                            sessionStorage.setItem("page", page);
                                            sessionStorage.setItem("recommendDoc", JSON.stringify(recommendDocumentSet));

                                            $(document).scrollTop(0);

                                        }
                                    });
                                }
                                console.log(page)
                            }
                        });
                    })
                </script>
            </div>
            <div class="container-fluid col-lg-4">
                <div id="recommendView" class="row">
<%--                    <div class="col-md-8">--%>
<%--                        <div class="ibox float-e-margins">--%>
<%--                            <div class="ibox-title">--%>
<%--                                <h5>西湖</h5>--%>
<%--                            </div>--%>
<%--                            <div class="ibox-content">--%>
<%--                                <div class="row">--%>
<%--                                    <a href="#">--%>
<%--                                        <img src="${pageContext.request.contextPath}/images/avatar/p3.jpg" class="img-preview">--%>
<%--                                    </a>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <div class="col-md-8">--%>
<%--                        <div class="ibox float-e-margins">--%>
<%--                            <div class="ibox-title">--%>
<%--                                <h5>西湖</h5>--%>
<%--                            </div>--%>
<%--                            <div class="ibox-content">--%>
<%--                                <div class="row">--%>
<%--                                    <a href="#">--%>
<%--                                        <img src="${pageContext.request.contextPath}/images/avatar/p3.jpg" class="img-preview">--%>
<%--                                    </a>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                </div>
            </div>
        </div>
    </div>
</div>



<script>



    //滚动时保存滚动位置
    $(window).scroll(function(){
        if($(document).scrollTop()!==0){
            sessionStorage.setItem("offsetTop", $(window).scrollTop());
        }
    });
    $(function() {
        var url = sessionStorage.getItem("lastUrl");
        var data = JSON.parse(sessionStorage.getItem("lastJson"));
        var keyWord = sessionStorage.getItem("keyword");
        var offset = sessionStorage.getItem("offsetTop");
        var resultCount = sessionStorage.getItem("searchResultCount");
        var recommendDoc = JSON.parse(sessionStorage.getItem("recommendDoc"));

        if(url !== null && data !== null) {
            render(data);
            renderRecommend(recommendDoc);
            addEventForLikeAndCollect();
            $('#searchContext').val(keyWord);
            $(document).scrollTop(offset);
            $("#page").Page({
                totalPages: resultCount / 10 //分页总数
            })
        }
    });

</script>


</body>
</html>
