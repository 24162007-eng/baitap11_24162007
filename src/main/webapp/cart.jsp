<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head><title>Giỏ hàng</title></head>
<body>

<div class="page-title">
    <div>
        <h2>Giỏ hàng</h2>
        <div class="subtitle">Mỗi sản phẩm mua tối đa ${maxPerItem} và không vượt quá số lượng trong kho</div>
    </div>
</div>

<c:if test="${not empty cartMsg}">
    <div class="flash-ok"><c:out value="${cartMsg}"/></div>
</c:if>
<c:if test="${not empty cartError}">
    <div class="form-error"><c:out value="${cartError}"/></div>
</c:if>

<c:choose>
    <c:when test="${empty sessionScope.cart}">
        <div class="empty-state">
            <div class="empty-icon">&#128722;</div>
            Giỏ hàng của bạn đang trống.
            <div style="margin-top:14px;"><a class="btn btn-primary" href="${ctx}/category">Đi mua sắm</a></div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-wrap">
            <table class="data-table user-table">
                <thead>
                    <tr>
                        <th>Sản phẩm</th>
                        <th>Đơn giá</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="it" items="${sessionScope.cart.values()}">
                        <tr>
                            <td>
                                <a href="${ctx}/videoDetail?videoId=<c:out value='${it.videoId}'/>"><b><c:out value="${it.title}"/></b></a>
                                <div class="meta-row">Mã: <c:out value="${it.videoId}"/> | Còn ${it.stock} sản phẩm</div>
                            </td>
                            <td><fmt:formatNumber value="${it.price}" pattern="#,##0"/>đ</td>
                            <td>
                                <form class="qty-form" action="${ctx}/cart" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="videoId" value="<c:out value='${it.videoId}'/>">
                                    <input type="number" name="quantity" value="${it.quantity}" min="1"
                                           max="${it.stock < maxPerItem ? it.stock : maxPerItem}">
                                    <button type="submit" class="btn btn-ghost btn-sm">Cập nhật</button>
                                </form>
                            </td>
                            <td><b><fmt:formatNumber value="${it.total}" pattern="#,##0"/>đ</b></td>
                            <td>
                                <form action="${ctx}/cart" method="post">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="videoId" value="<c:out value='${it.videoId}'/>">
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="cart-summary">
            <div class="cart-total">Tổng cộng: <fmt:formatNumber value="${cartTotal}" pattern="#,##0"/>đ</div>
            <div class="cart-actions">
                <a class="btn btn-ghost" href="${ctx}/category">Tiếp tục mua</a>
                <form action="${ctx}/cart" method="post" onsubmit="return confirm('Xóa toàn bộ giỏ hàng?');">
                    <input type="hidden" name="action" value="clear">
                    <button type="submit" class="btn btn-danger">Xóa giỏ hàng</button>
                </form>
                <a class="btn btn-primary" href="${ctx}/checkout">Thanh toán</a>
            </div>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>