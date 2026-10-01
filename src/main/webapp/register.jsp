<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng ký</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="auth-shell">
        <div class="auth-card" style="max-width:420px;">
            <div class="auth-icon">&#128221;</div>
            <h2>Đăng ký tài khoản</h2>
            <p class="auth-subtitle">Tạo tài khoản để bắt đầu xem video</p>

            <c:if test="${not empty error}">
                <p class="form-error">${error}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="post">
                <label class="field-label">Tên đăng nhập</label>
                <input type="text" name="username" placeholder="Tên đăng nhập" required />

                <label class="field-label">Mật khẩu</label>
                <input type="password" name="password" placeholder="Mật khẩu" required />

                <label class="field-label">Họ tên</label>
                <input type="text" name="fullname" placeholder="Họ tên" required />

                <label class="field-label">Số điện thoại</label>
                <input type="text" name="phone" placeholder="Số điện thoại" />

                <label class="field-label">Email</label>
                <input type="email" name="email" placeholder="Email" required />

                <button type="submit" class="btn btn-primary btn-block" style="margin-top:20px;">Đăng ký</button>
            </form>

            <a class="auth-link" href="${pageContext.request.contextPath}/login.jsp">
                Đã có tài khoản? Đăng nhập
            </a>
        </div>
    </div>
</body>
</html>