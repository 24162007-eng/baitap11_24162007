<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html><head><title>Sửa video</title></head><body>

<div class="page-title">
    <div>
        <h2>Cập nhật video</h2>
        <div class="subtitle">Mã video: ${video.videoId}</div>
    </div>
</div>

<div class="form-card">
    <form action="${pageContext.request.contextPath}/admin/videos" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="videoId" value="${video.videoId}" />

        <div class="form-grid">
            <div class="full">
                <label class="field-label">Tiêu đề</label>
                <input type="text" name="title" value="<c:out value='${video.title}'/>" required />
            </div>
            <div class="full">
                <label class="field-label">Mô tả</label>
                <input type="text" name="description" value="<c:out value='${video.description}'/>" />
            </div>
            <div>
                <label class="field-label">Category</label>
                <select name="categoryId" required>
                    <c:forEach var="cat" items="${categories}">
                        <option value="${cat.categoryId}" ${cat.categoryId == video.categoryId ? 'selected' : ''}><c:out value="${cat.categoryname}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label class="field-label">Ảnh poster mới (bỏ trống để giữ ảnh cũ)</label>
                <c:if test="${not empty video.poster}">
                    <img src="${pageContext.request.contextPath}/media/${video.poster}"
                         style="height:64px;border-radius:8px;display:block;margin-bottom:8px;"
                         onerror="this.style.display='none'" />
                </c:if>
                <input type="file" name="poster" accept="image/*" />
            </div>
            <div>
                <label class="field-label">Giá (đ)</label>
                <input type="number" name="price" min="0" value="${video.price}" required />
            </div>
            <div>
                <label class="field-label">Tồn kho</label>
                <input type="number" name="stock" min="0" value="${video.stock}" required />
            </div>
            <div class="full">
                <label class="field-label">File video mới (bỏ trống để giữ video cũ)</label>
                <c:if test="${not empty video.videoFile}">
                    <p style="margin:0 0 8px;">Đang dùng: ${video.videoFile}</p>
                </c:if>
                <input type="file" name="videoFile" accept="video/mp4,video/webm,video/ogg" />
            </div>
        </div>

        <div class="checkbox-row">
            <input type="checkbox" id="active" name="active" ${video.active ? 'checked' : ''} />
            <label for="active">Hiển thị video (Active)</label>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-admin">Lưu</button>
            <a class="btn btn-ghost" href="${pageContext.request.contextPath}/admin/videos">Hủy</a>
        </div>
    </form>
</div>

</body></html>