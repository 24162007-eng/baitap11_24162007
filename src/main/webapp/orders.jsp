<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head><title>Lịch sử đặt hàng</title></head>
<body>

<div class="page-title">
    <div>
        <h2>Lịch sử đặt hàng</h2>
        <div class="subtitle">Lọc đơn hàng theo trạng thái</div>
    </div>
</div>

<div class="cat-tabs">
    <a class="cat-tab ${empty currentStatus ? 'active' : ''}" href="${ctx}/orders">Tất cả (${totalOrders})</a>
    <c:forEach var="s" items="${statuses}">
        <a class="cat-tab ${currentStatus == s.code ? 'active' : ''}" href="${ctx}/orders?status=${s.code}">
            <c:out value="${s.label}"/> (${empty statusCounts[s.code] ? 0 : statusCounts[s.code]})
        </a>
    </c:forEach>
</div>

<c:choose>
    <c:when test="${empty orders}">
        <div class="empty-state">
            <div class="empty-icon">&#128230;</div>
            <c:choose>
                <c:when test="${empty currentStatus}">Bạn chưa có đơn hàng nào.</c:when>
                <c:otherwise>Không có đơn hàng nào ở trạng thái này.</c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-wrap">
            <table class="data-table user-table">
                <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Ngày đặt</th>
                        <th>Tổng tiền</th>
                        <th>Thanh toán</th>
                        <th>Trạng thái</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="o" items="${orders}">
                        <tr>
                            <td>#${o.orderId}</td>
                            <td><fmt:formatDate value="${o.createdDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td><fmt:formatNumber value="${o.totalAmount}" pattern="#,##0"/>đ</td>
                            <td><span class="badge badge-success"><c:out value="${o.paymentMethod}"/></span></td>
                            <td><span class="badge ${o.statusClass}"><c:out value="${o.statusLabel}"/></span></td>
                            <td><a class="btn btn-ghost btn-sm" href="${ctx}/orders?id=${o.orderId}">Xem chi tiết</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>