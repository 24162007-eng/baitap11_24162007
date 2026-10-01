<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head><title>Đơn hàng của tôi</title></head>
<body>

<div class="page-title">
    <div>
        <h2>Đơn hàng của tôi</h2>
    </div>
</div>

<c:choose>
    <c:when test="${empty orders}">
        <div class="empty-state">
            <div class="empty-icon">&#128230;</div>
            Bạn chưa có đơn hàng nào.
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
                            <td><c:out value="${o.status}"/></td>
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