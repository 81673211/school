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
</head>
<body>
<input type="hidden" value="${customer.openId}" id="openId" />
<div class="wrap container sendPiece">
    <h3 class="">个人信息</h3>
    <div class="input-control has-icon-right">
        <input id="name" type="text" class="form-control" placeholder="姓名" value="${customer.name}">
    </div>
    <div class="input-control has-icon-right">
        <input id="phone" type="text" class="form-control" placeholder="手机号" value="${customer.phone}">
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
        <input id="addr" type="text" class="form-control" placeholder="地址" value="${customer.addr}">
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


    <div class="alert alert-success-inverse" id="feedback-suc">修改成功</div>
    <div class="alert alert-warning-inverse" id="feedback-err"></div>



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
      $.post("/customer/verifyCode", {"phone": $("#phone").val()}, function (result) {
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
    if (code != '') {
      data.code = code;
    }
    $.post("/customer/profile", data, function (result) {
      if (result != "success") {
        $("#feedback-err").html(result);
        $("#feedback-err").show();
      } else {
        $("#feedback-suc").show();
      }
    });
  });
</script>
</body>
</html>