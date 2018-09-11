<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>帮我取件</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link href="/css/mzui.min.css" rel="stylesheet"/>

    <script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>
    <script src="../../js/limit.js"></script>

</head>
<body>
<input type="hidden" value="${openId}" id="openId">


<section class="section">
    <form class="box" onsubmit="return false;">
        <div class="control">
            <label for="code">快递单号/取件码 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i> </label>
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
                            <option value="${item.id}">${item.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="control">
            <label for="distributionType">选择配送方式 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i>
            </label>
            <div class="select">
                <select id="distributionType" onchange="calcServiceAmt();">
                    <option value="">请选择配送方式</option>
                    <option value="box">配送入柜</option>
                    <option value="door">送货上门</option>
                </select>
            </div>
        </div>

        <div class="control" id="expressWeightDiv">
            <label for="expressWeight">物品重量(KG) <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i>
            </label>
            <input id="expressWeight" type="text" class="input" value="1" onblur="calcServiceAmt();"
                   onkeyup="this.value=this.value.replace(/[^\d\.]/g, '')">
        </div>

        <div class="control">
            <label for="helpReceiveAddr">取件地址 <i class="icon icon-asterisk"
                                                 style="font-size: 5px;color:red"></i> </label>
            <input id="helpReceiveAddr" type="text" class="input">
        </div>

        <div class="control">
            <label for="remark">备注</label>
            <textarea style="width: 100%;" class="textarea" cols="10" id="remark" placeholder="有什么想对我们备注的就写在这里吧！比如：预约配送时间等。限200字！"
                      maxlength="200"></textarea>
        </div>

        <div class="text-right" style="margin-top: 20px; margin-bottom: 20px">
            ￥服务费:<span class="price" id="serviceAmt">0.00</span>元
        </div>

        <div class="control text-center">
            <span id="confirm">
                <button type="button" class="btn primary">提交</button>
            </span>
        </div>
    </form>
</section>

<script>

    function calcServiceAmt() {
        var openId = $("#openId").val();
        var expressWeight = $("#expressWeight").val();
        var distributionType = $("#distributionType").val();
        var data = {openId: openId};
        data.expressWeight = expressWeight;
        data.distributionType = distributionType;
        if (distributionType != '' && expressWeight > 0) {
            $.get("/calc/1/help/serviceAmt", data, function (result) {
                if (result.status != 200) {
                    alert(result.msg);
                } else {
                    $("#serviceAmt").html(result.data);
                }
            });
        } else {
            $("#serviceAmt").html("0.00");
        }
    }

    window.alert = function (name) {
        var iframe = document.createElement("IFRAME");
        iframe.style.display = "none";
        iframe.setAttribute("src", 'data:text/plain,');
        document.documentElement.appendChild(iframe);
        window.frames[0].window.alert(name);
        iframe.parentNode.removeChild(iframe);
    };

    $("#confirm").click(function () {
        var openId = $("#openId").val();
        var code = $("#code").val();
        var receiverName = $("#receiverName").val();
        var receiverPhone = $("#receiverPhone").val();
        var companyId = $("#company").val();
        var helpReceiveAddr = $("#helpReceiveAddr").val();
        var expressWeight = $("#expressWeight").val();
        var distributionType = $("#distributionType").val();
        var remark = $("#remark").val();

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
        if (distributionType != '') {
            data.distributionType = distributionType;
        } else {
            alert("请选择配送方式");
            return false;
        }
        if (expressWeight != '') {
            data.expressWeight = expressWeight;
        } else {
            alert("请输入物品重量");
            return false;
        }
        data.remark = remark;
        $.post("/express/1/help/create", data, function (result) {
            if (result.status != 200) {
                alert(result.msg);
            } else {
                if (result.data != null) {
                    var orderNo = result.data;
                    window.location.href = "http://www.glove1573.cn/wxpay/pay?orderNo=" + orderNo;
                } else {
                    window.location.href = "http://www.glove1573.cn/express/1/list?status=0,1,2,3,4";
                }
            }
        });
    });

</script>
</body>
</html>