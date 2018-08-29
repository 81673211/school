<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link href="/css/mzui.min.css" rel="stylesheet"/>
    <style>
        .profile-haeder {
            position: relative;
            overflow: hidden;
            min-height: 8rem
        }

        .profile-haeder > .front {
            background: rgba(0, 0, 0, 0.49);
            text-align: center;
            padding: 1.5rem
        }
    </style>
</head>
<body>
<input type="hidden" value="${customer.openId}" id="openId"/>


<div class="profile-haeder shadow">
    <div class="red dock blur-lg back" style="background-image: url(/img/profile_backimg.jpg)"></div>
    <div class="front dock text-white">
        <div class="avatar avatar-xl circle space-sm">
            <img src="${customer.avatar}">
        </div>
        <h4 class="lead text-shadow-black">${customer.nickname}</h4>
    </div>
</div>

<section class="section">
    <form class="box" onsubmit="return false;">
        <div class="control">
            <label for="name">用户名</label>
            <input id="name" type="text" class="input" placeholder="用户名" value="${customer.name}">
        </div>
        <div class="control">
            <label for="phone">手机号</label>
            <input id="phone" type="text" class="input" placeholder="手机号(必填)" value="${customer.phone}">
        </div>
        <div id="verify_code">
            <div class="control flex">
                <span style="margin-right: 20px">
                    <input id="code" type="text" class="input" placeholder="验证码">
                </span>
                <span>
                    <button class="btn gray getCode">获取验证码</button>
                </span>
            </div>
        </div>
        <div class="control">
            <label for="email">电子邮箱</label>
            <input id="email" type="email" class="input" placeholder="example@email.com"
                   value="${customer.email}">
        </div>
        <div class="control">
            <label for="addr">寝室地址</label>
            <input id="addr" type="text" class="input" placeholder="寝室详细地址，方便小哥上门取件" value="${customer.addr}">
        </div>
        <div class="control text-center" style="margin-top: 20px">
            <span id="confirm">
                <button type="button" class="btn primary">提交</button>
            </span>
            <span id="reset">
                <button type="button" class="btn">重置</button>
            </span>
        </div>
    </form>
</section>

<!-- ZUI Javascript 依赖 jQuery -->
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>
<script>
    $(document).ready(function () {
        if ($("#phone").val() != '') {
            $("#verify_code").hide();
            $("#phone").attr("readonly", "readonly");
        }
        $("#feedback-suc").hide();
        $("#feedback-err").hide();
    });
    $('.getCode').click(function () {
        if ($(this).html() == '获取验证码') {
            var phone = $("#phone").val();
            if (phone == null || phone == '') {
                $("#feedback-err").html("手机号不能为空");
                $("#feedback-err").show();
                $("#feedback-err").fadeOut(5000);
                return false;
            }
            if (!phone.match(/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/)) {
                $("#feedback-err").html("手机号格式错误");
                $("#feedback-err").show();
                $("#feedback-err").fadeOut(5000);
                return false;
            }
            $.post("/customer/verifyCode", {"phone": phone, "openId": $("#openId").val()}, function (result) {
                if (result == "1") {
                    var i = 60;
                    var codeNum;
                    setInterval(function () {
                        i--;
                        codeNum = i + "秒";
                        $('.getCode').html(codeNum);
                        if (i < 1) {
                            i = 60;
                            $('.getCode').html('获取验证码');
                        }
                    }, 1000);
                }
            });
        }
    });
    $("#reset").click(function () {
        $("#feedback-suc").hide();
        $("#feedback-err").hide();
        $("#name").val('');
        $("#email").val('');
        $("#addr").val('');
        if ($("#phone").attr("readonly") != 'readonly') {
            $("#phone").val('');
            $("#code").val('');
        }
    });
    $("#confirm").click(function () {
        $("#feedback-suc").hide();
        $("#feedback-err").hide();
        var openId = $("#openId").val();
        var name = $("#name").val();
        var email = $("#email").val();
        var addr = $("#addr").val();
        var phone = '';
        var code = '';
        if ($("#phone").attr("readonly") != 'readonly') {
            phone = $("#phone").val();
            code = $("#code").val();
        }
        var data = {openId: openId};
        if (name != '') {
            data.name = name;
        }
        if (email != '') {
            data.email = email;
        }
        if (addr != '') {
            data.addr = addr;
        }
        if (phone != '') {
            data.phone = phone;
        }
        if (code != '') {
            data.code = code;
        }
        $.post("/customer/profile", data, function (result) {
            if (result != "success") {
                alert(result);
            } else {
              alert("修改成功");
            }
        });
    });
</script>
</body>

<jsp:include page="limit.jsp"/>
</html>