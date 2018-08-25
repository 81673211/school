 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <title>订单管理</title>
    <script type="text/javascript">
      var vm_orderManage=avalon.define({
          $id:'orderManage',
          data:${listData},
          orderStatusMap:${orderStatusMap},
          inputStatus:"${searchParams.status}",
          inputText:"${searchParams.keyword}",
          methods:{
              query:'func',//搜索事件
              detail:'func',//查看
              detailUrl:"${detailUrl}",//详情url
              refund:"func",
              refundUrl:"${refundUrl}",
              toRefundUrl:"${toRefundUrl}"
          }
      });
    </script>
</head>

<body :controller="orderManage">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 订单管理 <span class="c-gray en">&gt;</span> 订单列表 <a class="btn btn-success radius r"
            style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="page-container">
        <div class="bk-gray pd-20 clearfix">
            <div class="revi-staus f-l">
                <span class="f-l lh-30">状态：</span>
                <span class="select-box radius f-l" style="width: 120px;">
                 <select class="select" size="1" name="status" :duplex="@inputStatus">
                 	<option value="">全部</option>
                    <option :for="(k,v) in @orderStatusMap" :attr="{value:k}">{{v}}</option> 
                 </select>
             </span>
            </div>
            <div class="tran-serch f-l ml-50">
                <input type="text" name="keywordSearch" id="" placeholder="订单名称" style="width:200px" class="input-text" :duplex="@inputText">
                <button name="" id="" class="btn btn-primary radius" type="button" :click="@methods.query"><i class="Hui-iconfont"></i> 搜索</button>
            </div>
        </div>
        <div class="mt-20">
            <table class="table table-border table-bordered table-hover table-bg table-sort" id="reviwtable">
                <thead>
                    <tr class="text-c">
                        <th width="25">编号</th>
                        <th width="100">订单号</th>
                        <th width="100">快递单号</th>
                        <th width="30">快递类型</th>
                        <th width="100">订单金额</th>
                        <th width="100">创建时间</th>
                        <th width="100">完成时间</th>
                        <th width="100">订单状态</th>
                        <th width="50">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="text-c" :for="index,el in @data">
                        <td>{{index+1}}</td>
                        <td>{{el.orderNo}}</td>
                        <td>{{el.expressCode}}</td>
                        <td>{{el.expressType}}</td>
                        <td>{{el.amount}}</td>
                        <td>{{el.createdTime|date('yyyy-MM-dd HH:mm:ss')}}</td>
                        <td>{{el.sucTime|date('yyyy-MM-dd HH:mm:ss')}}</td>
                        <td>{{@orderStatusMap[el.status]}}</td>
<!--                         <td>{{el.status}}</td> -->
                        <td class="but_xq">
                        	<btn:hasUrlPerm link="${detailUrl}">
	                           	<a class="btn btn-primary-outline size-S radius" :click="@methods.detail(el.id,@methods.detailUrl)">详情</a>
                            </btn:hasUrlPerm>
                        	<btn:hasUrlPerm link="${refundUrl}">
	                           	<a class="ml-5 btn btn-primary-outline size-S radius" :click="@methods.refund(el.id,@methods.toRefundUrl)">退款</a>
	                        </btn:hasUrlPerm>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!--分页-->
		<div class="pag-box withdarwal-pag" id="page">
			<form method="post" id="searchForm"
				action="${ctx}/order/list.do">
				<input name="search_status" id="testForm_state"
					type="hidden"  :attr="{value:@inputStatus}" />
				<input name="search_type" id="testForm_type" type="hidden"
					:attr="{value:@inputType}" />
				<input name="search_keyword" id="testForm_text" type="hidden"
					:attr="{value:@inputText}" />
			</form>
			<page:createPager pageSize="${pageSize}" totalPage="${pageCount}"
					totalCount="${totalCount}" curPage="${pageNo}" formId="searchForm" />
		</div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/model/order/order.js"></script>
</body>
</html>