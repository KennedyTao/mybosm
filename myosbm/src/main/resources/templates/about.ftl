<!DOCTYPE html>
<html lang="ch">

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
                <div class="col-md-12 col-sm-12 col-xs-12">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            公司信息(客户展示)
                        </div>
                        <div class="panel-body">
                            <#if info??>
                            <form role="form" id="infoForm" method="post" action="/admin/setInfo">

                                <input type="text" class="form-control" value="${info.infoId!}" name="infoId" style="display: none;" />

                                <div class="form-group input-group">
                                    <span class="input-group-addon">@</span>
                                    <input type="text" class="form-control" placeholder="company" value="${info.companyName!}" name="companyName" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon"><i class="fa fa-phone-square"></i></span>
                                    <input type="tel" class="form-control" placeholder="tel" value="${info.tel!}" name="tel" />
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                                    <input type="email" class="form-control" placeholder="email" value="${info.email!}" name="email" />
                                </div>

                                <button type="button" class="btn btn-success" onclick="checkInfo();">submit</button>
                                <button type="button" id="input_msg" class="btn btn-danger" data-toggle="modal" data-target="#myModal" style="display: none">提示</button>
                            </form>
                                <#else >
                                    <p>查询出错了..</p>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<<#-- 模态框（Modal） -->
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
    function checkInfo() {
        var companyName = $("input[name='companyName']").val();
        var tel = $("input[name='tel']").val();
        var email = $("input[name='email']").val();

        var emailReg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;

        var errorMsg = '';

        if(email != null && email != '' && !emailReg.test(email)){
            errorMsg = '邮箱格式不正确';
            $("input[name='email]").val("");
            $("input[name='email']").focus();

        }else if((companyName != null && companyName.length > 50) || (tel != null && tel.length > 20)){
            errorMsg = '输入内容过长';
        }

        if(errorMsg == null || errorMsg == ''){
            $("#infoForm").submit();

        }else{
            $("#info").text(errorMsg);
            $("#input_msg").click();
        }
    }
</script>

</body>