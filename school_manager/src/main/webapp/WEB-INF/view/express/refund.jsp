<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_refundOrder=avalon.define({
        $id:'refundOrder',
//         data:${caseAuthApplyVo},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>订单退款</title>
</head>
<body :controller="refundOrder">
    <div class="mt-30">
        <form id="orderRefundForm" action="${ctx}/express/expressSend/refund.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">快递单号：</label>
                <div class="formControls col-xs-9 col-sm-9">
                	<input type="hidden" id="id" name="id" value="${expressSend.id}">
                	<input type="hidden" id="expressSendNo" name="expressSendNo" value="${expressSend.code}">
<%--                     <input class="input-text" autocomplete="off" placeholder="" name="orderNo" value="${orderInfo.orderNo}" type="text"> --%>
                    <p>${expressSend.code}</p>
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">退款金额：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="refundAmt" value="" type="text">
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/express/refundOrder.js"></script>
</body>
</html>