<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
	<title>登录页面</title>
	<script type="text/javascript">
	if (window != top)
	    top.location.href = window.location.href;
	</script>
</head>

<body>
	<div style="padding-bottom: 30px;">
		<div class="login_log">
			<img src="../../static/image/logo1.png">
		</div>
        <div class="loginTop">
			<div class="loginTop_con">
				<div class="loginBox">
					<div class="title">快递管理平台</div>
					<form action="${ctx}/managers/user/login.do" method="post">
						<div class="pos-r">
							<input type="text" placeholder="用户名" class="input-text size-XL txtname" id="user" name="userName">
						</div>
						<div class="pos-r mt-20">
							<i class="Hui-iconfont login_icon" style="top:5px">&#xe60e;</i>
							<input type="password" placeholder="密码" class="input-text size-XL txtpass" id="pwd" name="password">
						</div>
<!-- 						<div class="mt-20"> -->
<!-- 							<input type="text" placeholder="验证码" class="input-text size-XL" style="width: 200px;font-size:16px;"> -->
<!-- 							<img src="../../static/image/login_bg.png" style="width: 85px;height: 48px;"> -->
<!-- 							<a class="c-primary" href="">换一张</a> -->
<!-- 						</div> -->
						<div class="mt-20">
							<input class="btn btn-block btn-primary size-XL" type="submit" value="登录">
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="kouhao">
<!-- 			<img src="../../static/image/kouhao.png" > -->
		</div>
    </div>
	<div class="footerLogoin">©2018 成都xx信息技术有限公司</div>
</body>

</html>