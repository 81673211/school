<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        var isWP = ua.indexOf('Windows Phone') != -1;
        var isAndroid = ua.indexOf('android') != -1;
        var isIos = (ua.indexOf('iphone') != -1) || (ua.indexOf('ipad') != -1);
        if (!isWeixin && !isWP) {
            document.head.innerHTML = '<title>抱歉</title><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0"><link rel="stylesheet" type="text/css" href="https://res.wx.qq.com/open/libs/weui/0.4.1/weui.css">';
            document.body.innerHTML = "<div class='weui_msg'><div class='weui_icon_area'><i class='weui_icon_info weui_icon_msg'></i></div><div class='weui_text_area'><h4 class='weui_msg_title'>请在微信客户端打开链接</h4></div>";
        }
    });
</script>
