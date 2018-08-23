<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>意见及建议</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link href="/css/mzui.min.css" rel="stylesheet" />
</head>
<body>
<input type="hidden" value="${openId}" id="openId" />

<div class="container" style="margin-top: 20px;text-align: center">
    <div>
        <textarea class="textarea form-control" rows="3" id="content"></textarea>
    </div>
    <div style="margin-top: 30px;text-align: center">
        <span class="col-xs-6" id="reset">
            <button type="button" class="btn warning rounded" style="margin-right: 30px">重置</button>
        </span>
        <span class="col-xs-6" id="confirm">
            <button type="button" class="btn success rounded">提交</button>
        </span>
    </div>
</div>

<div class="alert alert-success-inverse text-center" id="feedback-suc">提交成功</div>
<div class="alert alert-warning-inverse text-center" id="feedback-err"></div>
</div>

<!-- ZUI Javascript 依赖 jQuery -->
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>
<script>
  $(document).ready(function () {
    $("#feedback-suc").hide();
    $("#feedback-err").hide();
  });
  $("#reset").click(function () {
    $("#feedback-suc").hide();
    $("#feedback-err").hide();
    $("#content").val('');
  });
  $("#confirm").click(function () {
    $("#feedback-suc").hide();
    $("#feedback-err").hide();
    var openId = $("#openId").val();
    var content = $("#content").val();
    if (content == '') {
      $("#feedback-err").html("请填写意见或建议");
      $("#feedback-err").show();
      $("#feedback-err").fadeOut(5000);
      return;
    }
    var data = {openId: openId, content: content};
    $.post("/help/suggestion/push", data, function (result) {
      if (result.status != 200) {
        $("#feedback-err").html(result.msg);
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