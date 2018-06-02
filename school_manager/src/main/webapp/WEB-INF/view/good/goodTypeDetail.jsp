<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_goodTypeDetail=avalon.define({
        $id:'goodTypeDetail',
        data:${goodType},
        goodTypeStatusMap:${goodTypeStatusMap}
    });
    </script>
    <title>商品类型详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="goodTypeDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">类型名称：</div>
                    <div class="col-xs-8">
                        <p>{{@data.typeName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">图片地址：</div>
                    <div class="col-xs-8">
                        <p>{{@data.picPath}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">父节点：</div>
                    <div class="col-xs-8">
                        <p>{{@data.pid}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">节点排序：</div>
                    <div class="col-xs-8">
                        <p>{{@data.sort}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">状态：</div>
                    <div class="col-xs-8">
                        <p>{{@goodTypeStatusMap[@data.status]}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>