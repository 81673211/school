<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_orderDetail=avalon.define({
        $id:'orderDetail',
        data:${order},
        orderStatusMap:${orderStatusMap}
    });
    </script>
    <title>订单详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="orderDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">快递ID：</div>
                    <div class="col-xs-8">
                        <p>{{@data.expressId}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">订单金额：</div>
                    <div class="col-xs-8">
                        <p>{{@data.amount}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">是否已完成：</div>
                    <div class="col-xs-8">
                        <p>{{@data.isFinished}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">创建时间：</div>
                    <div class="col-xs-8">
                        <p>{{@data.createdTime}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">修改时间：</div>
                    <div class="col-xs-8">
                        <p>{{@data.modifiedTime}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">状态：</div>
                    <div class="col-xs-8">
                        <p>{{@orderStatusMap[@data.status]}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>