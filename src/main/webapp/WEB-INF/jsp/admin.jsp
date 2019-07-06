<%--
  Created by IntelliJ IDEA.
  User: shiro
  Date: 19-6-4
  Time: 上午9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>admin page</title>

    <!-- Mainly scripts -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous">
    </script>

    <style>
        #userListView {
            width: 50em;
            margin-left: auto;
            margin-right: auto;
            margin-TOP: 100PX;
        }
        #loginView {
            width: 25em;
            margin-left: auto;
            margin-right: auto;
            margin-TOP: 100PX;
        }

    </style>

</head>
<body>

<div class="row" id="loginView">
    <div class="col-lg-12">

        <h2 align="center">管理员用户登录界面</h2>
        <!--下面是用户名输入框-->
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon">账号</span>
            <input id="userName" type="text" class="form-control" placeholder="用户名" aria-describedby="basic-addon">
        </div>
        <br>

        <script>
            $("userName").focus();
        </script>
        <!--下面是密码输入框-->
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">密码</span>
            <input id="passWord" type="password" class="form-control" placeholder="密码" aria-describedby="basic-addon1">
        </div>
        <br>


        <!--下面是登陆按钮,包括颜色控制-->
        <div align="center">
            <button  type="button" id="login" style="width:280px;" class="btn btn-default">登 录</button>
        </div>
        <%--<button type="button" id="register" style="width:280px;" class="btn btn-default">注 册</button>--%>

        <br>
        <div id="log"></div>
    </div>
</div>

<div class="row" style="display: none" id="userListView" >
    <div class="col-lg-12">
        <h2 align="center">用户管理界面</h2>
        <a href="#" id="deleteCollection" class="btn btn-danger btn-xs" onclick="adminLogout()">登出</a>

        <script>
            function adminLogout() {
                $.ajax({
                    async: false,
                    url: "/admin/logout",
                    type: "post",
                    contentType: "application/json; charset=UTF-8",
                    dataType: "text",
                    success: function (res) {
                        console.log(res);

                        // localStorage.setItem("last_login", userName);
                        // alert("登录成功");
                        sessionStorage.removeItem("data");
                        window.location.reload();
                        // window.location.href = "./index.html";
                    }
                });
            }
        </script>
        <div class="panel panel-default">
            <div class="panel-heading">user list</div>
            <!-- /.panel-heading -->
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th>user ID</th>
                    <th>register ID</th>
                    <th>name</th>
                    <th>last login time</th>
                    <th>valid</th>
                </tr>
                </thead>
                <tbody id="userListTBody">
                <tr>
<%--                    <td><a href="/note/${row.noteId}"> ${row.title}</a></td>--%>
<%--                    <td>hello</td>--%>
<%--                    <td>hellp</td>--%>
<%--                    <td>--%>
<%--                        <a href="#" id="deleteCollection" class="btn btn-danger btn-xs"--%>
<%--                           onclick="deleteCustomer('${row.noteId}')">禁用</a>--%>
<%--                    </td>--%>
                </tr>

                </tbody>
            </table>


            <script>

            </script>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>

<script>
    function loginEvent() {
        var userName = document.getElementById("userName").value;
        var passWord = document.getElementById("passWord").value;
        var log = document.getElementById("log");
        if (userName.length === 0 || passWord.length === 0) {
            log.innerHTML = "账号或者密码不能为空";
            return;
        }


        $.ajax({
            async: false,
            url: "/admin",
            type: "post",
            data: JSON.stringify({registerId: userName, password: passWord}),
            contentType: "application/json; charset=UTF-8",
            dataType: "json",
            success: function (res) {
                console.log(res);
                render(res);

                // localStorage.setItem("last_login", userName);
                // alert("登录成功");
                sessionStorage.setItem("data", JSON.stringify(res));
                // window.location.href = "./index.html";
            },
            error: function () {
                log.innerText = "Error username or Error password"
            }
        });
    }
</script>

<script>
    function render(res) {

        var loginView = document.getElementById("loginView");
        var userListView = document.getElementById("userListView");
        var tbody = document.getElementById("userListTBody");

        loginView.style.display = "none";
        userListView.style.display = "block";

        var html = "";
        for(var item in res) {
            html += "<tr>";
            html += "<td><a href=/user/" + res[item].userId + "> " + res[item].userId +"</a></td>";
            html += "<td>" + res[item].registerId + "</td>";
            html += "<td>" + res[item].userName + "</td>";
            html += "<td>" + new Date(res[item].lastLoginTime) + "</td>";
            html += "<td>" + res[item].valid + "</td>";
            html += "<td>";
            if(res[item].valid === 1) {
                html += "<a href='#' id='deleteCollection' class='btn btn-danger btn-xs' onclick='disableUser(" + res[item].userId + ")'>禁用</a>"
            }
            else {
                html += "<a href='#' id='deleteCollection' class='btn btn-danger btn-xs' onclick='enableUser(" + res[item].userId + ")'>恢复</a>"
            }

            html += "</td>";
            html += "</tr>";
        }
        tbody.innerHTML = html;
    }
</script>

<script>
    function enableUser(userID) {
        if(confirm('enable this user?')) {
            $.ajax({
                url: "/enable",
                type: "post",
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify({userId: userID}),
                success: function (data) {
                    if(data === "success"){
                        alert("success！");

                    }else{
                        alert("fail！");
                    }
                    loginEvent();

                }
            });
            return false;
        }
    }
    function disableUser(userID) {
        if(confirm('disable this user?')) {
            $.ajax({
                url: "/disable",
                type: "post",
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify({userId: userID}),
                success: function (data) {
                    if(data === "success"){
                        alert("success！");

                    }else{
                        alert("fail！");
                    }
                    loginEvent();
                }
            });
            return false;
        }
    }
</script>

<script>
    // var register = document.getElementById("register");
    // register.addEventListener("click", function () {
    //     window.location.href = "./register.html"
    // });
    var login = document.getElementById("login");

    login.addEventListener("click", loginEvent);

    $(document).keydown(function (event) {
        if(event.keyCode === 13) {
            loginEvent();
        }
    })

</script>


<script>

    $(window).scroll(function(){
        if($(document).scrollTop()!==0){
            sessionStorage.setItem("offsetTop", $(window).scrollTop());
        }
    });
    $(function() {
        var data = JSON.parse(sessionStorage.getItem("data"));
        var offset = sessionStorage.getItem("offsetTop");
        if(data !== null) {
            render(data);
            $(document).scrollTop(offset);
        }
    });
</script>



</body>
</html>
