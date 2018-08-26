<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${param.companyId == 1}"><div class="avatar warning circle">?</div></c:when>
    <c:when test="${param.companyId == 2}"><div class="avatar dark circle">顺</div></c:when>
    <c:when test="${param.companyId == 3}"><div class="avatar green circle">E</div></c:when>
    <c:otherwise><div class="avatar warning circle">?</div></c:otherwise>
</c:choose>
