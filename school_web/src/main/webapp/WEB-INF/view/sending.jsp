<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我要寄件</title>
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
            <label for="receiverName">收件人姓名 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i> </label>
            <input id="receiverName" type="text" class="input">
        </div>
        <div class="control">
            <label>收件人地址 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i> </label>
            <div class="flex-inline">
                <div class="select" style="width: 30%;margin-right: 5px">
                    <select id="province" name="province" onchange="change(this,'city');">
                        <option value="">[请选择省]</option>
                        <c:if test="${regionList != null and regionList.size() > 0}">
                            <c:forEach items="${regionList}" varStatus="var" var="item">
                                <option value="${item.id}">${item.areaName}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
                <div class="select" style="width: 33%;margin-right: 5px">
                    <select id="city" name="city" onchange="change(this,'area');">
                        <option value="">[请选择市]</option>
                    </select>
                </div>
                <div class="select" style="width: 30%">
                    <select id="area" name="area" onchange="calcAmount();">
                        <option value="">[请选择区/县]</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="control">
            <label for="receiverAddr">收件人详细地址 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i>
            </label>
            <input id="receiverAddr" type="text" class="input">
        </div>
        <div class="control">
            <label for="receiverPhone">收件人手机号 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i>
            </label>
            <input id="receiverPhone" type="text" class="input">
        </div>

        <div class="control">
            <label for="company">选择快递公司 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i> </label>
            <div class="select">
                <select id="company" name="company" onchange="calcAmount();">
                    <option value="">请选择快递公司</option>
                    <option value="2">顺丰</option>
                    <option value="1">其它</option>
                </select>
            </div>
        </div>

        <div class="control">
            <label for="expressWay">选择寄件方式 <i class="icon icon-asterisk" style="font-size: 5px;color:red"></i> </label>
            <div class="select">
                <select id="expressWay" name="expressWay" onchange="calcServiceAmt();">
                    <option value="">请选择寄件方式</option>
                    <option value="0">自送到点</option>
                    <option value="1">上门取件</option>
                </select>
            </div>
        </div>

        <div class="control">
            <label for="expressWay">选择快件类型<i class="icon icon-asterisk" style="font-size: 5px;color:red"></i> </label>
            <div class="select">
                <select id="expressType" name="expressType">
                    <option value="">请选择快件类型</option>
                    <option value="0">其他</option>
                    <option value="1">文件</option>
                    <option value="2">数码产品</option>
                    <option value="3">日用品</option>
                    <option value="4">服饰</option>
                    <option value="5">食品</option>
                    <option value="6">医药类产品</option>
                </select>
            </div>
        </div>

        <div class="text-right" style="margin-top: 20px; margin-bottom: 20px">
            ￥首重预收运费:<span class="price" id="price">0.00</span>&nbsp;&nbsp;&nbsp;￥服务费:<span class="price"
                                                                                           id="serviceAmt">0.00</span>元
        </div>

        <div class="control text-center">
            <span id="confirm">
                <button type="button" class="btn primary" disabled="disabled">提交</button>
            </span>
        </div>
    </form>
</section>

<script>

    window.alert = function (name) {
        var iframe = document.createElement("IFRAME");
        iframe.style.display = "none";
        iframe.setAttribute("src", 'data:text/plain,');
        document.documentElement.appendChild(iframe);
        window.frames[0].window.alert(name);
        iframe.parentNode.removeChild(iframe);
    };

    function change(e, id) {
        if (id == 'city') {
            $("#area").html("<option value=''>[请选择区/县]</option>");
        }
        var html = "";
        $.get("/region/list", {"parentId": e.value}, function (result) {
            if (id == 'city') {
                html += "<option value=''>[请选择市]</option>";
                $("#price").html("0.00");
            } else {
                html += "<option value=''>[请选择区/县]</option>";
                $("#price").html("0.00");
            }
            $.each(result.data, function (index, item) {
                html += "<option value='" + item.id + "'>" + item.areaName + "</option>";
            });
            $("#" + id).html(html);
        });
    }

    function calcServiceAmt() {
        var expressWay = $("#expressWay").val();
        if (expressWay == 1) {
            $.get("/calc/0/serviceAmt", {}, function (result) {
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


    function calcAmount() {
        var openId = $("#openId").val();
        var data = {openId: openId};
        var receiverProvinceId = $("#province").val();
        var receiverCityId = $("#city").val();
        var receiverDistrictId = $("#area").val();
       var companyId = $("#company").val();
        if (receiverProvinceId != '') {
            data.receiverProvinceId = receiverProvinceId;
        } else {
            return false;
        }
        if (receiverCityId != '') {
            data.receiverCityId = receiverCityId;
        } else {
            return false;
        }
        if (receiverDistrictId != '') {
            data.receiverDistrictId = receiverDistrictId;
        } else {
            $("#price").html("0.00");
            return false;
        }
       if (companyId != '') {
           data.companyId = companyId;
       } else {
          return false;
       }
        $.get("/calc/0", data, function (result) {
            if (result.status != 200) {
                alert(result.msg);
            } else {
                $("#price").html(result.data);
                $("#confirm").attr("disabled", false);
            }
        });


    }

    $("#confirm").click(function () {
        var openId = $("#openId").val();
        var receiverPhone = $("#receiverPhone").val();
        var receiverName = $("#receiverName").val();
        var receiverAddr = $("#receiverAddr").val();
        var companyId = $("#company").val();
        var receiverProvinceId = $("#province").val();
        var receiverCityId = $("#city").val();
        var receiverDistrictId = $("#area").val();
        var expressWay = $("#expressWay").val();
        var expressType = $("#expressType").val();
        if (openId == '') {
            alert("参数错误");
            return false;
        }
        var data = {openId: openId};

        if (receiverName != '') {
            data.receiverName = receiverName;
        } else {
            alert("请输入收件人姓名");
            return false;
        }
        if (receiverProvinceId != '') {
            data.receiverProvinceId = receiverProvinceId;
        } else {
            alert("请选择收件人省份");
            return false;
        }
        if (receiverCityId != '') {
            data.receiverCityId = receiverCityId;
        } else {
            alert("请选择收件人市区");
            return false;
        }
        if (receiverDistrictId != '') {
            data.receiverDistrictId = receiverDistrictId;
        } else {
            alert("请选择收件人区县");
            return false;
        }
        if (receiverAddr != '') {
            data.receiverAddr = receiverAddr;
        } else {
            alert("请输入收件人详细地址");
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
        if (expressWay != '') {
            data.expressWay = expressWay;
        } else {
            alert("请选择寄件方式");
            return false;
        }
        if (expressType != '') {
            data.expressType = expressType;
        } else {
            alert("请选择寄件类型");
            return false;
        }
        $.post("/express/0/create", data, function (result) {
            if (result.status != 200) {
                alert(result.msg);
            } else {
                var orderNo = result.data;
                window.location.href = "http://www.glove1573.cn/wxpay/pay?orderNo=" + orderNo;
            }
        });
    });

</script>

</body>
</html>