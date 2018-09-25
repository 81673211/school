<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${param.companyId == 1}"><div class="avatar warning circle">?</div></c:when>
    <c:when test="${param.companyId == 2}"><div class="avatar dark circle">顺丰</div></c:when>
    <c:when test="${param.companyId == 3}"><div class="avatar danger circle">EMS</div></c:when>
    <c:when test="${param.companyId == 4}"><div class="avatar special circle">圆通</div></c:when>
    <c:when test="${param.companyId == 5}"><div class="avatar gray circle">申通</div></c:when>
    <c:when test="${param.companyId == 6}"><div class="avatar yellow circle">韵达</div></c:when>
    <c:when test="${param.companyId == 7}"><div class="avatar blue-pale circle">中通</div></c:when>
    <c:when test="${param.companyId == 8}"><div class="avatar red-pale circle">百世</div></c:when>
    <c:when test="${param.companyId == 9}"><div class="avatar blue circle">天天</div></c:when>
    <c:when test="${param.companyId == 10}"><div class="avatar green circle">邮下</div></c:when>
    <c:when test="${param.companyId == 11}"><div class="avatar red circle">京东</div></c:when>
    <c:when test="${param.companyId == 12}"><div class="avatar blue-pale circle">优速</div></c:when>
    <c:otherwise><div class="avatar warning circle">?</div></c:otherwise>
</c:choose>
