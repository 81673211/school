<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_expressSendEdit=avalon.define({
        $id:'expressSendEdit',
//         data:${caseAuthApplyVo},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>编辑商品类型</title>
</head>
<body :controller="expressSendEdit">
    <div class="mt-30">
        <form id="expressSendForm" action="${ctx}/express/expressSend/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">快递单号：</label>
                <div class="formControls col-xs-9 col-sm-9">
                	<input type="hidden" id="id" name="id" value="${expressSend.id}">
                    <input class="input-text" autocomplete="off" placeholder="" name="code" value="${expressSend.code}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">寄件人姓名：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="senderName" value="${expressSend.senderName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">寄件人电话：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="senderPhone" value="${expressSend.senderPhone}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">寄件人地址：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="senderAddr" value="${expressSend.senderAddr}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">收件人姓名：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="receiverName" value="${expressSend.receiverName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">收件人电话：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="receiverPhone" value="${expressSend.receiverPhone}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">收件人地址：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="receiverAddr" value="${expressSend.receiverAddr}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">快递公司：</label>
                <div class="formControls col-xs-9 col-sm-9">
                	<select class="input-text" name="companyCode">
                		<c:forEach items="${expressCompanyList}" var="expressCompany">
	                		<option value="${expressCompany.code}" <c:if test="${expressCompany.code == expressSend.companyCode}">selected="selected"</c:if>>${expressCompany.name}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">状态：</label>
                <div class="formControls col-xs-9 col-sm-9">
                	<select class="input-text" name="expressStatus">
                		<c:forEach items="${expressSendStatusMap}" var="status">
	                		<option value="${status.key}" <c:if test="${status.key == expressSend.expressStatus}">selected="selected"</c:if>>${status.value}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/express/expressSendEdit.js"></script>
</body>
</html>