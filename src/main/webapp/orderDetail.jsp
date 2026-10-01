<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head><title>Đơn hàng #${order.orderId}</title></head>
<body>

<c:if test="${success}">
    <div class="flash-ok">Đặt hàng thành công! Bạn sẽ thanh toán tiền mặt khi nhận hàng.</div>
</c:if>

<div class="page-title">
    <div>
        <h2>Đơn hàng #${order.orderId}</h2>
        <div class="subtitle">
            Đặt lúc <fmt:formatDate value="${order.createdDate}" pattern="dd/MM/yyyy HH:mm"/>
            &nbsp;|&nbsp; Trạng thái: <span class="badge ${order.statusClass}"><c:out value="${order.statusLabel}"/></span>
        </div>
    </div>
</div>

<div class="checkout-layout">
    <div class="form-card">
        <h3>Thông tin giao hàng</h3>
        <p class="meta-line"><b>Người nhận:</b> <c:out value="${order.receiverName}"/></p>
        <p class="meta-line"><b>Số điện thoại:</b> <c:out value="${order.phone}"/></p>
        <p class="meta-line"><b>Địa chỉ:</b> <c:out value="${order.address}"/></p>
        <p class="meta-line"><b>Ghi chú:</b> <c:out value="${empty order.note ? '(Không có)' : order.note}"/></p>
        <p class="meta-line"><b>Thanh toán:</b> <span class="badge badge-success"><c:out value="${order.paymentMethod}"/></span></p>
    </div>

    <div class="form-card">
        <h3>Sản phẩm</h3>
        <table class="data-table user-table">
            <thead>
                <tr><th>Sản phẩm</th><th>Đơn giá</th><th>SL</th><th>Thành tiền</th></tr>
            </thead>
            <tbody>
                <c:forEach var="d" items="${order.details}">
                    <tr>
                        <td><c:out value="${d.title}"/></td>
                        <td><fmt:formatNumber value="${d.price}" pattern="#,##0"/>đ</td>
                        <td>${d.quantity}</td>
                        <td><fmt:formatNumber value="${d.total}" pattern="#,##0"/>đ</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="cart-total" style="margin-top:14px;">Tổng cộng: <fmt:formatNumber value="${order.totalAmount}" pattern="#,##0"/>đ</div>
    </div>
</div>

<div style="margin-top:18px;">
    <a class="btn btn-ghost" href="${ctx}/orders">Danh sách đơn hàng</a>
    <a class="btn btn-primary" href="${ctx}/category">Tiếp tục mua sắm</a>
</div>

</body>
</html>