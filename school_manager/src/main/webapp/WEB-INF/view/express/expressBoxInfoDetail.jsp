<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_expressBoxInfoDetail=avalon.define({
        $id:'expressBoxInfoDetail',
        data:${expressBoxInfo},
        expressBoxInfoStatusMap:${expressBoxInfoStatusMap}
    });
    </script>
    <title>柜子取件详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="expressBoxInfoDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">快递单号：</div>
                    <div class="col-xs-8">
                        <p>{{@data.expressCode}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">柜子组编号：</div>
                    <div class="col-xs-8">
                        <p>{{@data.boxGroup}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">柜子编号：</div>
                    <div class="col-xs-8">
                        <p>{{@data.boxNo}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">创建时间：</div>
                    <div class="col-xs-8">
                        <p>{{@data.createdTime|date('yyyy-MM-dd HH:mm:ss')}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">状态：</div>
                    <div class="col-xs-8">
                        <p>{{@expressBoxInfoStatusMap[@data.status]}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>