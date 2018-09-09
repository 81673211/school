<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_expressLogDetail=avalon.define({
        $id:'expressLogDetail',
        data:${expressLog},
        expressTypes:${expressTypes}
    });
    </script>
    <title>日志详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="expressLogDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">快递单号：</div>
                    <div class="col-xs-8">
                        <p>{{@data.code}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">快递类型：</div>
                    <div class="col-xs-8">
                        <p>{{@expressTypes[@data.expressType]}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">操作：</div>
                    <div class="col-xs-8">
                        <p>{{@data.action}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">快递状态：</div>
                    <div class="col-xs-8">
                        <p>{{@data.status}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">操作时间：</div>
                    <div class="col-xs-8">
                        <p>{{@data.operateTime|date('yyyy-MM-dd HH:mm:ss')}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">操作人：</div>
                    <div class="col-xs-8">
                        <p>{{@data.operatorName}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>