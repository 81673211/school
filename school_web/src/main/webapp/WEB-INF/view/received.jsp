<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我收到过的快件</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link href="/css/mzui.min.css" rel="stylesheet" />

    <script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/lib/jquery/jquery.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/zui/1.8.1/js/zui.min.js"></script>
    <script src="../../js/limit.js"></script>

</head>
<body>
<c:choose>
    <c:when test="${list != null and list.size() > 0}">
        <div class="list section with-divider">
            <c:forEach items="${list}" varStatus="var" var="item">
                <a class="item multi-lines with-avatar">
                    <jsp:include page="snippet/item_logo.jsp">
                        <jsp:param name="companyId" value="${item.companyId}"/>
                    </jsp:include>
                    <div class="content">
                        <div>
                            <div class="title inline">
                                <span>${item.code}</span>
                            </div>
                            <div>
                                <small class="muted"><fmt:formatDate value="${item.createdTime}" pattern="yyyy-MM-dd HH:mm" /></small>
                            </div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="center-content" style="margin-top: 40%">
            当前没有已收取的快递!
        </div>
    </c:otherwise>
</c:choose>


<!-- ZUI Javascript 依赖 jQuery -->
<script src="/lib/jquery/jquery-3.2.1.min.js" />
<!-- ZUI 标准版压缩后的 JavaScript 文件 -->
<script src="/js/mzui.min.js" />

</body>

</html>