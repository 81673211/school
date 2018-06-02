<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/WEB-INF/view/common/common.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录管理</title>
<style>
	body,p,div,ul,li,h1,h2,h3,h4,h5,h6{
		margin:0;
		padding: 0;
	}
	body{
		background: #E9E9E9; 
	}
	#login{
		width: 400px;
		height: 250px;
		background: #FFF;
		margin:200px auto;
		position: relative;
	}
	#login h1{
		text-align:center;
		position:absolute;
		left:120px;
		top:-40px;
		font-size:21px;
	}
	#login form p{
		text-align: center;
	}
	#user{
		background:url(../../static/image/user.png) rgba(0,0,0,.1) no-repeat;
		width: 200px;
		height: 30px;
		border:solid #ccc 1px;
		border-radius: 3px;
		padding-left: 32px;
		margin-top: 50px;
		margin-bottom: 30px;
	}
	#pwd{
		background: url(../../static/image/pwd.png) rgba(0,0,0,.1) no-repeat;
		width: 200px;
		height: 30px;
		border:solid #ccc 1px;
		border-radius: 3px;
		padding-left: 32px;
		margin-bottom: 30px;
	}
	#submit{
		width: 232px;
		height: 30px;
		background: rgba(0,0,0,.1);
		border:solid #ccc 1px;
		border-radius: 3px;
	}
	#submit:hover{
		cursor: pointer;
		background:#D8D8D8;
	}
</style>
<script type="text/javascript">
if (window != top)
    top.location.href = window.location.href;
</script>
</head>
<body>
	<div id="login">
	<h1>登陆管理</h1>
		<form action="<c:url value='/managers/user/login.do' />" method="post">
			<p><input type="text" name="userName" id="user" placeholder="用户名"></p>
			<p><input type="password" name="password" id="pwd" placeholder="密码"></p>
			<p><input type="submit" id="submit" value="登陆"></p>
		</form>
	</div>
</body>
</html>