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
                            用户信息
                        </div>
                        <div class="panel-body">
                            <#if userPage?? && (userPage.content?size>0)>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>uno</th>
                                        <th>open_id</th>
                                        <th>用户昵称</th>
                                        <th>手机</th>
                                        <th>状态</th>
                                        <th>应缴押金</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list userPage.content as user>                                            <tr>
                                                <td>${user.uno!}</td>
                                                <td>${user.openId!}</td>
                                                <td>${user.username!}</td>
                                                <td>${user.tel!}</td>
                                                <td>
                                                    <#if user.UStatus??>
                                                        <#switch user.UStatus>
                                                            <#case 0>
                                                                未缴纳押金
                                                            <#break >
                                                            <#case 1>
                                                                正常
                                                            <#break >
                                                            <#case 2>
                                                                冻结
                                                            <#break >
                                                        </#switch>
                                                    </#if>
                                                </td>
                                                <td>￥${user.rent!}</td>
                                            </tr>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>

                            <#--<div class="row">-->
                                <#--<div class="col-md-6 offset-md-3">-->
                            <ul class="pagination" style="margin-left: 27px;">
                                <li class="page-item">
                                    <a class="page-link previous" href="javascript:void(0);" onclick="toPage(0);">
                                        首页
                                    </a>
                                </li>

                                <li>
                                    <#if userPage.isFirst()>
                                        <a class="page-link" href="javascript:void(0);" disabled="disabled">
                                            上一页
                                        </a>
                                    <#else>
                                        <a class="page-link" href="javascript:void(0)" onclick="toPage(${userPage.getNumber() - 1})">
                                            上一页
                                        </a>
                                    </#if>
                                </li>

                                <li class="page-item active">
                                    <a class="page-link current_page">
                                        <#if userPage.getTotalPages() == 0>
                                            第<strong>${userPage.getNumber() + 1}</strong>页/共<strong>${userPage.getTotalPages() + 1}</strong>页
                                        <#else>
                                            第<strong>${userPage.getNumber() + 1}</strong>页/共<strong>${userPage.getTotalPages()}</strong>页
                                        </#if>
                                    </a>
                                </li>

                                <li class="page-item">
                                    <#if userPage.isLast()>
                                        <a class="page-link" href="javascript:void(0);" disabled="disabled">
                                            下一页
                                        </a>
                                    <#else>
                                        <a class="page-link" href="javascript:void(0)" onclick="toPage(${userPage.getNumber() + 1});">
                                            下一页
                                        </a>
                                    </#if>
                                </li>

                                <li class="page-item">
                                    <a class="page-link" href="javascript:void(0);" onclick="toPage(${userPage.getTotalPages() - 1});">
                                        尾页
                                    </a>
                                </li>
                            </ul>
                                <#--</div>-->
                            <#--</div>-->
                                
                                <#else >
                                <p>暂无用户信息</p>
                            </#if>
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
    function toPage(index) {

        var currentPage = index;
        var maxsize = "${userPage.getTotalPages()}";

        if(maxsize == '0'){
            maxsize = '1';
        }

        if(currentPage > maxsize - 1 || currentPage < 0){
            return;
        }

        window.location.href = "/admin/listUsers?currentPage=" + currentPage;
    }
</script>

</body>