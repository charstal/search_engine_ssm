<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="cn.edu.zucc.caviar.searchengine.core.pojo.User" %><%--
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
    <div style="height: 30px"></div>
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
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#registerModal">
                    Sign up
                </button>
            </div>

        </div>

        <div class="modal fade" id="registerModal" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel">Register user</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" id="register_user_form">
                            <div class="form-group">
                                <label for="register_id" class="col-sm-2 control-label">email</label>
                                <div class="col-sm-10">
                                    <input type="email" class="form-control" id="register_id" placeholder="email" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="user_name" class="col-sm-2 control-label">user name</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="user_name" placeholder="user name" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="gender" class="col-sm-2 control-label">gender</label>
                                <div class="col-sm-10">
                                    <select id="gender" class="form-control">
                                        <option>male</option>
                                        <option>female</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-sm-2 control-label">password</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="password" required />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="rePassword" class="col-sm-2 control-label">repeat password</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="rePassword"/>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="registerUser()">commit</button>
                    </div>

                    <script>
                        function registerUser() {
                            var email = $('#register_id').val();
                            var username = $('#user_name').val();
                            var gender = $('#gender').val();
                            var password1 = $('#password').val();
                            var password2 = $('#rePassword').val();

                            if(password1 === null || password2 === null || password1 !== password2) {
                                alert("password not same");
                                return false;
                            }

                            if(password1.length < 6) {
                                alert("password too short");
                                return false;
                            }


                            $.ajax({
                                url: "${pageContext.request.contextPath}/user",
                                type: "post",
                                data: JSON.stringify({registerId: email, password: password1, gender: gender, userName: username}),
                                contentType: "application/json; charset=UTF-8",
                                dataType: "json",
                                success: function(res) {
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
                        if (res.userId !== undefined && res.valid === 1) {
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

                                window.location.href="${pageContext.request.contextPath}/user/" + res.userId;
                            } else {
                                $.removeCookie('username');
                                $.removeCookie('password');
                                $.removeCookie('bit');
                            }
                        }
                        else if(res.valid !== 1) {
                            alert("登录失败：" + "验证不通过");
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


    <script>
        function reRenderView() {
            var deleteFrom = document.getElementById("loginForm");
            deleteFrom.parentNode.removeChild(deleteFrom);
        }

    </script>
</div>

</body>
</html>
