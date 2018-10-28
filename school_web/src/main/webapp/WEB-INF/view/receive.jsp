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


    <script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>
    <script src="../../js/limit.js"></script>

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

                        <div class="title inline">
                            <span>${item.code}</span>
                            <span>
                                    <c:choose>
                                        <c:when test="${item.expressStatus == 0}">
                                            <label class="label label-sm primary outline rounded">代理点已签收</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 1}">
                                            <label class="label label-sm primary outline rounded">等待自提</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 2}">
                                            <label class="label label-sm primary outline rounded">等待入柜</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 3}">
                                            <label class="label label-sm primary outline rounded">快件已入柜</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 4}">
                                            <label class="label label-sm primary outline rounded">快件已入柜</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 5}">
                                            <label class="label label-sm primary outline rounded">已完成</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 6}">
                                            <label class="label label-sm primary outline rounded">等待取件</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 7}">
                                            <label class="label label-sm primary outline rounded">正在取件</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 8}">
                                            <label class="label label-sm primary outline rounded">未生效</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 10}">
                                            <label class="label label-sm primary outline rounded">补单未支付</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 11}">
                                            <label class="label label-sm primary outline rounded">补单已支付</label>
                                        </c:when>
                                        <c:when test="${item.expressStatus == 12}">
                                            <label class="label label-sm primary outline rounded">正在派送</label>
                                        </c:when>
                                        <c:otherwise>
                                            <label class="label label-sm primary outline rounded"> </label>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                        </div>
                        <div>
                            <small class="muted"><fmt:formatDate value="${item.createdTime}" pattern="yyyy-MM-dd HH:mm" /></small>
                        </div>
                        <div>
                            <c:choose>
                                <c:when test="${item.expressStatus == 0 || item.expressStatus == 1}">
                                    <small class="muted">如选择入柜，需要支付(￥<fmt:formatNumber
                                            value="${item.distributionBoxCost}" pattern="0.00"/>)
                                    </small><br>
                                    <small class="muted">如选择送货上门，需要支付(￥<fmt:formatNumber
                                            value="${item.distributionDoorCost}" pattern="0.00"/>)
                                    </small>
                                </c:when>
                                <c:when test="${item.expressStatus == 10}">
                                    <small class="muted">您还需要支付补差(￥<fmt:formatNumber
                                            value="${item.reOrderAmt}" pattern="0.00"/>)
                                    </small>
                                </c:when>
                                <c:otherwise>
                                    <span><br/><br/></span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="pull-right">
                        <c:choose>
                            <c:when test="${item.expressStatus == 0}">
                                <div>
                                    <button type="button" class="btn btn-sm info outline rounded"
                                            style="margin-bottom: 5px;width: 70px"
                                            onclick="receiveWay(${item.id}, 1)">配送入柜
                                    </button>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-sm info outline rounded"
                                            style="margin-bottom: 5px;width: 70px"
                                            onclick="launchPay(${item.id}, 2)">送货上门
                                    </button>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-sm warning outline rounded" style="width: 70px"
                                            onclick="receiveWay(${item.id},0)">自提
                                    </button>
                                </div>
                            </c:when>
                            <c:when test="${item.expressStatus == 1}">
                                <div>
                                    <button type="button" class="btn btn-sm info outline rounded" style="width: 70px"
                                            onclick="launchPay(${item.id}, 1)">配送入柜
                                    </button>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-sm info outline rounded"
                                            style="width: 70px"
                                            onclick="launchPay(${item.id}, 2)">送货上门
                                    </button>
                                </div>
                            </c:when>
                            <c:when test="${item.expressStatus == 8}">
                                <div>
                                    <button type="button" class="btn btn-sm info outline rounded" style="width: 70px"
                                            onclick="launchPay(${item.id},'${item.expressWay}')">支付
                                    </button>
                                </div>
                            </c:when>
                            <c:when test="${item.expressStatus == 10}">
                                <div>
                                    <button type="button" class="btn btn-sm info outline rounded" style="width: 70px"
                                            onclick="launchRePay(${item.id})">支付补差
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
    window.alert = function (name) {
        var iframe = document.createElement("IFRAME");
        iframe.style.display = "none";
        iframe.setAttribute("src", 'data:text/plain,');
        document.documentElement.appendChild(iframe);
        window.frames[0].window.alert(name);
        iframe.parentNode.removeChild(iframe);
    };

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
        var msg = (way == 0 ? "确认自提?" : "确认入柜?");
        if (confirm(msg)) {
            $.post("/express/1/modify", {"openId": '${openId}', "id": id, "expressWay": way}, function (result) {
                window.location.reload();
            });
        }
    }

    function launchPay(expressId, type) {
        if (confirm("确认配送?")) {
            $.post("/express/1/modify",
                    {
                        "id": expressId,
                        "openId": '${openId}',
                        "expressWay": type
                    },
                    function (result) {
                        if (result.status != 200) {
                            alert(result.msg);
                            return;
                        } else {
                            var orderNo = result.msg;
                            window.location.href = "http://www.glove1573.cn/wxpay/pay?orderNo=" + orderNo;
                        }
                    }
            );
        }
    }

    function launchRePay(expressId) {
      if (confirm("确认支付?")) {
        $.post("/order/1/reOrder/create",
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
              window.location.href = "http://www.glove1573.cn/wxpay/pay?orderNo=" + orderNo;
            }
          }
        );
      }
    }
</script>
</body>

</html>