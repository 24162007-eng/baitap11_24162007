<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head><title><c:out value="${category.categoryname}"/></title></head>
<body>

<%-- Câu 6: đếm số video theo từng Category --%>
<div class="cat-tabs">
    <c:forEach var="cat" items="${allCategories}">
        <a class="cat-tab ${cat.categoryId == category.categoryId ? 'active' : ''}"
           href="${ctx}/category?categoryId=${cat.categoryId}">
            <c:out value="${cat.categoryname}"/> (${empty videoCounts[cat.categoryId] ? 0 : videoCounts[cat.categoryId]})
        </a>
    </c:forEach>
</div>

<div class="page-title">
    <div>
        <h2><c:out value="${category.categoryname}"/> (${totalVideos})</h2>
        <div class="subtitle">${totalVideos} video trong danh mục này</div>
    </div>
</div>

<c:choose>
    <c:when test="${empty videos}">
        <div class="empty-state">
            <div class="empty-icon">&#128561;</div>
            Chưa có video nào trong danh mục này.
        </div>
    </c:when>
    <c:otherwise>
        <div class="videoGrid">
            <c:forEach var="v" items="${videos}">
                <div class="videoCard">
                    <c:choose>
                        <c:when test="${not empty v.poster}">
                            <img src="${ctx}/media/${v.poster}" alt="poster" onerror="this.style.display='none'" />
                        </c:when>
                        <c:otherwise><div class="poster-ph">&#127916;</div></c:otherwise>
                    </c:choose>
                    <div class="videoCard-body">
                        <p class="videoCard-title">Tiêu đề: <c:out value="${v.title}"/></p>
                        <p class="meta-row"><b>Mã video:</b> <c:out value="${v.videoId}"/></p>
                        <p class="meta-row"><b>Category name:</b> <c:out value="${category.categoryname}"/></p>
                        <p class="meta-row"><b>View:</b> ${v.views}</p>
                        <div class="stat-chips">
                            <span class="chip">&#128257; Share(${v.shareCount})</span>
                            <span class="chip">&#10084; Like(${v.favoriteCount})</span>
                        </div>
						<p class="price"><fmt:formatNumber value="${v.price}" pattern="#,##0"/>đ</p>
						<c:choose>
						    <c:when test="${v.stock <= 0}">
						        <span class="badge badge-muted" style="margin-bottom:10px;">Hết hàng</span>
						    </c:when>
						    <c:when test="${empty sessionScope.currentUser}">
						        <a class="btn btn-primary btn-block buy-form" href="${ctx}/login.jsp">Đăng nhập để mua</a>
						    </c:when>
						    <c:when test="${not sessionScope.currentUser.admin}">
						        <form class="buy-form" action="${ctx}/cart" method="post">
						            <input type="hidden" name="action" value="add">
						            <input type="hidden" name="videoId" value="<c:out value='${v.videoId}'/>">
						            <input type="hidden" name="quantity" value="1">
						            <button type="submit" class="btn btn-primary btn-block">Thêm vào giỏ</button>
						        </form>
						    </c:when>
						</c:choose>
                        <a class="btn btn-ghost btn-block" href="${ctx}/videoDetail?videoId=<c:out value='${v.videoId}'/>">Xem chi tiết</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<c:if test="${totalPages > 1}">
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="${ctx}/category?categoryId=${category.categoryId}&page=${currentPage - 1}">&laquo;</a>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span>${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${ctx}/category?categoryId=${category.categoryId}&page=${i}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="${ctx}/category?categoryId=${category.categoryId}&page=${currentPage + 1}">&raquo;</a>
        </c:if>
    </div>
</c:if>

</body>
</html>
