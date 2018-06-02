<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ page isErrorPage="true"%>
<%	
	//设置返回码200，避免浏览器自带的错误页面
	response.setStatus(200);
	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(exception.getMessage(), exception);
%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <title>500错误页面</title>
    <script type="text/javascript">
      var vm_500Manage=avalon.define({
          $id:'500Manage',
          methods:{
              closeLayer:'func'//关闭layer
          }
      });
    </script>
</head>

<body :controller="500Manage">
	<article class="page-404 minWP text-c page-500">
		<p class="error-title">
			<i class="Hui-iconfont va-m Hui-iconfont-suoding"></i><span class="va-m">服务器异常</span>
		</p>
		<p class="error-description">系统异常，请联系工作人员！</p>
	</article>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>