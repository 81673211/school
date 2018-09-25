<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_expressReceiveEdit=avalon.define({
        $id:'expressReceiveEdit',
//         data:${caseAuthApplyVo},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>编辑收件</title>
</head>
<body :controller="expressReceiveEdit">
    <div class="mt-10">
        <form id="expressReceiveForm" action="${ctx}/express/expressReceive/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">快递单号：</label>
                <div class="formControls col-xs-9 col-sm-9">
                	<input type="hidden" id="id" name="id" value="${expressReceive.id}">
                    <input class="input-text" autocomplete="off" placeholder="" name="code" value="${expressReceive.code}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">寄件人姓名：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="senderName" value="${expressReceive.senderName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">寄件人电话：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="senderPhone" value="${expressReceive.senderPhone}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">寄件人地址：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="senderAddr" value="${expressReceive.senderAddr}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">收件人姓名：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="receiverName" value="${expressReceive.receiverName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">收件人电话：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="receiverPhone" value="${expressReceive.receiverPhone}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">收件人地址：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="receiverAddr" value="${expressReceive.receiverAddr}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">快递公司：</label>
                <div class="formControls col-xs-9 col-sm-9">
                	<select class="input-text" name="companyCode">
                		<c:forEach items="${expressCompanyList}" var="expressCompany">
	                		<option value="${expressCompany.code}" <c:if test="${expressCompany.code == expressReceive.companyCode}">selected="selected"</c:if>>${expressCompany.name}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
            <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">重量：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="expressWeight" value="${expressReceive.expressWeight}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">状态：</label>
                <div class="formControls col-xs-9 col-sm-9">
                	<select class="input-text" name="expressStatus">
                		<c:forEach items="${expressReceiveStatusMap}" var="status">
	                		<option value="${status.key}" <c:if test="${status.key == expressReceive.expressStatus}">selected="selected"</c:if>>${status.value}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/express/expressReceiveEdit.js"></script>
</body>
</html>