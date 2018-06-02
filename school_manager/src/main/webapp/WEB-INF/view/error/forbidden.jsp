 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <title>没有权限</title>
    <script type="text/javascript">
      var vm_forbiddenManage=avalon.define({
          $id:'forbiddenManage',
          methods:{
              closeLayer:'func'//关闭layer
          }
      });
    </script>
</head>

<body :controller="forbiddenManage">
	<article class="page-404 minWP text-c page-forbidden">
		<p class="error-title">
			<i class="Hui-iconfont va-m Hui-iconfont-suoding"></i><span class="va-m">没有权限</span>
		</p>
		<p class="error-description">不好意思，您没有该操作的权限！</p>
	</article>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>