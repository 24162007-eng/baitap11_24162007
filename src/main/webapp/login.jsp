<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng nhập</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="auth-shell">
        <div class="auth-card">
            <div class="auth-icon">&#128100;</div>
            <h2>Đăng nhập</h2>
            <p class="auth-subtitle">Chào mừng bạn quay lại Shop Thời Trang</p>

            <c:if test="${not empty error}">
                <p class="form-error">${error}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <label class="field-label">Tên đăng nhập</label>
                <input type="text" name="username" placeholder="Nhập tên đăng nhập" required />

                <label class="field-label">Mật khẩu</label>
                <input type="password" name="password" placeholder="Nhập mật khẩu" required />

                <button type="submit" class="btn btn-primary btn-block" style="margin-top:20px;">Đăng nhập</button>
            </form>

            <a class="auth-link" href="${pageContext.request.contextPath}/register">
                Chưa có tài khoản? Đăng ký ngay
            </a>
        </div>
    </div>
</body>
</html>