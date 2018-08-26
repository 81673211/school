<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我待收的快件</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link href="/css/mzui.min.css" rel="stylesheet"/>
</head>
<body>
<c:choose>
    <c:when test="${list != null and list.size() > 0}">
        <div class="list section with-divider">
            <c:forEach items="${list}" varStatus="var" var="item">
                <a class="item multi-lines with-avatar">
                    <jsp:include page="snippet/item_logo.jsp">
                        <jsp:param name="companyName" value="${item.companyName}"/>
                    </jsp:include>
                    <div class="content">
                        <div>
                            <div class="title inline">
                                <span>${item.code}</span>
                                <span>
                                    <c:choose>
                                        <c:when test="${item.expressStatus == 1}">
                                            <label class="label label-sm gray outline rounded">等待自提</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 2}">
                                            <label class="label label-sm gray outline rounded">等待入柜</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 3}">
                                            <label class="label label-sm gray outline rounded">快件已入柜</label>
                                        </c:when>
                                        <c:otherwise>
                                            <label class="label label-sm gray outline rounded">快件入柜超时</label>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                            <div>
                                <small class="muted"><fmt:formatDate value="${item.createdTime}"
                                                                     pattern="yyyy-MM-dd HH:mm"/></small>
                            </div>
                            <div>
                                <c:choose>
                                    <c:when test="${item.expressStatus == 0 || item.expressStatus == 1}">
                                        <small class="muted">如选择配送，需要支付(￥<fmt:formatNumber
                                                value="${item.distributionCost}" pattern="0.00"/>)
                                        </small>
                                    </c:when>
                                    <c:otherwise>
                                        <span><br/></span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                        </div>
                    </div>
                    <div class="pull-right">
                        <c:choose>
                            <c:when test="${item.expressStatus == 0}">
                                <div>
                                    <button type="button" class="btn btn-sm info outline rounded"
                                            style="margin-bottom: 5px" onclick="launchPay(${item.id})">配送
                                    </button>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-sm gray outline rounded"
                                            onclick="receiveWay(${item.id},0)">自提
                                    </button>
                                </div>
                            </c:when>
                            <c:when test="${item.expressStatus == 1}">
                                <div>
                                    <button type="button" class="btn btn-sm info outline rounded"
                                            onclick="launchPay(${item.id})">配送
                                    </button>
                                </div>
                            </c:when>
                        </c:choose>

                    </div>
                </a>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="center-content" style="margin-top: 40%">
            当前没有待收取的快递!
        </div>
    </c:otherwise>
</c:choose>


<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>


<script>

    function receiveDetail(id) {
        var html = "";
        $.get("/express/1/get", {"openId": '${openId}', "id": id}, function (result) {
            $.each(result.data, function (index, item) {
                html += item;
            });
            alert(html);
        });
    }

    function receiveWay(id, way) {
        if (confirm("确认自提?")) {
            $.post("/express/1/modify", {"openId": '${openId}', "id": id, "expressWay": way}, function (result) {
                if (result.status == 200) {
                    window.location.reload();
                } else {
                }
            });
        }
    }

    function launchPay(expressId) {
        if (confirm("确认配送?")) {
            $.post("/order/1/create",
                    {
                        "expressId": expressId,
                        "openId": '${openId}'
                    },
                    function (result) {
                        if (result.status != 200) {
                            alert(result.msg);
                            return;
                        } else {
                            var orderNo = result.msg;
                            alert(orderNo);
                            window.location.href = "http://www.glove1573.cn/wxpay/pay?orderNo=" + orderNo;
                        }
                    }
            );
        }
    }

</script>
</body>
</html>