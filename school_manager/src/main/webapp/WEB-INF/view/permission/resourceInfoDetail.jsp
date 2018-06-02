<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_resourceInfoDetail=avalon.define({
        $id:'resourceInfoDetail',
        data:${resourceInfo}
    });
    </script>
    <title>资源详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="resourceInfoDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">资源名称：</div>
                    <div class="col-xs-8">
                        <p>{{@data.resName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">资源地址：</div>
                    <div class="col-xs-8">
                        <p>{{@data.resUrl}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">父级资源：</div>
                    <div class="col-xs-8">
                        <p>{{@data.parentResName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">菜单：</div>
                    <div class="col-xs-8">
                        <p>{{@data.menuName}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>