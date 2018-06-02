<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_goodBrandDetail=avalon.define({
        $id:'goodBrandDetail',
        data:${goodBrand}
    });
    </script>
    <title>商品品牌详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="goodBrandDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">品牌名称：</div>
                    <div class="col-xs-8">
                        <p>{{@data.brandName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">logo地址：</div>
                    <div class="col-xs-8">
                        <p>{{@data.logoPath}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">创建时间：</div>
                    <div class="col-xs-8">
                        <p>{{@data.createTime}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>