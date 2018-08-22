<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" href="/css/cssReset.css">
    <!-- ZUI 标准版压缩后的 CSS 文件 -->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/css/zui.min.css">
    <link rel="stylesheet" href="/css/sudi.css">
    <style>
        .profile-haeder {position: relative; overflow: hidden; min-height: 8rem}
        .profile-haeder > .front {background: rgba(0,0,0,0.49); text-align: center; padding: 1.5rem}
    </style>
</head>
<body>
<input type="hidden" value="${customer.openId}" id="openId" />
<div class="wrap container sendPiece">
    <div class="profile-haeder space shadow">
        <div class="front dock text-white">
            <img src="${customer.avatar}" alt="" class="img-circle" style="height: 70px">
            <h4 class="lead text-shadow-black">${customer.nickname}</h4>
            <h4>编辑个人信息</h4>
        </div>
    </div>

    <div class="input-control has-icon-right">
        <input id="name" type="text" class="form-control" placeholder="姓名" value="${customer.name}">
    </div>
    <div class="input-control has-icon-right">
        <input id="phone" type="text" class="form-control" placeholder="手机号" value="${customer.phone}">
        <label class="input-control-icon-right"><i class="icon icon-asterisk"></i></label>
    </div>
    <div class="input-control has-icon-right">
        <input id="idNumber" type="text" class="form-control" placeholder="身份证号" value="${customer.idNumber}">
        <label class="input-control-icon-right"><i class="icon icon-asterisk"></i></label>
    </div>
    <div class="row" id="verify_code">
        <div class="input-control has-icon-right col-xs-6">
            <input id="code" type="text" class="form-control" placeholder="验证码">
        </div>
        <div class="col-xs-4">
            <button class="btn getCode">获取验证码</button>
        </div>
    </div>
    <div class="input-control has-icon-right">
        <input id="email" type="text" class="form-control" placeholder="邮箱" value="${customer.email}">
    </div>
    <div class="input-control has-icon-right">
        <input id="addr" type="text" class="form-control" placeholder="寝室详细地址" value="${customer.addr}">
        <label class="input-control-icon-right"><i class="icon icon-asterisk"></i></label>
    </div>
    <div class="row btnGroup">
        <div class="col-xs-6" id="reset">
            <button class="btn btn-danger " type="button">重置</button>
        </div>
        <div class="col-xs-6" id="confirm">
            <button class="btn btn-success " type="button">确定</button>
        </div>
    </div>

    <div class="alert alert-success-inverse text-center" id="feedback-suc">修改成功</div>
    <div class="alert alert-warning-inverse text-center" id="feedback-err"></div>
</div>

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
        return;
      }
      if (!phone.match(/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/)) {
        $("#feedback-err").html("手机号格式错误");
        $("#feedback-err").show();
        $("#feedback-err").fadeOut(5000);
        return;
      }
      $.post("/customer/verifyCode", {"phone": phone, "openId":$("#openId").val()}, function (result) {
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
    $("#idNumber").val('');
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
    var idNumber = $("#idNumber").val();
    var phone = '';
    var code = '';
    if ($("#phone").attr("readonly") != 'readonly') {
      phone = $("#phone").val();
      code = $("#code").val();
    }
    var data = {openId : openId};
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
    if (idNumber != '') {
      data.idNumber = idNumber;
    }
    if (code != '') {
      data.code = code;
    }
    $.post("/customer/profile", data, function (result) {
      if (result != "success") {
        $("#feedback-err").html(result);
        $("#feedback-err").show();
        $("#feedback-err").fadeOut(5000);
      } else {
        $("#feedback-suc").show();
        $("#feedback-suc").fadeOut(5000);
      }
    });
  });
</script>
</body>
</html>