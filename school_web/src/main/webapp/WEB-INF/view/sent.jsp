<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我寄出的快件</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link href="/css/mzui.min.css" rel="stylesheet" />
</head>
<body>

<c:choose>
    <c:when test="${list != null and list.size() > 0}">
        <div class="list section with-divider">
            <c:forEach items="${list}" varStatus="var" var="item">
                <a class="item multi-lines with-avatar">
                    <jsp:include page="snippet/item_logo.jsp">
                        <jsp:param name="companyId" value="${item.companyId}"/>
                    </jsp:include>
                    <div class="content">
                        <div>
                            <div class="title inline">
                                <span>${item.code}</span>
                                <span>
                                    <c:choose>
                                        <c:when test="${item.expressStatus == 0}">
                                            <label class="label label-sm gray outline rounded">发起寄件</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 1}">
                                            <label class="label label-sm gray outline rounded">等待上门取件</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 2}">
                                            <label class="label label-sm gray outline rounded">代收点已签收</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 3}">
                                            <label class="label label-sm gray outline rounded">补单待支付</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 4}">
                                            <label class="label label-sm gray outline rounded">准备寄出</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 5}">
                                            <label class="label label-sm gray outline rounded">已寄出</label>
                                        </c:when>
                                    </c:choose>
                                </span>
                            </div>
                            <div>
                                <small class="muted"><fmt:formatDate value="${item.createdTime}" pattern="yyyy-MM-dd HH:mm" /></small>
                            </div>
                            <div>
                                <small class="muted">${item.receiverProvince} ${item.receiverCity} ${item.receiverDistrict}</small>
                            </div>
                            <div>
                                <small class="muted">${item.receiverName} ${item.receiverPhone}</small>
                            </div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="center-content" style="margin-top: 40%">
            当前没有已寄出的快递!
        </div>
    </c:otherwise>
</c:choose>

<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>
</body>
</html>