<nav class="navbar navbar-default top-navbar" role="navigation">
    <div class="navbar-header">
        <a class="navbar-brand" href="javascript:void(0);">MyOSBM</a>
    </div>

    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="/admin/toLogin">
                        <i class="fa fa-sign-out fa-fw"></i> 登录/登出</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
    </ul>
</nav>
<#--</.top-navbar>-->

<nav class="navbar-default navbar-side" id="nav-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav" id="main-menu">

            <li>
                <a class="active-menu" href="/admin/listUsers"><i class="fa fa-user fa-fw"></i> 用户管理</a>
            </li>

            <li>
                <a href="/bike/toAdd"><i class="fa fa-bicycle"></i> 单车管理</a>
            </li>

            <li>
                <a href="#"><i class="fa fa-table"></i> 租赁管理<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href="/lease/toLeaseInfo">租赁明细</a>
                    </li>
                    <li>
                        <a href="/lease/toInsufficientLease">欠费明细</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="/admin/info"><i class="fa fa-edit"></i> 关于我们 </a>
            </li>
        </ul>
    </div>
</nav>