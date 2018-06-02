<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <script type="text/javascript">
    var vm_goodBrandEdit=avalon.define({
        $id:'goodBrandEdit',
//         data:${caseAuthApplyVo},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>编辑商品品牌</title>
</head>
<body :controller="goodBrandEdit">
    <div class="mt-30">
        <form id="goodBrandForm" action="${ctx}/good/goodBrand/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">品牌名称：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<input type="hidden" name="id" value="${goodBrand.id}">
                    <input class="input-text" autocomplete="off" placeholder="" name="brandName" value="${goodBrand.brandName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-3 text-r">logo：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="logoPath" value="${goodBrand.logoPath}" type="text">
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/good/goodBrandEdit.js"></script>
</body>
</html>