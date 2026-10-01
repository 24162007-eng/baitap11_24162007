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
<body class="admin-scope">
    <header class="app-header is-admin">
        <a class="brand" href="${ctx}/admin/users"><span class="logo-dot"></span> Trang Quản Trị</a>
        <nav class="app-nav">
            <a href="${ctx}/home">Trang Chủ</a>
            <a href="${ctx}/category">Sản phẩm</a>
            <a href="${ctx}/admin/users">Quản lý User</a>
            <a href="${ctx}/admin/videos">Quản lý Video</a>
            <a href="${ctx}/logout">Đăng xuất</a>
        </nav>
        <div class="app-user">
            <span class="avatar">${fn:toUpperCase(fn:substring(sessionScope.currentUser.fullname, 0, 1))}</span>
            Admin: <c:out value="${sessionScope.currentUser.fullname}"/>
        </div>
    </header>
    <main class="app-main"><sitemesh:write property='body'/></main>
    <footer class="app-footer">
        Họ tên: Nguyễn Ngọc Tú Anh &nbsp;|&nbsp; MSSV: 24162007 &nbsp;|&nbsp; Mã đề: 04
    </footer>
</body>
</html>