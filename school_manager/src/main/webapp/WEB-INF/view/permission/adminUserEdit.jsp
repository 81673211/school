<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_adminUserEdit=avalon.define({
        $id:'adminUserEdit',
        roles:${roles},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>编辑用户</title>
</head>
<body :controller="adminUserEdit">
    <div class="mt-30">
        <form id="adminUserForm" action="${ctx}/permission/adminUser/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">登陆账号：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<input id="id" type="hidden" name="id" value="${adminUser.id}">
                    <input id="loginName" class="input-text" <c:if test="${adminUser.id != null}">disabled="disabled"</c:if> <c:if test="${adminUser.id == null}">name="loginName"</c:if> autocomplete="off" placeholder="" value="${adminUser.loginName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">用户名称：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="adminName" value="${adminUser.adminName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">手机号：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="adminPhone" value="${adminUser.adminPhone}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">角色：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <select class="input-text" name="roleId">
                		<c:forEach items="${roles}" var="role">
	                		<option value="${role.id}" <c:if test="${role.id == adminUser.roleId}">selected="selected"</c:if>>${role.roleName}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">状态：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<select class="input-text" name="status">
                		<c:forEach items="${adminUserStatusMap}" var="status">
	                		<option value="${status.key}" <c:if test="${status.key == adminUser.status}">selected="selected"</c:if>>${status.value}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/permission/adminUserEdit.js"></script>
</body>
</html>