<%@ taglib prefix="itheima" uri="http://itheima.com/common/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ page import="cn.edu.zucc.caviar.searchengine.core.pojo.User" %><%--
  Created by IntelliJ IDEA.
  User: shiro
  Date: 19-5-3
  Time: 下午8:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>user</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>index</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

    <!-- Mainly scripts -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path + "/";
    %>


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

                <div class="row">
                    <div class="col-md-4">
                        <div class="ibox float-e-margins">
                            <img src="${pageContext.request.contextPath}/${user.avatar}"
                                 class=" img-rounded img-responsive">
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h2>用户名：${user.userName}</h2>
                            </div>
                            <div class="ibox-content">
                                <div class="row">
                                    <p>
                                        register id: ${user.registerId}
                                    </p>
                                </div>
                                <div class="row">
                                    <p>
                                        describe: ${user.describe}
                                    </p>
                                </div>
                                <div class="row">
                                    <p>
                                        register time: ${user.registerTime}
                                    </p>
                                </div>
                                <div class="row">
                                    <p>
                                        last login time: ${user.lastLoginTime}
                                    </p>
                                </div>

                            </div>
                        </div>
                    </div>
                    <button id="modifyButton" type="button" class="btn btn-danger" data-toggle="modal" data-target="#userModal" style="display: none">
                        modify
                    </button>

                    <script>
                        var button = document.getElementById("modifyButton");
                        var list = window.location.href.split("/");
                        var user = list[list.length - 1];
                        console.log(user);
                        console.log(<%=user.getUserId()%>);

                        if (user == <%=user.getUserId()%>) {
                            button.style.display = "block";
                        }
                    </script>
                </div>
                <div class="row">
                    <h2>收藏文章</h2>
                </div>
<%--                <table class="table table-hover">--%>
<%--                    <thead>--%>
<%--                    <tr>--%>
<%--                        <th>文章标题</th>--%>
<%--                        <th>文章简介</th>--%>
<%--                    </tr>--%>
<%--                    </thead>--%>
<%--                    <tbody>--%>
<%--                    <tr>--%>
<%--                        <th>标题</th>--%>
<%--                        <th>&&简介</th>--%>
<%--                    </tr>--%>
<%--                    <tr>--%>
<%--                        <th>标题</th>--%>
<%--                        <th>&&简介</th>--%>
<%--                    </tr>--%>
<%--                    </tbody>--%>

<%--                </table>--%>

                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">note collection</div>
                            <!-- /.panel-heading -->
                            <table class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>name</th>
                                    <th>describe</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.rows}" var="row">
                                    <tr>
                                        <td><a href="/note/${row.noteId}"> ${row.title}</a></td>
                                        <td>${row.describe}</td>
                                        <td>
                                            <a href="#" id="deleteCollection" class="btn btn-danger btn-xs" onclick="deleteCustomer('${row.noteId}')" >删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="col-md-12 text-right">
                                <itheima:page url="${pageContext.request.contextPath}/user/${user.userId}" />
                            </div>

                            <script>
                                function deleteCustomer(id) {
                                    if(confirm('delele this note?')) {
                                        $.ajax({
                                            url: "<%=basePath%>collect/" + id,
                                            type: "delete",
                                            success: function (data) {
                                                if(data =="success"){
                                                    alert("success！");
                                                    window.location.reload();
                                                }else{
                                                    alert("fail！");
                                                    window.location.reload();
                                                }
                                            }
                                        });
                                        return false;
                                    }
                                }
                            </script>
                            <!-- /.panel-body -->
                        </div>
                        <!-- /.panel -->
                    </div>
                    <!-- /.col-lg-12 -->
                </div>


                <div class="modal fade" id="userModal" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h4 class="modal-title" id="myModalLabel">User Information</h4>
                            </div>
                            <div class="modal-body">
                                <form class="form-horizontal" id="register_user_form">
                                    <div class="form-group">
                                        <label for="user_name" class="col-sm-2 control-label">user name</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="user_name"
                                                   placeholder="${user.userName}" required/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="describe" class="col-sm-2 control-label">describe</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="describe"
                                                   placeholder="${user.describe}" required/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="gender" class="col-sm-2 control-label">gender</label>
                                        <div class="col-sm-10">
                                            <select id="gender" class="form-control">
                                                <option value='' disabled selected
                                                        style='display:none;'>${user.gender}</option>
                                                <option>male</option>
                                                <option>female</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="password" class="col-sm-2 control-label">password</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" id="password"/>
                                        </div>
                                    </div>
                                </form>
                                <div class="form-group">
                                    <label for="fileForm" class="col-sm-2 control-label">avatar</label>
                                    <div class="col-sm-10">
                                        <form id="fileForm" enctype="multipart/form-data" method="post"
                                              action="${pageContext.request.contextPath}/fileUpload">
                                            <input id="file" type="file" name="uploadfile" multiple="multiple">
                                            <input type="button" value="upload" onclick="uploadFile()">
                                        </form>

                                        <input id="filePath" hidden value="">
                                    </div>

                                    <script>

                                        function uploadFile() {
                                            var formData = new FormData($("#fileForm")[0]);

                                            $.ajax({
                                                url: "${pageContext.request.contextPath}/fileUpload",
                                                type: "post",
                                                data: formData,
                                                async: false,
                                                contentType: false,
                                                processData: false,
                                                success: function (res) {
                                                    if(res === null || res.data === "FAIL") {
                                                        alert("upload faile")
                                                    }
                                                    else {
                                                        $("#filePath").val(res);
                                                        alert(res);
                                                    }
                                                }
                                            });
                                            return false;
                                        }
                                    </script>

                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button type="button" class="btn btn-primary" onclick="updateUser()">commit</button>
                            </div>

                            <script>
                                function updateUser() {

                                    var username = $('#user_name').val();
                                    var describe = $('#describe').val();
                                    var gender = $('#gender').val();
                                    var password = $('#password').val();


                                    if (password !== null && password !== undefined && password.length != 0 && password.length < 5) {
                                        alert("password too short");
                                        return false;
                                    }

                                    $.ajax({
                                        url: "${pageContext.request.contextPath}/user/" + <%=user.getUserId()%>,
                                        type: "put",
                                        data: JSON.stringify({
                                            describe: describe,
                                            password: password,
                                            gender: gender,
                                            avatar: $('#filePath').val(),
                                            userName: username
                                        }),
                                        contentType: "application/json; charset=UTF-8",
                                        dataType: "json",
                                        success: function (res) {
                                            alert("success");
                                            window.location.href = "${pageContext.request.contextPath}/user/" + res.userId;
                                        }
                                    });
                                    return false;
                                }
                            </script>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>
</div>


</body>
</html>
