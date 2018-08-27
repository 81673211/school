<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>帮我取件</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link href="/css/mzui.min.css" rel="stylesheet" />
</head>
<body>
<input type="hidden" value="${openId}" id="openId">


<section class="section">
    <form class="box" onsubmit="return false;">
        <div class="control">
            <label for="code">快递单号 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i> </label>
            <input id="code" type="text" class="input">
        </div>
        <div class="control">
            <label for="receiverName">收件人姓名 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i>
            </label>
            <input id="receiverName" type="text" class="input">
        </div>
        <div class="control">
            <label for="receiverPhone">收件人手机号 <i class="icon icon-asterisk"
                                                 style="font-size: 5px;color:red"></i> </label>
            <input id="receiverPhone" type="text" class="input">
        </div>
        <div class="control">
            <label for="company">选择快递公司 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i>
            </label>
            <div class="select">
                <select id="company" name="company">
                    <c:if test="${companyList != null and companyList.size() > 0}">
                        <c:forEach items="${companyList}" varStatus="var" var="item">
                            <c:if test="${item.id != 1}">
                                <option value="${item.id}">${item.name}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="control">
            <label for="helpReceiveAddr">取件地址 <i class="icon icon-asterisk"
                                                 style="font-size: 5px;color:red"></i> </label>
            <input id="helpReceiveAddr" type="text" class="input">
        </div>
        <div class="control">
            <label for="helpReceiveCode">取件码</label>
            <input id="helpReceiveCode" type="text" class="input">
        </div>

        <div class="control text-center">
            <span id="confirm">
                <button type="button" class="btn primary">提交</button>
            </span>
        </div>
    </form>
</section>

<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>

<script>


  $("#confirm").click(function () {
    var openId = $("#openId").val();
    var code = $("#code").val();
    var receiverName = $("#receiverName").val();
    var receiverPhone = $("#receiverPhone").val();
    var companyId = $("#company").val();
    var helpReceiveAddr = $("#helpReceiveAddr").val();
    var helpReceiveCode = $("#helpReceiveCode").val();

    if (openId == '') {
      alert("参数错误");
      return false;
    }
    var data = {openId: openId};

    if (code != '') {
      data.code = code;
    } else {
      alert("请输入快递单号");
      return false;
    }
    if (receiverName != '') {
      data.receiverName = receiverName;
    } else {
      alert("请输入收件人姓名");
      return false;
    }
    if (receiverPhone != '') {
      data.receiverPhone = receiverPhone;
    } else {
      alert("请输入收件人电话");
      return false;
    }
    if (companyId != '') {
      data.companyId = companyId;
    } else {
      alert("请选择快递公司");
      return false;
    }
    if (helpReceiveAddr != '') {
      data.helpReceiveAddr = helpReceiveAddr;
    } else {
      alert("请输入详细取件地址");
      return false;
    }
    if (helpReceiveCode != '') {
      data.helpReceiveCode = helpReceiveCode;
    }
    $.post("/express/1/help/create", data, function (result) {
      if (result.status != 200) {
        alert(result.msg);
      } else {
        var orderNo = result.data;
        alert(orderNo);
        window.location.href = "http://www.glove1573.cn/wxpay/pay?orderNo=" + orderNo;
      }
    });
  });

</script>
</body>
</html>