<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${param.companyId == 1}"><div class="avatar warning circle">?</div></c:when>
    <c:when test="${param.companyId == 2}"><div class="avatar dark circle">顺</div></c:when>
    <c:when test="${param.companyId == 3}"><div class="avatar danger circle">E</div></c:when>
    <c:when test="${param.companyId == 4}"><div class="avatar special circle">圆</div></c:when>
    <c:when test="${param.companyId == 5}"><div class="avatar gray circle">申</div></c:when>
    <c:when test="${param.companyId == 6}"><div class="avatar yellow circle">韵</div></c:when>
    <c:when test="${param.companyId == 7}"><div class="avatar blue-pale circle">中</div></c:when>
    <c:when test="${param.companyId == 8}"><div class="avatar red-pale circle">百</div></c:when>
    <c:when test="${param.companyId == 9}"><div class="avatar blue circle">天</div></c:when>
    <c:when test="${param.companyId == 10}"><div class="avatar green circle">邮</div></c:when>
    <c:when test="${param.companyId == 11}"><div class="avatar red circle">京</div></c:when>
    <c:otherwise><div class="avatar warning circle">?</div></c:otherwise>
</c:choose>
