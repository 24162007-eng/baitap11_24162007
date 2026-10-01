<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html><head><title>Quản lý Video</title></head><body>

<div class="page-title">
    <div>
        <h2>Quản lý Video</h2>
        <div class="subtitle">Thêm, sửa, xóa video</div>
    </div>
</div>

<c:if test="${not empty error}"><p class="form-error"><c:out value="${error}"/></p></c:if>

<div class="form-card">
    <form action="${pageContext.request.contextPath}/admin/videos" method="post" enctype="multipart/form-data">
        <div class="form-grid">
            <div class="full">
                <label class="field-label">Tiêu đề</label>
                <input type="text" name="title" required />
            </div>
            <div class="full">
                <label class="field-label">Mô tả</label>
                <input type="text" name="description" />
            </div>
            <div>
                <label class="field-label">Category</label>
                <select name="categoryId" required>
                    <c:forEach var="cat" items="${categories}">
                        <option value="${cat.categoryId}"><c:out value="${cat.categoryname}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label class="field-label">Ảnh poster (jpg, png, gif, webp)</label>
                <input type="file" name="poster" accept="image/*" />
            </div>
            <div>
                <label class="field-label">Giá (đ)</label>
                <input type="number" name="price" min="0" value="0" required />
            </div>
            <div>
                <label class="field-label">Tồn kho</label>
                <input type="number" name="stock" min="0" value="0" required />
            </div>
            <div class="full">
                <label class="field-label">File video (mp4, webm, ogg – tối đa 200MB)</label>
                <input type="file" name="videoFile" accept="video/mp4,video/webm,video/ogg" />
            </div>
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-admin">Upload</button>
        </div>
    </form>
</div>

<div class="table-wrap" style="margin-top:24px;">
    <table class="data-table">
        <tr>
            <th>Poster</th><th>Mã</th><th>Tiêu đề</th><th>Category</th>
            <th>Giá</th><th>Tồn kho</th><th>Video</th><th>Trạng thái</th><th>Hành động</th>
        </tr>
        <c:forEach var="v" items="${videos}">
            <tr>
                <td>
                    <c:if test="${not empty v.poster}">
                        <img src="${pageContext.request.contextPath}/media/${v.poster}"
                             style="height:48px;border-radius:6px;"
                             onerror="this.style.display='none'" />
                    </c:if>
                </td>
                <td><c:out value="${v.videoId}"/></td>
                <td><c:out value="${v.title}"/></td>
                <td><c:out value="${v.categoryname}"/></td>
                <td><fmt:formatNumber value="${v.price}" pattern="#,##0"/>đ</td>
                <td>${v.stock}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty v.videoFile}">
                            <a href="${pageContext.request.contextPath}/media/${v.videoFile}" target="_blank">Xem</a>
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${v.active}"><span class="badge badge-success">Hiện</span></c:when>
                        <c:otherwise><span class="badge badge-muted">Ẩn</span></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <div class="actions-cell">
                        <a class="btn btn-ghost btn-sm"
                           href="${pageContext.request.contextPath}/admin/videos?action=edit&videoId=${v.videoId}">Sửa</a>
                        <form action="${pageContext.request.contextPath}/admin/videos" method="post" style="display:inline;"
                              onsubmit="return confirm('Xác nhận xóa video này?')">
                            <input type="hidden" name="action" value="delete" />
                            <input type="hidden" name="videoId" value="${v.videoId}" />
                            <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body></html>