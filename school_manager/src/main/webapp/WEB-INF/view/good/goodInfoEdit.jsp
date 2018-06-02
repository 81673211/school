<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/view/common/common.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/lib/webuploader/0.1.5/webuploader.css" />
    <script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/webuploader/0.1.5/webuploader.js"></script>
    <script type="text/javascript">
    var vm_goodInfoEdit=avalon.define({
        $id:'goodInfoEdit',
        goodInfo:${goodInfo},
        goodLabels:${goodLabels},
        methods:{
        	validAndSubmit:'func',
        	addGoodImage:'func',
        	delGoodImage:'func'
        }
    });
    </script>
    <title>编辑商品</title>
</head>
<body :controller="goodInfoEdit">
    <div class="mt-30">
        <form id="goodInfoForm" action="${ctx}/good/goodInfo/save.do" method="post">
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">商品编号：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<input id="id" type="hidden" name="id" value="${goodInfo.id}">
                    <input class="input-text" autocomplete="off" placeholder="" id="goodNo" name="goodNo" value="${goodInfo.goodNo}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">商品名称：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="goodName" value="${goodInfo.goodName}" type="text">
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">商品类型：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<select class="input-text" name="goodTypeId">
                		<c:forEach items="${goodTypes}" var="goodType">
	                		<option value="${goodType.id}" <c:if test="${goodType.id == goodInfo.goodTypeId}">selected="selected"</c:if>>${goodType.typeName}</option>
                		</c:forEach>
               		</select>
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">品牌：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<select class="input-text" name="goodBrandId">
                		<c:forEach items="${goodBrands}" var="goodBrand">
	                		<option value="${goodBrand.id}" <c:if test="${goodBrand.id == goodInfo.goodBrandId}">selected="selected"</c:if>>${goodBrand.brandName}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
            <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">商品图片：</label>
                <div class="formControls col-xs-10 col-sm-9">
	                <div class="cl pd-5 bg-1 bk-gray"> 
	                	<span class="l">
	                		<input type="checkbox" id="selectAll" />全选/取消
	               		</span>
	               		<span class="r">
	                		<a href="javascript:;" :click="@methods.addGoodImage()" class="ml-10 btn btn-primary radius">
	                			<i class="Hui-iconfont">&#xe612;</i> 添加
	                		</a>
	                		<a href="javascript:;" :click="@methods.delGoodImage()" class="ml-5 btn btn-danger radius">
	                			<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
	                		</a>
	               		</span>
	               	</div>
                	<div class="portfolio-content">
						<ul class="cl portfolio-area">
							<li class="item" :for="index,el in @goodInfo.goodImages">
								<div class="portfoliobox">
									<input class="checkbox noNeedSubmit" name="goodImageCheckBox" type="checkbox" :attr="{value:index}">
									<div class="picbox"><a :attr="{href:'${ctx}'+el.bigImgPath}" data-lightbox="gallery"><img :attr="{src:'${ctx}'+el.bigImgPath}"></a></div>
									<div class="textbox text-c"><input class="noNeedSubmit" type="radio" name="goodSort" :attr="{index:index,checked:(el.sort == 1)}"/>&nbsp;设为默认</div>
								</div>
							</li>
						</ul>
					</div>
                </div>
            </div>
			<div id="addImageDiv" class="row cl">
				<label class="form-label col-xs-2 col-sm-2 text-r"></label>
				<div class="formControls col-xs-10 col-sm-9">
					<div class="uploader-list-container"> 
						<div class="queueList">
							<div id="dndArea" class="placeholder">
								<div id="filePicker-2"></div>
								<p>或将照片拖到这里，单次最多可选10张</p>
							</div>
						</div>
						<div class="statusBar" style="display:none;">
							<div class="progress"> <span class="text">0%</span> <span class="percentage"></span> </div>
							<div class="info"></div>
							<div class="btns">
								<div id="filePicker2"></div>
								<div class="uploadBtn">开始上传</div>
							</div>
						</div>
					</div>
				</div>
			</div>

            <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">进货价格：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="purchasePrice" value="${goodInfo.purchasePrice}" type="text">
                </div>
            </div>
            <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">原价：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="oldPrice" value="${goodInfo.oldPrice}" type="text">
                </div>
            </div>
            <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">现价：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="price" value="${goodInfo.price}" type="text">
                </div>
            </div>
            <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">现价对应积分：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="goodPoints" value="${goodInfo.goodPoints}" type="text">
                </div>
            </div>
            <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">商铺名称：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<select class="input-text" name="shopId">
                		<c:forEach items="${shops}" var="shop">
	                		<option value="${shop.id}" <c:if test="${shop.id == goodInfo.shopId}">selected="selected"</c:if>>${shop.shopName}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">是否邮寄：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<select class="input-text" name="isPost">
                		<c:forEach items="${isPostMap}" var="isPost">
	                		<option value="${isPost.key}" <c:if test="${isPost.key == goodInfo.isPost}">selected="selected"</c:if>>${isPost.value}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">邮寄费用：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="postMoney" value="${goodInfo.postMoney}" type="text">
                </div>
            </div>
<!--              <div class="row cl mb-15"> -->
<!--                 <label class="form-label col-xs-2 col-sm-2 text-r">二维码：</label> -->
<!--                 <div class="formControls col-xs-10 col-sm-9"> -->
<%--                     <input class="input-text" autocomplete="off" placeholder="" name="qrCodePath" value="${goodInfo.qrCodePath}" type="text"> --%>
<!--                 </div> -->
<!--             </div> -->
            <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">是否为商铺产品：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<select class="input-text" name="isShopGood">
                		<c:forEach items="${isShopGoodMap}" var="isShopGood">
	                		<option value="${isShopGood.key}" <c:if test="${isShopGood.key == goodInfo.isShopGood}">selected="selected"</c:if>>${isShopGood.value}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">商品总量：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <input class="input-text" autocomplete="off" placeholder="" name="totalNum" value="${goodInfo.totalNum}" type="text">
                </div>
            </div>
            
             <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">标签：</label>
                <div class="formControls col-xs-10 col-sm-9">
	                <select class="select" id="labels" name="labels" multiple>
	                    <option :for="el in @goodLabels" :attr="{value:el.labelCode}">{{el.labelName}}</option> 
	                </select>
                </div>
            </div>
            
            <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">首页推荐：</label>
                <div class="formControls col-xs-10 col-sm-9">
                	<select class="input-text" name="isRecommend">
                		<c:forEach items="${isRecommendMap}" var="isRecommend">
	                		<option value="${isRecommend.key}" <c:if test="${isRecommend.key == goodInfo.isShopGood}">selected="selected"</c:if>>${isRecommend.value}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
            <div class="row cl mb-15">
                <label class="form-label col-xs-2 col-sm-2 text-r">详情描述：</label>
                <div class="formControls col-xs-10 col-sm-9">
                    <textarea  style="height:300px" name="description" id="container">${goodInfo.description}</textarea>
                </div>
            </div>
        </form>
    </div>    
<script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
<script type="text/javascript" src="${ctx}/static/model/good/goodInfoEdit.js"></script>
</body>
</html>