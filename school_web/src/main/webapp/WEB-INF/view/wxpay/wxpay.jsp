<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>微信支付</title>
    <script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
    <!-- 引入 jweixin-1.0.0.js-->
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
<input id="appId" type="hidden" name="appId" value="${resultMap.appId}" />
<input id="timeStamp" type="hidden" name="timeStamp" value="${resultMap.timeStamp}" />
<input id="nonceStr" type="hidden" name="nonceStr" value="${resultMap.nonceStr}" />
<input id="pkg" type="hidden" name="pkg" value="${resultMap.pkg}" />
<input id="signType" type="hidden" name="signType" value="${resultMap.signType}" />
<input id="paySign" type="hidden" name="paySign" value="${resultMap.paySign}" />
<input id="openId" type="hidden" name="openId" value="${resultMap.openId}" />
</body>
<script type="text/javascript">
  $(function () {
    callpay();
  });

  function onBridgeReady() {
    WeixinJSBridge.invoke(
      'getBrandWCPayRequest', {
        "appId": $("#appId").val(),     //公众号名称，由商户传入
        "timeStamp": $("#timeStamp").val(),         //时间戳，自1970年以来的秒数
        "nonceStr": $("#nonceStr").val(), //随机串
        "package": $("#pkg").val(),
        "signType": $("#signType").val(),         //微信签名方式：
        "paySign": $("#paySign").val() //微信签名
      },
      function (res) {
        if (res.err_msg == "get_brand_wcpay_request:ok") {
          window.location.href =
            "http://www.glove1573.cn/express/1/list?status=0,1,2,3,4&openId=" + $("#openId").val();
        }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
      }
    );
  }

  function callpay() {
    if (typeof WeixinJSBridge == "undefined") {
      if (document.addEventListener) {
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
      } else if (document.attachEvent) {
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
      }
    } else {
      onBridgeReady();
    }
  }
</script>
</html>