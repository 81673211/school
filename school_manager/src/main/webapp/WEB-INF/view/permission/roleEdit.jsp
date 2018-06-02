<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_roleEdit=avalon.define({
        $id:'roleEdit',
        zNodes:${zNodes},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>编辑角色</title>
</head>
<body :controller="roleEdit">
    <div class="mt-30">
        <form id="roleForm" action="${ctx}/permission/role/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">角色名称：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<input id="id" type="hidden" name="id" value="${role.id}">
                    <input class="input-text" autocomplete="off" placeholder="" name="roleName" value="${role.roleName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">拥有资源：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<div class="zTreeDemoBackground left">
						<ul id="treeDemo" class="ztree"></ul>
						<input type="hidden" name="resIds" id="resIds">
					</div>
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">备注：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="remark" value="${role.remark}" type="text">
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/permission/roleEdit.js"></script>
</body>
</html>