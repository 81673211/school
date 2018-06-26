<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_customerDetail=avalon.define({
        $id:'customerDetail',
        data:${customer}
//         customerStatusMap:${customerStatusMap}
    });
    </script>
    <title>客户详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="customerDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
<!--                 <div class="row cl"> -->
<!--                     <div class="col-xs-4 text-r">头像：</div> -->
<!--                     <div class="col-xs-8"> -->
<!--                         <p>{{@data.avatar}}</p> -->
<!--                     </div> -->
<!--                 </div> -->
                <div class="row cl">
                    <div class="col-xs-4 text-r">名称：</div>
                    <div class="col-xs-8">
                        <p>{{@data.name}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">性别：</div>
                    <div class="col-xs-8">
                        <p>{{@data.sex}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">手机号：</div>
                    <div class="col-xs-8">
                        <p>{{@data.phone}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">住址：</div>
                    <div class="col-xs-8">
                        <p>{{@data.addr}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">电子邮箱：</div>
                    <div class="col-xs-8">
                        <p>{{@data.email}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">创建时间：</div>
                    <div class="col-xs-8">
                        <p>{{@data.createdTime|date('yyyy-MM-dd HH:mm:ss')}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">修改时间：</div>
                    <div class="col-xs-8">
                        <p>{{@data.modifiedTime|date('yyyy-MM-dd HH:mm:ss')}}</p>
                    </div>
                </div>
<!--                 <div class="row cl"> -->
<!--                     <div class="col-xs-4 text-r">状态：</div> -->
<!--                     <div class="col-xs-8"> -->
<!--                         <p>{{@customerStatusMap[@data.status]}}</p> -->
<!--                     </div> -->
<!--                 </div> -->
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>