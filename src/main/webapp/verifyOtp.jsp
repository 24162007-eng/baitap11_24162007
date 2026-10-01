<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xác thực OTP</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="auth-shell">
        <div class="auth-card">
            <div class="auth-icon">&#128274;</div>
            <h2>Nhập mã OTP</h2>
            <p class="auth-subtitle">Mã OTP đã được gửi về email của bạn</p>

            <c:if test="${not empty error}">
                <p class="form-error">${error}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/verifyOtp" method="post">
                <input type="hidden" name="username" value="${username}" />
                <label class="field-label">Mã OTP</label>
                <input type="text" name="otp" placeholder="Nhập mã OTP" required />

                <button type="submit" class="btn btn-primary btn-block" style="margin-top:20px;">Xác thực</button>
            </form>
        </div>
    </div>
</body>
</html>