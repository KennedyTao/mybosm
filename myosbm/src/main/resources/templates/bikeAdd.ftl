<!DOCTYPE html>
<html lang="ch">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
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

    <script src="/static/assets/js/bootbox.js"></script>

    //map
    <script charset="utf-8" src="https://map.qq.com/api/js?v=2.exp&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77"></script>

    <style type="text/css">
        body{
            background: #09192A;
        }

        p{
            width:603px;
            padding-top:3px;
            overflow:hidden;
        }

        body, button, input, select, textarea {
            font: 12px/16px Verdana, Helvetica, Arial, sans-serif;
        }

        *{
            margin:0px;
            padding:0px;
        }

        #myMap{
            min-width:600px;
            min-height:767px;
        }
    </style>

    <script type="text/javascript">
        function init() {

            //直接加载地图
            //初始化地图函数  自定义函数名init
            //定义map变量 调用 qq.maps.Map() 构造函数   获取地图显示容器
            var map = new qq.maps.Map(document.getElementById("myMap"), {
                center: new qq.maps.LatLng(${lat!23.054314}, ${log!113.396834}),      // 地图的中心地理坐标。
                zoom: 16                                                 // 地图的中心地理坐标。
            });

            <#if bikeList??>
                <#list bikeList as bike>
                    <#--alert(${bike.latitude?string("#.###############")!0})-->
                    <#--alert(${bike.longitude?string("#.###############")!0})-->
                    var position = new qq.maps.LatLng(${bike.latitude?string("#.###############")!0}, ${bike.longitude?string("#.###############")!0})
                    var marker = new qq.maps.Marker({
                        position: position,
                        map: map
                    })
                    qq.maps.event.addListener(marker, "click", function (event) {
                        bootbox.confirm({
                            message: "移除该地点的单车吗?",
                            callback: function (res) {
                                if(res == true){
                                    $.ajax({
                                        type: "POST",
                                        url : "/bike/del",
                                        data : {
                                            "bno": ${bike.bno!0}
                                        },
                                        success: function (result) {
                                            if(result.res == "success"){
                                                bootbox.alert("删除 <b>成功</b>");
                                                window.location.href="/bike/toAdd?lat=" + event.latLng.getLat() + "&log=" + event.latLng.getLng();
                                            }else{
                                                var msg = result.msg;
                                                bootbox.alert("删除 <b>失败</b>.."+ msg)
                                            }
                                        },
                                        error: function () {
                                            bootbox.alert("删除 <b>失败</b>..请重试")
                                        }
                                    })
                                }
                            }
                        })
                    })
                </#list>
            </#if>

            //添加监听事件  获取鼠标点击事件
            qq.maps.event.addListener(map, 'click', function(event) {

                // alert(event.latLng.getLat())
                // alert(event.latLng.getLng())

                bootbox.confirm({
                    size: "normal",
                    message: "在此处添加单车吗?",
                    callback: function(result){
                        /* result is a boolean; true = OK, false = Cancel*/

                        if(result == true){
                            $.ajax({
                                type: "POST",
                                url : "/bike/add",
                                data : {
                                    "latitude": event.latLng.getLat(), //北纬
                                    "longitude": event.latLng.getLng()   //东经
                                },
                                success : function (result2) {
                                    if(result2.res == "success"){
                                        bootbox.alert("添加 <b>成功</b>");
                                        // var marker=new qq.maps.Marker({
                                        //     position:event.latLng,
                                        //     map:map
                                        // });
                                        window.location.href="/bike/toAdd?lat=" + event.latLng.getLat() + "&log=" + event.latLng.getLng();

                                    }else{
                                        var msg = result2.msg;
                                        bootbox.alert("添加 <b>失败</b>.."+ msg)
                                    }
                                },
                                error: function () {
                                    bootbox.alert("添加 <b>失败</b>..请重试")
                                }
                            })

                        }else {

                        }
                    }
                })
            });
        }
    </script>
</head>

<body onload="init()">
<div id="wrapper">
    <#include "head.ftl">
    <div id="page-wrapper" >
        <div id="page-inner">
            <div id="myMap"></div>
        </div>
    </div>
</div>

</body>
</html>