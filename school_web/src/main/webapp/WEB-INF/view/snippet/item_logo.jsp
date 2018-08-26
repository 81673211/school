<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${param.companyName == '顺丰快递'}"><div class="avatar black circle">顺</div></c:when>
    <c:when test="${param.companyName == '申通'}"><div class="avatar blue circle">申</div></c:when>
    <c:choose><div class="avatar blue circle">？</div></c:choose>
</c:choose>
