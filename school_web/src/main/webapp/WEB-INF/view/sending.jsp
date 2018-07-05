<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我要寄件</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="../../css/cssReset.css">
    <!-- ZUI 标准版压缩后的 CSS 文件 -->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/css/zui.min.css">
    <link rel="stylesheet" href="../../css/sudi.css">
</head>
<body>
<div class="wrap container sendPiece">
    <h3 class="">我要寄件</h3>
    <div class="input-control has-icon-right">
        <input type="password" class="form-control" placeholder="收件人姓名">
        <label class="input-control-icon-right"><i class="icon icon-asterisk"></i></label>
    </div>
    <div class="row">
        <div class="col-xs-3">
            <select class="form-control ">
                <option value="">[---请选择---]</option>
                <c:if test="${regionList != null and regionList.size() > 0}">
                    <c:forEach items="${regionList}" varStatus="var" var="item">
                        <option value="${item.id}">${item.areaName}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <div class="col-xs-3">
            <select class="form-control">
                <option value="">[---请选择---]</option>
            </select>
        </div>
        <div class="col-xs-3">
            <select class="form-control">
                <option value="">[---请选择---]</option>
            </select>
        </div>
        <div class="col-xs-3">
            <select class="form-control">
                <option value="">[---请选择---]</option>
            </select>
        </div>
    </div>
    <div class="input-control has-icon-right">
        <input type="password" class="form-control" placeholder="详细地址">
        <label class="input-control-icon-right"><i class="icon icon-asterisk"></i></label>
    </div>
    <div class="input-control has-icon-right">
        <input type="password" class="form-control" placeholder="电话">
        <label class="input-control-icon-right"><i class="icon icon-asterisk"></i></label>
    </div>
    <div>
        <select class="form-control">
            <option value="">顺丰</option>
        </select>
    </div>
    <div>
        <select class="form-control">
            <option value="">寄件方式</option>
            <option value="">1</option>
            <option value="">2</option>
        </select>
    </div>
    <div>价格（￥<span class="price">10.0</span>元）</div>
    <div class="row btnGroup">
        <div class="col-xs-6">
            <button class="btn btn-danger " type="button">重置</button>
        </div>
        <div class="col-xs-6">
            <button class="btn btn-success " type="button">确定</button>
        </div>
    </div>
</div>

<!-- ZUI Javascript 依赖 jQuery -->
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>

</body>
</html>