<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我寄出的快件</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="../../css/cssReset.css">
    <!-- ZUI 标准版压缩后的 CSS 文件 -->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/css/zui.min.css">
    <link rel="stylesheet" href="../../css/sudi.css">
</head>
<body>
<div class="wrap container receivedExpress">
    <h3 class="">我寄出的快件</h3>
    <c:if test="${list != null and list.size() > 0}">
        <c:forEach items="${list}" varStatus="var" var="item">
            <div>
                <div class="row">
                    <div class="col-xs-7">
                        <label for="">快递名称:<span>${item.companyName}</span></label>
                        <label for="">运单号:<span>${item.code}</span></label>
                        <label for="">状态:<span>
                         <c:choose>
                             <c:when test="${item.expressStatus == 0}">
                                 发起寄件
                             </c:when>
                             <c:when test="${item.expressStatus == 1}">
                                 等待上门取件
                             </c:when>
                             <c:when test="${item.expressStatus == 2}">
                                 代收点已签收
                             </c:when>
                             <c:when test="${item.expressStatus == 3}">
                                 已寄出
                             </c:when>
                         </c:choose></span></label>
                    </div>
                    <div class="col-xs-5">
                        <label for=""><span>${item.receiverName}</span></label>
                        <label for=""><span>${item.receiverPhone}</span></label>
                    </div>
                </div>
                <div>
                    <label for="">地址:<span>${item.receiverProvince}&nbsp;&nbsp;${item.receiverCity}&nbsp;&nbsp;${item.receiverDistrict}&nbsp;&nbsp;${item.receiverAddr}</span></label>
                    <label for="" class="pull-right timeExp"><span>${item.endTime}</span></label>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>

<!-- ZUI Javascript 依赖 jQuery -->
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>

</body>
</html>