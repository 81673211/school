<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我待收的快件</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link href="/css/mzui.min.css" rel="stylesheet" />
</head>
<body>
<div class="heading">
    <div class="title"><h3>待收快件</h3></div>
</div>

<div class="list section">
    <c:if test="${list != null and list.size() > 0}">
        <c:forEach items="${list}" varStatus="var" var="item">
            <a class="item multi-lines with-avatar">
                <div class="avatar circle blue outline">${var.count}</div>
                <div class="content">
                    <div>
                        <span class="title">${item.code}</span>
                        <div>
                            <small class="text-gray">${item.companyName}</small>
                            &nbsp;
                            <small class="muted"><fmt:formatDate value="${item.createdTime}"
                                                                 pattern="yyyy-MM-dd HH:mm" /></small>
                        </div>
                    </div>
                    <div class="inline-block pull-right">
                        <c:choose>
                            <c:when test="${item.expressStatus == 0}">
                                <button type="button" class="btn primary-pale btn-sm text-tint pull-left"
                                        onclick="launchPay(${item.id})">配送(￥<fmt:formatNumber
                                        value="${item.distributionCost}" pattern="0.00" />)
                                </button>
                                &nbsp;&nbsp;
                                <button type="button" class="btn primary-pale btn-sm text-tint pull-right"
                                        onclick="receiveWay(${item.id},0)">自提
                                </button>
                            </c:when>
                            <c:when test="${item.expressStatus == 1}">
                                <button type="button" class="btn primary-pale btn-sm text-tint pull-left"
                                        onclick="launchPay(${item.id})">配送(￥<fmt:formatNumber
                                        value="${item.distributionCost}" pattern="0.00" />)
                                </button>
                                &nbsp;&nbsp;
                                <label class="btn primary-pale btn-sm text-tint pull-right">等待自提</label>
                            </c:when>
                            <c:when test="${item.expressStatus == 2}">
                                <label class="btn primary-pale btn-sm text-tint pull-right">等待入柜</label>
                            </c:when>
                            <c:when test="${item.expressStatus == 3}">
                                <label class="btn primary-pale btn-sm text-tint pull-right">快件已入柜</label>
                            </c:when>
                            <c:otherwise>
                                <label class="btn primary-pale btn-sm text-tint pull-right">快件入柜超时</label>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </a>
        </c:forEach>
    </c:if>
</div>

<!-- ZUI Javascript 依赖 jQuery -->
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
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
    $.post("/express/1/modify", {"openId": '${openId}', "id": id, "expressWay": way}, function (result) {
      if (result.status == 200 && way == 1) {
        window.location.href = "http://www.glove1573.cn/wxpay/pay?expressId=" + id;
      } else {
        window.location.reload();
      }
    });
  }
  
  function launchPay(expressId) {
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

</script>
</body>
</html>