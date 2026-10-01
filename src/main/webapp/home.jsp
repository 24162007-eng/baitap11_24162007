<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Trang chủ</title></head>
<body>

<div class="page-title">
    <div>
        <h2>Danh mục sản phẩm</h2>
        <div class="subtitle">Chọn một danh mục để xem các video liên quan</div>
    </div>
</div>

<c:choose>
    <c:when test="${empty categories}">
        <div class="empty-state">
            <div class="empty-icon">&#128230;</div>
            Hiện chưa có danh mục nào.
        </div>
    </c:when>
    <c:otherwise>
        <div class="category-grid">
            <c:forEach var="cat" items="${categories}">
                <a class="category-card" href="${pageContext.request.contextPath}/category?categoryId=${cat.categoryId}">
                    <div class="cat-icon">&#128717;</div>
                    <h3><c:out value="${cat.categoryname}"/> (${empty videoCounts[cat.categoryId] ? 0 : videoCounts[cat.categoryId]})</h3>
                    <div class="cat-cta">Xem video &rarr;</div>
                </a>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>
