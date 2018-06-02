<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_goodAuthEdit=avalon.define({
        $id:'goodAuthEdit',
        data:${goodAuth},
        isPostMap:${isPostMap},
        isShopGoodMap:${isShopGoodMap},
        isRecommendMap:${isRecommendMap},
        goodInfoStatusMap:${goodInfoStatusMap},
        goodLabels:${goodLabels},
        methods:{
        	validAndSubmit:'func'
        }
    });
    </script>
    <title>商品详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="goodAuthEdit">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-3 text-r">商品编号：</div>
                    <div class="col-xs-9">
                        <p>{{@data.goodNo}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">商品名称：</div>
                    <div class="col-xs-9">
                        <p>{{@data.goodName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">类型：</div>
                    <div class="col-xs-9">
                        <p>{{@data.goodTypeName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">品牌：</div>
                    <div class="col-xs-9">
                        <p>{{@data.goodBrandName}}</p>
                    </div>
                </div>
                <div class="row cl">
	                <div class="col-xs-3 text-r">商品图片：</div>
	                <div class="formControls col-xs-10 col-sm-9">
	                	<div class="portfolio-content">
							<ul class="cl portfolio-area">
								<li class="item" :for="index,el in @data.goodImages">
									<div class="portfoliobox">
										<div class="picbox">
											<a :attr="{href:'${ctx}'+el.bigImgPath}" data-lightbox="gallery">
												<img :attr="{src:'${ctx}'+el.bigImgPath}">
											</a>
										</div>
									</div>
								</li>
							</ul>
						</div>
	                </div>
	            </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">进货价格：</div>
                    <div class="col-xs-9">
                        <p>{{@data.purchasePrice | number(2)}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">原价：</div>
                    <div class="col-xs-9">
                        <p>{{@data.oldPrice | number(2)}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">现价：</div>
                    <div class="col-xs-9">
                        <p>{{@data.price | number(2)}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">现价对应积分：</div>
                    <div class="col-xs-9">
                        <p>{{@data.goodPoints}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">店铺名称：</div>
                    <div class="col-xs-9">
                        <p>{{@data.shopName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">是否邮寄：</div>
                    <div class="col-xs-9">
                        <p>{{@isPostMap[@data.isPost]}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">邮寄费用：</div>
                    <div class="col-xs-9">
                        <p>{{@data.postMoney | number(2)}}</p>
                    </div>
                </div>
<!--                 <div class="row cl"> -->
<!--                     <div class="col-xs-3 text-r">二维码：</div> -->
<!--                     <div class="col-xs-9"> -->
<!--                         <p>{{@data.qrCodePath}}</p> -->
<!--                     </div> -->
<!--                 </div> -->
                <div class="row cl">
                    <div class="col-xs-3 text-r">是否为商铺产品：</div>
                    <div class="col-xs-9">
                        <p>{{@isShopGoodMap[@data.isShopGood]}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">销售数量/总量：</div>
                    <div class="col-xs-9">
                        <p>{{@data.saleNum}}/{{@data.totalNum}}</p>
                    </div>
                </div>
                <div class="row cl" :if="(@data.publishTime != null && @data.publishTime != '')">
                    <div class="col-xs-3 text-r">上架时间：</div>
                    <div class="col-xs-9">
                        <p>{{@data.publishTime}}</p>
                    </div>
                </div>
                <div class="row cl" :if="(@data.downTime != null && @data.downTime != '')">
                    <div class="col-xs-3 text-r">下架时间：</div>
                    <div class="col-xs-9">
                        <p>{{@data.downTime}}</p>
                    </div>
                </div>
                <div class="row cl">
	                <div class="col-xs-3 text-r">标签：</div>
	                <div class="col-xs-9">
		                <select class="select" id="labels" name="labels" multiple disabled="disabled">
		                    <option :for="el in @goodLabels" :attr="{value:el.labelCode}">{{el.labelName}}</option> 
		                </select>
	                </div>
	            </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">首页推荐：</div>
                    <div class="col-xs-9">
                        <p>{{@isRecommendMap[@data.isRecommend]}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-3 text-r">详情描述：</div>
                    <div class="col-xs-9">
                        <textarea  style="height:300px;" id="container">{{@data.description}}</textarea>
                    </div>
                </div>
                <form id="goodAuthForm" action="${ctx}/good/goodAuth/auth.do" method="post">
                	<input name="id" type="hidden" :attr="{value:@data.id}" />
                	<input name="status" type="hidden" />
	                <div class="row cl">
	                    <div class="col-xs-3 text-r">审核意见：</div>
	                    <div class="col-xs-9">
	                        <textarea class="textarea" name="noPassReason"></textarea>
	                    </div>
	                </div>
                </form>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/model/good/goodAuthEdit.js"></script>
</body>
</html>