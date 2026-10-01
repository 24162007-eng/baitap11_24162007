<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Quản lý người dùng</title></head>
<body>

<div class="page-title">
    <div>
        <h2>Danh sách người dùng</h2>
        <div class="subtitle">Quản lý tài khoản người dùng trong hệ thống</div>
    </div>
    <a class="btn btn-admin" href="${pageContext.request.contextPath}/admin/users?action=add">+ Thêm mới</a>
</div>

<c:if test="${param.msg == 'selfdelete'}"><p class="form-error">Không thể tự xóa tài khoản đang đăng nhập.</p></c:if>

<c:choose>
    <c:when test="${empty users}">
        <div class="empty-state">
            <div class="empty-icon">&#128100;</div>
            Chưa có người dùng nào.
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-wrap">
            <table class="data-table">
                <tr>
                    <th>Username</th>
                    <th>Họ tên</th>
                    <th>Email</th>
                    <th>SĐT</th>
                    <th>Admin</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td><c:out value="${u.username}"/></td>
                        <td><c:out value="${u.fullname}"/></td>
                        <td><c:out value="${u.email}"/></td>
                        <td><c:out value="${u.phone}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${u.admin}"><span class="badge badge-admin">Có</span></c:when>
                                <c:otherwise><span class="badge badge-muted">Không</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${u.active}"><span class="badge badge-success">Đã kích hoạt</span></c:when>
                                <c:otherwise><span class="badge badge-muted">Chưa kích hoạt</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <div class="actions-cell">
                                <a class="btn btn-ghost btn-sm" href="${pageContext.request.contextPath}/admin/users?action=edit&username=<c:out value='${u.username}'/>">Sửa</a>
                                <form action="${pageContext.request.contextPath}/admin/users" method="post" style="display:inline;"
                                      onsubmit="return confirm('Xác nhận xóa user này?')">
                                    <input type="hidden" name="action" value="delete" />
                                    <input type="hidden" name="username" value="<c:out value='${u.username}'/>" />
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<c:if test="${totalPages > 1}">
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}">&laquo;</a>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span>${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/admin/users?page=${i}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}">&raquo;</a>
        </c:if>
    </div>
</c:if>

</body>
</html>