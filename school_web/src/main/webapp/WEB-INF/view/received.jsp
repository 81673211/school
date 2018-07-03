<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我收到过的快件</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="/css/cssReset.css">
    <!-- ZUI 标准版压缩后的 CSS 文件 -->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/css/zui.min.css">
    <link rel="stylesheet" href="/css/sudi.css">
</head>
<body>
    <div class="wrap container receivedExpress">
        <h3 class="">我收到过的快件</h3>
        <c:forEach items="${list}" var="item">
            <div>
                <div  class="row">
                    <div class="col-xs-7">
                        <label for="">快递名称:<span>${item.companyName }</span></label>
                        <label for="">运单号:<span>${item.code }</span></label>

                    </div>
                    <div class="col-xs-5">
                        <label for=""><span>${item.senderName}</span></label>
                        <label for=""><span>${item.senderPhone}</span></label>

                    </div>
                </div>
                <div>
                    <label for="">地址:<span>${item.senderAddr}</span></label>
                    <label for="" class="pull-right timeExp"><span>2018-10-2 13:45</span></label>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- ZUI Javascript 依赖 jQuery -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
    <!-- ZUI 标准版压缩后的 JavaScript 文件 -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>

</body>
</html>