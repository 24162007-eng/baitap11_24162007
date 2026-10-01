<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property='title'/></title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/assets/css/style.css">
    <sitemesh:write property='head'/>
</head>
<body>
    <header class="app-header">
        <a class="brand" href="${ctx}/home"><span class="logo-dot"></span> Shop Thời Trang</a>
        <nav class="app-nav">
			<a href="${ctx}/cart">Giỏ hàng (${empty sessionScope.cart ? 0 : fn:length(sessionScope.cart)})</a>
            <a href="${ctx}/home">Trang Chủ</a>
            <a href="${ctx}/category">Sản phẩm</a>
            <c:if test="${empty sessionScope.currentUser}">
                <a href="${ctx}/login.jsp">Đăng nhập</a>
            </c:if>
            <c:if test="${not empty sessionScope.currentUser}">
                <c:if test="${sessionScope.currentUser.admin}">
                    <a href="${ctx}/admin/users">Trang quản trị</a>
                </c:if>
				<c:if test="${not sessionScope.currentUser.admin}">
				    <a href="${ctx}/orders">Đơn hàng</a>
				</c:if>
                <a href="${ctx}/logout">Đăng xuất</a>
            </c:if>
        </nav>
        <c:if test="${not empty sessionScope.currentUser}">
            <div class="app-user">
                <span class="avatar">${fn:toUpperCase(fn:substring(sessionScope.currentUser.fullname, 0, 1))}</span>
                Xin chào, <c:out value="${sessionScope.currentUser.fullname}"/>
            </div>
        </c:if>
    </header>
    <main class="app-main"><sitemesh:write property='body'/></main>
    <footer class="app-footer">
        Họ tên: Nguyễn Ngọc Tú Anh &nbsp;|&nbsp; MSSV: 24162007 &nbsp;|&nbsp; Mã đề: 04
    </footer>
</body>
</html>