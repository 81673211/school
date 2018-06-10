<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_expressCompanyDetail=avalon.define({
        $id:'expressCompanyDetail',
        data:${expressCompany}
    });
    </script>
    <title>快递公司详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="expressCompanyDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">名称：</div>
                    <div class="col-xs-8">
                        <p>{{@data.name}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">编码：</div>
                    <div class="col-xs-8">
                        <p>{{@data.code}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">联系电话：</div>
                    <div class="col-xs-8">
                        <p>{{@data.phone}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">地址：</div>
                    <div class="col-xs-8">
                        <p>{{@data.addr}}</p>
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
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>