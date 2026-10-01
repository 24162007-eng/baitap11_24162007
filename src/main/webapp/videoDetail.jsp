<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head><title><c:out value="${video.title}"/></title></head>
<body>

<div class="detail">
    <div class="poster">
        <c:choose>
            <c:when test="${not empty video.poster}">
                <img src="${ctx}/media/${video.poster}" alt="poster" onerror="this.style.display='none'" />
            </c:when>
            <c:otherwise><div class="poster-ph">&#127916;</div></c:otherwise>
        </c:choose>
    </div>
    <div class="info">
        <h2>Tiêu đề: <c:out value="${video.title}"/></h2>
        <p class="meta-line"><b>Mã video:</b> <c:out value="${video.videoId}"/></p>
        <p class="meta-line"><b>Category name:</b> <c:out value="${video.categoryname}"/></p>
        <p class="meta-line"><b>View:</b> ${video.views}</p>
        <div class="stat-chips">
            <span class="chip">&#128257; Share(${video.shareCount})</span>
            <span class="chip">&#10084; Like(${video.favoriteCount})</span>
        </div>
        <div class="description"><c:out value="${video.description}"/></div>
		<p class="price"><fmt:formatNumber value="${video.price}" pattern="#,##0"/>đ &nbsp;|&nbsp; Còn ${video.stock} sản phẩm</p>
		<c:choose>
		    <c:when test="${video.stock <= 0}">
		        <span class="badge badge-muted">Hết hàng</span>
		    </c:when>
		    <c:when test="${empty sessionScope.currentUser}">
		        <a class="btn btn-primary" href="${ctx}/login.jsp">Đăng nhập để mua</a>
		    </c:when>
		    <c:when test="${not sessionScope.currentUser.admin}">
		        <form class="qty-form buy-form" action="${ctx}/cart" method="post">
		            <input type="hidden" name="action" value="add">
		            <input type="hidden" name="videoId" value="<c:out value='${video.videoId}'/>">
		            <input type="number" name="quantity" value="1" min="1" max="${video.stock < 10 ? video.stock : 10}">
		            <button type="submit" class="btn btn-primary">Thêm vào giỏ</button>
		        </form>
		    </c:when>
		</c:choose>
        <c:if test="${not empty video.videoFile}">
            <video controls preload="metadata" style="width:100%;max-width:560px;margin-top:14px;border-radius:10px;"
                   src="${ctx}/media/${video.videoFile}"></video>
        </c:if>
    </div>
</div>

</body>
</html>
