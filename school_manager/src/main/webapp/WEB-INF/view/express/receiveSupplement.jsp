<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_receiveSupplement=avalon.define({
        $id:'receiveSupplement',
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>补单</title>
</head>
<body :controller="reOrder">
    <div class="mt-30">
        <form id="receiveSupplementForm" action="${ctx}/express/expressReceive/supplement.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-3 col-sm-3 text-r">补单金额：</label>
                <div class="formControls col-xs-9 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="supplementAmt" value="" type="text">
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/express/receiveSupplement.js"></script>
</body>
</html>