<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_resourceInfoEdit=avalon.define({
        $id:'resourceInfoEdit',
        menus:${menus},
        parentResources:${parentResources},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>编辑资源</title>
</head>
<body :controller="resourceInfoEdit">
    <div class="mt-30">
        <form id="resourceInfoForm" action="${ctx}/permission/resourceInfo/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">资源名称：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<input id="id" type="hidden" name="id" value="${resourceInfo.id}">
                    <input class="input-text" autocomplete="off" placeholder="" name="resName" value="${resourceInfo.resName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">资源地址：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="resUrl" value="${resourceInfo.resUrl}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">父级资源：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <select class="input-text" name="parentResId">
               			<option value="0">无</option>
                		<c:forEach items="${parentResources}" var="parentResource">
	                		<option value="${parentResource.id}" <c:if test="${parentResource.id == resourceInfo.parentResId}">selected="selected"</c:if>>${parentResource.resName}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">菜单：</label>
                <div class="formControls col-xs-10 col-sm-9">
					<select class="input-text" name="parentResId">
						<c:forEach items="${menus}" var="menu">
	                		<option value="${menu.id}" <c:if test="${menu.id == resourceInfo.menuId}">selected="selected"</c:if>>${menu.resourceName}</option>
	               		</c:forEach>
               		</select>
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/permission/resourceInfoEdit.js"></script>
</body>
</html>