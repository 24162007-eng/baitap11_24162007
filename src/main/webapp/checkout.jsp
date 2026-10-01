<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head><title>Thanh toán</title></head>
<body>

<div class="page-title">
    <div>
        <h2>Thanh toán đơn hàng</h2>
        <div class="subtitle">Thanh toán khi nhận hàng (COD)</div>
    </div>
</div>

<c:if test="${not empty error}">
    <div class="form-error"><c:out value="${error}"/></div>
</c:if>

<div class="checkout-layout">
    <div class="form-card">
        <h3>Thông tin giao hàng</h3>
        <form action="${ctx}/checkout" method="post">
            <label class="field-label">Họ tên người nhận</label>
            <input type="text" name="receiverName" maxlength="50" required
                   value="<c:out value='${receiverName}'/>" />

            <label class="field-label">Số điện thoại</label>
            <input type="tel" name="phone" maxlength="10" pattern="0[0-9]{9}" required
                   title="10 chữ số, bắt đầu bằng số 0" value="<c:out value='${phone}'/>" />

            <label class="field-label">Địa chỉ giao hàng</label>
            <textarea name="address" rows="3" maxlength="255" required><c:out value="${address}"/></textarea>

            <label class="field-label">Ghi chú (không bắt buộc)</label>
            <textarea name="note" rows="2" maxlength="255"><c:out value="${note}"/></textarea>

            <label class="field-label">Phương thức thanh toán</label>
            <div class="checkbox-row">
                <span class="badge badge-success">COD</span> Thanh toán tiền mặt khi nhận hàng
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Đặt hàng</button>
                <a class="btn btn-ghost" href="${ctx}/cart">Quay lại giỏ hàng</a>
            </div>
        </form>
    </div>

    <div class="form-card">
        <h3>Đơn hàng của bạn</h3>
        <table class="data-table user-table">
            <thead>
                <tr><th>Sản phẩm</th><th>SL</th><th>Thành tiền</th></tr>
            </thead>
            <tbody>
                <c:forEach var="it" items="${sessionScope.cart.values()}">
                    <tr>
                        <td><c:out value="${it.title}"/></td>
                        <td>${it.quantity}</td>
                        <td><fmt:formatNumber value="${it.total}" pattern="#,##0"/>đ</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="cart-total" style="margin-top:14px;">Tổng cộng: <fmt:formatNumber value="${cartTotal}" pattern="#,##0"/>đ</div>
    </div>
</div>

</body>
</html>