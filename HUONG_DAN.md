# Đề 04 – Lập trình Web – MSSV 24162007

## Cách chạy
1. Chạy file `database/schema_mysql.sql` trong MySQL (tạo DB `QuanLyVideoThoiTrang_24162007` + dữ liệu mẫu).
2. Mở `src/main/resources/app.properties`, sửa `db.user`, `db.password` cho đúng MySQL của bạn.
   - Để gửi OTP thật: điền `mail.password` = **App Password** của Gmail.
   - Để trống `mail.password`: OTP được in ra **Console Tomcat** (chỉ để test).
3. Eclipse: chuột phải project → **Maven → Update Project (Alt+F5)** → chạy trên Tomcat 10.1+ (Jakarta EE 10, JDK 21).
4. Tài khoản mẫu: `admin / admin123` (quản trị), `user1 / 123456` (người dùng thường).

## Các lỗi đã sửa so với bản trước
- Câu 3: form thêm/sửa user thiếu `multipart/form-data` -> lỗi 500. Đã thêm form + ô chọn ảnh đại diện.
- Câu 3: xóa user bị lỗi khóa ngoại (Favorites/Shares) -> xóa trong transaction; xóa bằng POST; không cho tự xóa mình.
- Câu 4, 5: ảnh poster dùng sai đường dẫn `/images/` -> `/media/`; poster trống hiện khung placeholder.
- Câu 1: footer decorator admin còn placeholder -> đã ghi họ tên; xóa thư mục `webapp/decorators` cũ không dùng.
- Câu 2: gửi mail lỗi thì hủy tài khoản và báo lỗi (trước đây vẫn báo thành công); cấu hình mail/DB đưa ra `app.properties`.
- Câu 5: không còn lỗi 500 khi thiếu/sai `categoryId`, `page`; menu "Sản phẩm" không còn gắn cứng `categoryId=1`.
- Câu 6: số video theo từng category hiển thị ở thanh category (trang 5) và ở trang chủ, cộng số ở tiêu đề `Tên category (n)`.
- Chống XSS (dùng `<c:out>`), không lưu mật khẩu trong session.
