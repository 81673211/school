<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_roleDetail=avalon.define({
        $id:'roleDetail',
        data:${role},
        zNodes:${zNodes},
    });
    </script>
    <title>角色详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="roleDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">角色名称：</div>
                    <div class="col-xs-8">
                        <p>{{@data.roleName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">拥有资源：</div>
                    <div class="col-xs-8">
                        <div class="zTreeDemoBackground left">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">备注：</div>
                    <div class="col-xs-8">
                        <p>{{@data.remark}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/model/permission/roleDetail.js"></script>
</body>
</html>