 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <title>收件管理</title>
    <script type="text/javascript">
      var vm_expressReceiveManage=avalon.define({
          $id:'expressReceiveManage',
          data:${listData},
          expressReceiveStatusMap:${expressReceiveStatusMap},
          expressTypeMap:${expressTypeMap},
          helpDistributionTypeMap:${helpDistributionTypeMap},
          expressWayMap:${expressWayMap},
          companyMap:${companyMap},
          inputStatus:"${searchParams.status}",
          inputCompany:"${searchParams.companyId}",
          inputText:"${searchParams.keyword}",
          methods:{
              query:'func',//搜索事件
              detail:'func',//查看
              add:'func',//新增
              edit:'func',//编辑
              del:'func',//删除
              reOrder:'func',//补单
              detailUrl:"${detailUrl}",//详情url
              editUrl:"${editUrl}",//编辑url
              delUrl:"${delUrl}",//删除url
              reOrderUrl:"${reOrderUrl}",//补单url
              refundUrl:"${refundUrl}"//退款url
          }
      });
    </script>
</head>

<body :controller="expressReceiveManage">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 快递管理 <span class="c-gray en">&gt;</span> 收件列表 <a class="btn btn-success radius r"
            style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="page-container">
        <div class="bk-gray pd-20 clearfix">
            <div class="revi-staus f-l">
                <span class="f-l lh-30">状态：</span>
                <span class="select-box radius f-l" style="width: 120px;">
                 <select class="select" size="1" name="status" :duplex="@inputStatus">
                 	<option value="">全部</option>
                    <option :for="(k,v) in @expressReceiveStatusMap" :attr="{value:k}">{{v}}</option> 
                 </select>
                </span>
            </div>
            <div class="revi-staus f-l">
                <span class="f-l lh-30">快递公司：</span>
                <span class="select-box radius f-l" style="width: 120px;">
                 <select class="select" size="1" name="companyId" :duplex="@inputCompany">
                 	<option value="">全部</option>
                    <option :for="(k,v) in @companyMap" :attr="{value:v}">{{k}}</option>
                 </select>
                </span>
            </div>
            <div class="tran-serch f-l ml-50">
                <input type="text" name="keywordSearch" id="" placeholder="快递单号/收寄人姓名/电话" style="width:200px" class="input-text" :duplex="@inputText">
                <button name="" id="" class="btn btn-primary radius" type="button" :click="@methods.query"><i class="Hui-iconfont"></i> 搜索</button>
            </div>
            <div class="f-r">
                <btn:hasUrlPerm link="${editUrl}">
                	<a class="btn btn-danger ml-10" data-title="新建" :click="@methods.add(@methods.editUrl)" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
                </btn:hasUrlPerm>
	        </div>
        </div>
        <div class="mt-20">
            <table class="table table-border table-bordered table-hover table-bg table-sort" id="reviwtable">
                <thead>
                    <tr class="text-c">
                        <th width="25">编号</th>
                        <th width="50">快递单号</th>
                        <th width="50">收件人姓名</th>
                        <th width="50">收件人电话</th>
                        <th width="100">收件人地址</th>
                        <th width="50">快递公司</th>
                        <th width="50">类型</th>
                        <th width="50">取件码</th>
                        <th width="50">取件地址</th>
                        <th width="50">重量</th>
                        <th width="50">配送方式</th>
                        <th width="50">已支付</th>
                        <th width="30">已补单(服务费)</th>
                        <th width="50">创建时间</th>
                        <th width="50">备注</th>
                        <th width="30">状态</th>
                        <th width="50">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="text-c" :for="index,el in @data">
                        <td>{{index+1}}</td>
                        <td>{{el.code}}</td>
                        <td>{{el.receiverName}}</td>
                        <td>{{el.receiverPhone}}</td>
                        <td>{{el.receiverAddr}}</td>
                        <td>{{el.companyName}}</td>
                        <td>{{@expressTypeMap[el.expressType]}}</td>
                        <td>{{el.helpReceiveCode}}</td>
                        <td>{{el.helpReceiveAddr}}</td>
                        <td>{{el.expressWeight}}</td>
                        <td>{{@expressWayMap[el.expressWay]}}</td>
                        <td>{{el.serviceAmt}}</td>
                        <td>{{el.reOrderServiceAmt}}</td>
                        <td>{{el.createdTime|date('yyyy-MM-dd HH:mm:ss')}}</td>
                        <td>{{el.remark}}</td>
                        <td>
                        	<div :if="el.expressStatus == 8">{{@expressReceiveStatusMap[el.expressStatus]}}</div>
                        	<div :if="el.expressStatus != 8" style="color: green;">{{@expressReceiveStatusMap[el.expressStatus]}}</div>
                        </td>
                        <td class="but_xq">
                            <btn:hasUrlPerm link="${reOrderUrl}">
                                <a :if="el.expressStatus != 5" class="btn btn-primary-outline size-S radius" :click="@methods.reOrder(el.id,@methods.reOrderUrl)">补单</a>
                            </btn:hasUrlPerm>
                            <btn:hasUrlPerm link="${refundUrl}">
                                <a :if="el.expressStatus != 5" class="ml-5 btn btn-primary-outline size-S radius" :click="@methods.refund(el.id,@methods.refundUrl)">退款</a>
                            </btn:hasUrlPerm>
                        	<btn:hasUrlPerm link="${detailUrl}">
	                           	<a class="ml-5 btn btn-primary-outline size-S radius" :click="@methods.detail(el.id,@methods.detailUrl)">详情</a>
                            </btn:hasUrlPerm>
                            <btn:hasUrlPerm link="${editUrl}">
                            	<a class="ml-5 btn btn-primary-outline size-S radius" :click="@methods.edit(el.id, @methods.editUrl)">编辑</a>
                            </btn:hasUrlPerm>
                            <btn:hasUrlPerm link="${delUrl}">
                            	<a class="ml-5 btn btn-danger-outline size-S radius" :click="@methods.del(el.id, @methods.delUrl)">删除</a>
                            </btn:hasUrlPerm>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!--分页-->
		<div class="pag-box withdarwal-pag" id="page">
			<form method="post" id="searchForm"
				action="${ctx}/express/expressReceive/list.do">
				<input name="search_status" id="testForm_state" type="hidden"  :attr="{value:@inputStatus}" />
                <input name="search_companyId" id="testForm_companyId" type="hidden" :attr="{value:@inputCompany}" />
				<input name="search_type" id="testForm_type" type="hidden" :attr="{value:@inputType}" />
				<input name="search_keyword" id="testForm_text" type="hidden" :attr="{value:@inputText}" />
			</form>
			<page:createPager pageSize="${pageSize}" totalPage="${pageCount}"
					totalCount="${totalCount}" curPage="${pageNo}" formId="searchForm" />
		</div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/model/express/expressReceive.js"></script>
</body>
</html>