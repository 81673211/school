<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="btn" uri="/WEB-INF/btn.tld"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!-- common -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/mycss.css" />
<script type="text/javascript" src="${ctx}/static/lib/avalon/2.0/avalon.js"></script>
<!--[if lt IE 9]>
<script type="text/javascript" src="${ctx}/static/lib/html5.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/respond.min.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/PIE_IE678.js"></script>
<![endif]-->

<script type="text/javascript">
function getCtx(){
	return "${ctx}";
}
</script>






