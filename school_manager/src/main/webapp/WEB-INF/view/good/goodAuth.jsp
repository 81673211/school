 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <title>商品审核</title>
    <script type="text/javascript">
      var vm_goodAuthManage=avalon.define({
          $id:'goodAuthManage',
          data:${listData},
          shops:${shops},
          goodTypes:${goodTypes},
          goodBrands:${goodBrands},
          isPostMap:${isPostMap},
          isShopGoodMap:${isShopGoodMap},
          isRecommendMap:${isRecommendMap},
          goodInfoAuthStatusMap:${goodInfoAuthStatusMap},
          inputShopId:"${searchParams.shopId}",
          inputGoodTypeId:"${searchParams.goodTypeId}",
          inputGoodBrandId:"${searchParams.goodBrandId}",
          inputStatus:"${searchParams.status}",
          inputText:"${searchParams.keyword}",
          methods:{
              query:'func',//搜索事件
              detail:'func',//详情
              auth:'func',//审核
              detailUrl:'${detailUrl}',//详情url
              goodAuthUrl:"${goodAuthUrl}"//审核url
          }
      });
    </script>
</head>

<body :controller="goodAuthManage">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 商品管理 <span class="c-gray en">&gt;</span> 商品审核 <a class="btn btn-success radius r"
            style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="page-container">
        <div class="bk-gray pd-20 clearfix">
            <div class="revi-staus f-l">
                <span class="f-l lh-30">店铺：</span>
                <span class="select-box radius f-l" style="width: 120px;">
                 <select class="select" size="1" name="shopId" :duplex="@inputShopId">
                 	<option value="">全部</option>
                    <option :for="el in @shops" :attr="{value:el.id}">{{el.shopName}}</option> 
                 </select>
             </span>
                <span class="f-l lh-30 ml-20">商品类型：</span>
                <span class="select-box radius f-l" style="width: 120px;">
                 <select class="select" size="1" name="goodTypeId" :duplex="@inputGoodTypeId">
                 	<option value="">全部</option>
                    <option :for="el in @goodTypes" :attr="{value:el.id}">{{el.typeName}}</option> 
                 </select>
             </span>
             <span class="f-l lh-30 ml-20">品牌：</span>
                <span class="select-box radius f-l" style="width: 120px;">
                 <select class="select" size="1" name="goodBrandId" :duplex="@inputGoodBrandId">
                 	<option value="">全部</option>
                    <option :for="el in @goodBrands" :attr="{value:el.id}">{{el.brandName}}</option> 
                 </select>
             </span>
             <span class="f-l lh-30 ml-20">审核状态：</span>
                <span class="select-box radius f-l" style="width: 120px;">
                 <select class="select" size="1" name="status" :duplex="@inputStatus">
                 	<option value="">全部</option>
                    <option :for="(k,v) in @goodInfoAuthStatusMap" :attr="{value:k}">{{v}}</option> 
                 </select>
             </span>
            </div>
            <div class="tran-serch f-l ml-20">
                <input type="text" name="keywordSearch" id="" placeholder="编号/名称" style="width:200px" class="input-text" :duplex="@inputText">
                <button name="" id="" class="btn btn-primary radius" type="button" :click="@methods.query"><i class="Hui-iconfont"></i> 搜索</button>
            </div>
            <div class="f-r">
	        </div>
        </div>
        <div class="mt-20">
            <table class="table table-border table-bordered table-hover table-bg table-sort" id="goodAuthTable">
                <thead>
                    <tr class="text-c">
                        <th width="3%">编号</th>
                        <th width="5%">商品图片</th>
                        <th width="200">商品编号</th>
                        <th width="200">名称</th>
                        <th width="100">类型</th>
                        <th width="100">品牌</th>
                        <th width="100">进价</th>
                        <th width="100">原价</th>
                        <th width="100">现价</th>
                        <th width="100">现价对应积分</th>
                        <th width="100">店铺名称</th>
                        <th width="100">是否邮寄</th>
                        <th width="100">邮寄费用</th>
                        <th width="100">是否店铺商品</th>
                        <th width="100">总量</th>
                        <th width="100">销售数量</th>
<!--                         <th width="300">上架时间</th> -->
<!--                         <th width="300">下架时间</th> -->
<!--                         <th width="100">审核不通过原因</th> -->
<!--                         <th width="100">创建时间</th> -->
                        <th width="100">首页推荐</th>
<!--                         <th width="100">二维码图片地址</th> -->
                        <th width="5%">状态</th>
                        <th width="8%">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr class="text-c" :for="index,el in @data">
                        <td>{{index+1}}</td>
                        <td>
                        	<a :attr="{href:'${ctx}'+el.bigImgPath}" data-lightbox="gallery" :if="(el.smallImgPath != '' && el.smallImgPath != null)">
								<img :attr="{src:'${ctx}'+el.smallImgPath}" width="27px;" height="27px;">
							</a>
                        </td>
                        <td>{{el.goodNo}}</td>
                        <td>{{el.goodName}}</td>
                        <td>{{el.goodTypeName}}</td>
                        <td>{{el.goodBrandName}}</td>
                        <td>{{el.purchasePrice | number(2)}}</td>
                        <td>{{el.oldPrice | number(2)}}</td>
                        <td>{{el.price | number(2)}}</td>
                        <td>{{el.goodPoints}}</td>
                        <td>{{el.shopName}}</td>
                        <td>{{@isPostMap[el.isPost]}}</td>
                        <td>{{el.postMoney | number(2)}}</td>
                        <td>{{@isShopGoodMap[el.isShopGood]}}</td>
                        <td>{{el.totalNum}}</td>
                        <td>{{el.saleNum}}</td>
<!--                         <td>{{el.publishTime}}</td> -->
<!--                         <td>{{el.downTime}}</td> -->
<!--                         <td>{{el.noPassReason}}</td> -->
<!--                         <td>{{el.createTime}}</td> -->
                        <td>{{@isRecommendMap[el.isRecommend]}}</td>
<!--                         <td>{{el.qrCodePath}}</td> -->
                        <td>{{@goodInfoAuthStatusMap[el.status]}}</td>
                        <td class="but_xq">
                        	<btn:hasUrlPerm link="${detailUrl}">
	                           	<a class="btn btn-primary-outline size-S radius" :click="@methods.detail(el.id,@methods.detailUrl)">详情</a>
                            </btn:hasUrlPerm>
                        	<btn:hasUrlPerm link="${goodAuthUrl}">
	                           	<a class="ml-5 btn btn-primary-outline size-S radius" :click="@methods.auth(el.id,@methods.goodAuthUrl)" :if="el.status == 3">审核</a>
                            </btn:hasUrlPerm>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!--分页-->
		<div class="pag-box withdarwal-pag" id="page">
			<form method="post" id="searchForm"
				action="${ctx}/good/goodAuth/list.do">
				<input name="search_shopId" id="testForm_shopId" type="hidden"
					:attr="{value:@inputShopId}" />
				<input name="search_goodTypeId" id="testForm_goodTypeId" type="hidden"
					:attr="{value:@inputGoodTypeId}" />
				<input name="search_goodBrandId" id="testForm_goodBrandId" type="hidden"
					:attr="{value:@inputGoodBrandId}" />
				<input name="search_status" id="testForm_state" type="hidden"  
				    :attr="{value:@inputStatus}" />
				<input name="search_keyword" id="testForm_text" type="hidden"
					:attr="{value:@inputText}" />
			</form>
			<page:createPager pageSize="${pageSize}" totalPage="${pageCount}"
					totalCount="${totalCount}" curPage="${pageNo}" formId="searchForm" />
		</div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/model/good/goodAuth.js"></script>
</body>
</html>