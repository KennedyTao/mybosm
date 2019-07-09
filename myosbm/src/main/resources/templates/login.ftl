<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>MYOBSM</title>

    <#--<script type="text/javascript" src="/webjars/jquery/3.3.1/jquery.min.js"></script>-->
    <#--<script type="text/javascript" src="webjars/bootstrap/3.1.1/js/bootstrap.min.js"></script>-->
    <#--<link href="webjars/bootstrap/3.1.1/css/bootstrap.css" rel="stylesheet" />-->

    <!-- Bootstrap Styles-->
    <link href="/static/assets/css/bootstrap.css" rel="stylesheet" />
    <#--<script type="text/javascript" src="/static/assets/js/bootstrap.min.js"></script>-->

    <link href="/webjars/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet" />
    <link href="/static/assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
    <link href="/static/assets/css/custom-styles.css" rel="stylesheet" />
    <!-- Google Fonts-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

    <!-- JS Scripts-->
    <script src="/static/assets/js/jquery-1.10.2.js"></script>
    <!-- Bootstrap Js -->
    <script src="/static/assets/js/bootstrap.min.js"></script>
    <!-- Metis Menu Js -->
    <script src="/static/assets/js/jquery.metisMenu.js"></script>

    <style type="text/css">
        body{
            background: #09192A;
        }
    </style>
</head>

<body>
<div id="wrapper">
    <#include "head.ftl">
    <div id="page-wrapper" >
        <div id="page-inner">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">
                        管理员登录 <small>登录后才可访问后台功能</small>
                    </h1>
                </div>
            </div>
            <!-- /. ROW  -->


            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Messages
                        </div>

                        <div class="panel-body">
                            <form class="login_form" id="login_form" role="form" action="/admin/login" method="post">
                                <div class="form-group">
                                    <label>姓名</label>
                                    <input class="form-control" type="text" name="username" placeholder="Enter name" />
                                </div>

                                <div class="form-group">
                                    <label>密码</label>
                                    <input class="form-control" type="password" name="password" placeholder="Enter password" />
                                </div>

                                <button type="button" class="btn btn-success" onclick="checkLogin();">submit</button>
                                <button type="button" id="input_msg" class="btn btn-danger" data-toggle="modal" data-target="#myModal" style="display: none">提示</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    提示
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
            </div>
            <div class="modal-body" id="info">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function checkLogin() {
        var username = $("input[name='username']").val();
        var password = $("input[name='password']").val();

        var passwordReg = /^[a-zA-Z0-9]+/;

        var errorMsg = '';

        if(username == null || password == null || username == '' || password == ''){
            errorMsg = '输入内容不能为空';

        }else if(!passwordReg.test(password)){
            errorMsg = '密码含有非法字符';
            $("input[name='password']").val("");
            $("input[name='password']").focus();

        }else{

        }

        if(errorMsg == null || errorMsg == ''){
            $("#login_form").submit();

        }else{
            $("#info").text(errorMsg);
            $("#input_msg").click();
        }
    }

    $(document).ready(function () {
        var message = "${msg!}";

        if(message != null && message != '' ){
            $("#info").text(message);
            $("#input_msg").click();
        }
    })
</script>
</body>