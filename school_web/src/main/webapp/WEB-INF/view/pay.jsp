<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
</head>
<body>
<script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
<script type="application/javascript">
    $(document).ready(function () {
      $.post("/wxpay/pay", {'orderNo':'${orderNo}'});
    });
</script>
</body>
</html>
