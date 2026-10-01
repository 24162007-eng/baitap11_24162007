<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Người dùng</title></head>
<body>

<div class="page-title">
    <div>
        <h2>${empty user ? "Thêm người dùng" : "Cập nhật người dùng"}</h2>
        <div class="subtitle">Điền đầy đủ thông tin bên dưới</div>
    </div>
</div>

<div class="form-card">
    <c:if test="${param.error == 'exists'}"><p class="form-error">Username đã tồn tại, vui lòng chọn username khác.</p></c:if>
    <form action="${pageContext.request.contextPath}/admin/users" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="${empty user ? 'create' : 'update'}" />

        <div class="form-grid">
            <div class="full">
                <label class="field-label">Username</label>
                <input type="text" name="username" value="<c:out value='${user.username}'/>" ${not empty user ? 'readonly' : ''} required />
            </div>
            <div class="full">
                <label class="field-label">Mật khẩu</label>
                <input type="text" name="password" value="<c:out value='${user.password}'/>" required />
            </div>
            <div>
                <label class="field-label">Họ tên</label>
                <input type="text" name="fullname" value="<c:out value='${user.fullname}'/>" />
            </div>
            <div>
                <label class="field-label">SĐT</label>
                <input type="text" name="phone" value="<c:out value='${user.phone}'/>" />
            </div>
            <div class="full">
                <label class="field-label">Email</label>
                <input type="email" name="email" value="<c:out value='${user.email}'/>" />
            </div>
        </div>

        <div class="form-grid" style="margin-top:12px;">
            <div class="full">
                <label class="field-label">Ảnh đại diện (không bắt buộc)</label>
                <c:if test="${not empty user.images}">
                    <img src="${pageContext.request.contextPath}/media/${user.images}"
                         style="height:56px;border-radius:50%;display:block;margin-bottom:8px;"
                         onerror="this.style.display='none'" />
                </c:if>
                <input type="file" name="avatar" accept="image/*" />
            </div>
        </div>

        <div class="checkbox-row">
            <input type="checkbox" id="admin" name="admin" ${user.admin ? 'checked' : ''} />
            <label for="admin">Là quản trị viên (Admin)</label>
        </div>
        <div class="checkbox-row">
            <input type="checkbox" id="active" name="active" ${user.active ? 'checked' : ''} />
            <label for="active">Kích hoạt tài khoản</label>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-admin">Lưu</button>
            <a class="btn btn-ghost" href="${pageContext.request.contextPath}/admin/users">Hủy</a>
        </div>
    </form>
</div>

</body>
</html>