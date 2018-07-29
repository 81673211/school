<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我收到过的快件</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link href="/css/mzui.min.css" rel="stylesheet" />
</head>
<body class="white">
<div class="heading">
    <div class="title text-center secondary-pale"><h3>我收到过的快件</h3></div>
</div>
<c:choose>
    <c:when test="${list != null and list.size() > 0}">
        <div class="list with-divider">
            <c:forEach items="${list}" varStatus="var" var="item">
                <a class="item multi-lines with-avatar">
                    <div class="avatar circle blue outline">${var.count}</div>
                    <div class="content">
                        <span class="title">${item.code}</span>
                        <div>
                            <small class="text-gray">${item.companyName}</small>&nbsp;
                            <small class="text-gray">${item.senderPhone}</small>&nbsp;
                            <small class="text-gray">${item.senderName}</small>&nbsp;
                            <small class="muted">${item.endTime}</small>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <img width="30%" height="40%" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531471715&di=43b8048018a66b97871eb8a092013fee&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F011618576a5a5c0000018c1bea80fe.jpg%401280w_1l_2o_100sh.jpg" />
    </c:otherwise>
</c:choose>


<!-- ZUI Javascript 依赖 jQuery -->
<script src="/lib/jquery/jquery-3.2.1.min.js" />
<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
<script src="/js/mzui.min.js" />

</body>
</html>