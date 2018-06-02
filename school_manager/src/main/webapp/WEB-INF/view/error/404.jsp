<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <title>404</title>
    <script type="text/javascript">
      var vm_404Manage=avalon.define({
          $id:'404Manage',
          methods:{
              closeLayer:'func'//关闭layer
          }
      });
    </script>
</head>

<body :controller="404Manage">
	<article class="page-404 minWP text-c">
		<p class="error-title">
			<i class="Hui-iconfont va-m Hui-iconfont-face-ku"></i><span class="va-m">404</span>
		</p>
		<p class="error-description">不好意思，您访问的页面不存在！</p>
	</article>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>