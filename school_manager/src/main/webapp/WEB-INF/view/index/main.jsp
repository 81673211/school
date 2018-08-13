<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
	<%@include file="/WEB-INF/view/common/common.jsp"%>
	<title>快递管理系统</title>
	<style type="text/css">
		.msg-tips{
			width: 10px;
			height: 10px;
			border-radius: 50%;
			background: #dd514c;
			display: inline-block;
		}
	</style>
	<script type="text/javascript">
      var vm_main=avalon.define({
          $id:'main',
          menus:${myLeftMenus},
          alterPwd:'func'
      });
    </script>
</head>
<body :controller="main">
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl"> 
<!--         	<a class="logo navbar-logo f-l mr-10 hidden-xs" href="/aboutHui.shtml"><img src="../../static/image/logo.png"></a> -->
            <a class="logo navbar-logo f-l mr-10 hidden-xs" href="javascript:void(0);">快递管理系统</a>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
<%--                     <li>${sessionScope.adminUser.adminName}</li> --%>
                    <li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A"><c:if test="${!empty sessionScope.adminUser}">${sessionScope.adminUser.adminName}</c:if><i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:void(0);" :click="@alterPwd()">修改密码</a></li>
                            <li><a href="${ctx}/managers/user/logout.htm">退出系统</a></li>
                        </ul>
                    </li>
                    <li id="Hui-msg"> 
                    	<a href="#" title="消息">
                    		<span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i>
                    	</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<aside class="Hui-aside">
    <input runat="server" id="divScrollValue" type="hidden" value="" />
    <div class="menu_dropdown bk_2" id="leftMenus">
    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
    <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
        <div class="Hui-tabNav-wp">
            <ul id="min_title_list" class="acrossTab cl">
                <li class="active"><span title="首页" data-href="welcome.html">首页</span><em></em></li>
            </ul>
        </div>
        <div class="Hui-tabNav-more btn-group">
            <a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a>
            <a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
        </div>
    </div>
    <div id="iframe_box" class="Hui-article">
        <div class="show_iframe">
            <div style="display:none" class="loading"></div>
            欢迎使用系统！
        </div>
    </div>
</section>
<div id="alterPwd" class="hide text-c">
	<div class="clearfix mt-10">
         <input type="password" class="input-text" placeholder="原始密码" autocomplete="off" style="width: 210px;" name="oldPwd"/>
    </div>
	<div class="clearfix mt-10">
         <input type="password" class="input-text" placeholder="新密码" autocomplete="off" style="width: 210px;" name="newPwd"/>
    </div>
	<div class="clearfix mt-10">
         <input type="password" class="input-text" placeholder="再次输入" autocomplete="off" style="width: 210px;" name="rePwd"/>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/index/main.js"></script>
</body>
</html>