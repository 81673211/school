<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_customerEdit=avalon.define({
        $id:'customerEdit',
//         data:${caseAuthApplyVo},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>编辑客户</title>
</head>
<body :controller="customerEdit">
    <div class="mt-30">
        <form id="customerForm" action="${ctx}/customer/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">头像：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<input type="hidden" id="id" name="id" value="${customer.id}">
                    <input class="input-text" autocomplete="off" placeholder="" name="avatar" value="${customer.avatar}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">名称：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="name" value="${customer.name}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">性别：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="sex" value="${customer.sex}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">手机号：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="phone" value="${customer.phone}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">住址：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="addr" value="${customer.addr}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">电子邮箱：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="email" value="${customer.email}" type="text">
                </div>
            </div>
<!--              <div class="row cl mb-15"> -->
<!--                 <label class="form-label col-xs-2 col-sm-3 text-r">状态：</label> -->
<!--                 <div class="formControls col-xs-10 col-sm-9"> -->
<!--                 	<select class="input-text" name="status"> -->
<%--                 		<c:forEach items="${customerStatusMap}" var="status"> --%>
<%-- 	                		<option value="${status.key}" <c:if test="${status.key == customer.status}">selected="selected"</c:if>>${status.value}</option> --%>
<%--                 		</c:forEach> --%>
<!--                 	</select> -->
<!--                 </div> -->
<!--             </div> -->
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/customer/customerEdit.js"></script>
</body>
</html>