<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <script type="text/javascript">
    var vm_expressSendDetail=avalon.define({
        $id:'expressSendDetail',
        data:${expressSend},
        expressSendStatusMap:${expressSendStatusMap}
    });
    </script>
    <title>寄件详情</title>
        <style>
            .table th,.table td{font-size:12px;text-align:center;}
            .table td.text-r{text-align:right;}
        </style>
</head>
<body :controller="expressSendDetail">
    <div class="yhl-lay-box">
        <div class="panel">
            <div class="panel-body">
                <div class="row cl">
                    <div class="col-xs-4 text-r">快递单号：</div>
                    <div class="col-xs-8">
                        <p>{{@data.code}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">寄件人姓名：</div>
                    <div class="col-xs-8">
                        <p>{{@data.senderName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">寄件人电话：</div>
                    <div class="col-xs-8">
                        <p>{{@data.senderPhone}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">寄件人地址：</div>
                    <div class="col-xs-8">
                        <p>{{@data.senderAddr}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">收件人姓名：</div>
                    <div class="col-xs-8">
                        <p>{{@data.receiverName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">收件人电话：</div>
                    <div class="col-xs-8">
                        <p>{{@data.receiverPhone}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">收件人地址：</div>
                    <div class="col-xs-8">
                        <p>{{@data.receiverAddr}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">快递公司：</div>
                    <div class="col-xs-8">
                        <p>{{@data.companyName}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">备注：</div>
                    <div class="col-xs-8">
                        <p>{{@data.remark}}</p>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-4 text-r">状态：</div>
                    <div class="col-xs-8">
                        <p>{{@expressSendStatusMap[@data.expressStatus]}}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${ctx}/static/lib/requirejs/requirejs.js" data-main="${ctx}/static/requirejs.config.js"></script>
</body>
</html>