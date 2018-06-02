<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_adminUserDetail=avalon.define({
        $id:'adminUserDetail',
        data:${adminUser},
        adminUserStatusMap:${adminUserStatusMap}
    });
    </script>
    <title>用户详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="adminUserDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">登录账号：</div>
                    <div class="col-xs-8">
                        <p>{{@data.loginName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">用户名称：</div>
                    <div class="col-xs-8">
                        <p>{{@data.adminName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">手机号：</div>
                    <div class="col-xs-8">
                        <p>{{@data.adminPhone}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">角色：</div>
                    <div class="col-xs-8">
                        <p>{{@data.roleName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">状态：</div>
                    <div class="col-xs-8">
                        <p>{{@adminUserStatusMap[@data.status]}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>