<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_expressBoxInfoEdit=avalon.define({
        $id:'expressBoxInfoEdit',
//         data:${caseAuthApplyVo},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>编辑柜子取件</title>
</head>
<body :controller="expressBoxInfoEdit">
    <div class="mt-30">
        <form id="expressBoxInfoForm" action="${ctx}/expressBoxInfo/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">快递编号：</label>
                <div class="formControls col-xs-9 col-sm-9">
                	<input type="hidden" id="id" name="id" value="${expressBoxInfo.id}">
                    <input class="input-text" autocomplete="off" placeholder="" name="expressCode" value="${expressBoxInfo.expressCode}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">快递柜组名：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="boxGroup" value="${expressBoxInfo.boxGroup}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">快递柜编号：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="boxNo" value="${expressBoxInfo.boxNo}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">状态：</label>
                <div class="formControls col-xs-9 col-sm-9">
                	<select class="input-text" name="status">
                		<c:forEach items="${expressBoxInfoStatusMap}" var="status">
	                		<option value="${status.key}" <c:if test="${status.key == expressBoxInfo.status}">selected="selected"</c:if>>${status.value}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/express/expressBoxInfoEdit.js"></script>
</body>
</html>