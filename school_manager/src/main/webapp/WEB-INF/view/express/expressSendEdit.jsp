<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_goodTypeEdit=avalon.define({
        $id:'goodTypeEdit',
//         data:${caseAuthApplyVo},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>编辑商品类型</title>
</head>
<body :controller="goodTypeEdit">
    <div class="mt-30">
        <form id="goodTypeForm" action="${ctx}/good/goodType/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">类型名称：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<input type="hidden" id="id" name="id" value="${goodType.id}">
                    <input class="input-text" autocomplete="off" placeholder="" name="typeName" value="${goodType.typeName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">图片地址：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="picPath" value="${goodType.picPath}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">父节点：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="pid" value="${goodType.pid}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">节点排序：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="sort" value="${goodType.sort}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">状态：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<select class="input-text" name="status">
                		<c:forEach items="${goodTypeStatusMap}" var="status">
	                		<option value="${status.key}" <c:if test="${status.key == goodType.status}">selected="selected"</c:if>>${status.value}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/good/goodTypeEdit.js"></script>
</body>
</html>