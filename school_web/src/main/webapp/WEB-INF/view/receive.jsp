<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我待收的快件</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link href="/css/mzui.min.css" rel="stylesheet" />
</head>
<body class="white">

<div class="heading">
    <div class="title"><strong>7月</strong></div>
</div>

<c:choose>
    <c:when test="${list != null and list.size() > 0}">
        <div class="list with-divider">
            <c:forEach items="${list}" varStatus="var" var="item">
                <a class="item multi-lines with-avatar">
                    <div class="avatar circle blue outline">${var.count}</div>
                    <div class="content">
                        <div class="title">${item.code} ${item.companyName}</div>
                        <div class="subtitle">${item.endTime}</div>
                        <div class="pull-right">
                            <c:choose>
                                <c:when test="${item.expressStatus == 0}">
                                    <span class="icon blue-pale ">代理点已签收</span>
                                </c:when>
                                <c:when test="${item.expressStatus == 1}">
                                    <span class="label blue-pale ">等待自提</span>
                                </c:when>
                                <c:when test="${item.expressStatus == 2}">
                                    <span class="label blue-pale ">等待入柜</span>
                                </c:when>
                                <c:when test="${item.expressStatus == 3}">
                                    <span class="label blue-pale ">快件已入柜</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="label blue-pale ">快件入柜超时</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531471715&di=43b8048018a66b97871eb8a092013fee&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F011618576a5a5c0000018c1bea80fe.jpg%401280w_1l_2o_100sh.jpg" />
    </c:otherwise>
</c:choose>
<!-- ZUI Javascript 依赖 jQuery -->
<script src="/lib/jquery/jquery-3.2.1.min.js" />
<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
<script src="/js/mzui.min.js" />

</body>
</html>