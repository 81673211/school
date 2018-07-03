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
<body>
<div class="">
    <div class="heading">
        <div class="title"><h3>我收到过的快件</h3></div>
    </div>
    <div class="list section">
        <c:if test="${list != null and list.size() > 0}">
            <c:forEach items="${list}" varStatus="var" var="item">
                <a class="item multi-lines with-avatar">
                    <div class="avatar circle blue outline">${var.count}</div>
                    <div class="content">
                        <span class="title">${item.code}</span>
                        <div>
                            <small class="text-gray">${item.companyName}</small>&nbsp;
                            <small class="muted">2018-07-01 20:00</small>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </c:if>
    </div>
</div>

<!-- ZUI Javascript 依赖 jQuery -->
<script src="/lib/jquery/jquery-3.2.1.min.js" />
<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
<script src="/js/mzui.min.js" />

</body>
</html>