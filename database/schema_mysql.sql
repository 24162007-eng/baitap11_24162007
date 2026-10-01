-- =====================================================================
-- Script tạo CSDL + dữ liệu mẫu cho Đề 04 (MySQL 8)
-- Chạy toàn bộ file này trong MySQL Workbench / mysql CLI.
-- Tài khoản mẫu:  admin / admin123  (quản trị)   |   user1..user7 / 123456
-- Ghi chú: so với đề có thêm 3 cột: Users.OtpCode, Users.OtpExpire (OTP đăng ký)
--          và Videos.VideoFile (file video upload).
-- =====================================================================
CREATE DATABASE IF NOT EXISTS QuanLyVideoThoiTrang_24162007
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE QuanLyVideoThoiTrang_24162007;

CREATE TABLE IF NOT EXISTS Users (
    Username   VARCHAR(50)  NOT NULL PRIMARY KEY,
    Password   VARCHAR(50),
    Phone      VARCHAR(15),
    Fullname   VARCHAR(50),
    Email      VARCHAR(150),
    Admin      BIT DEFAULT 0,
    Active     BIT DEFAULT 0,
    Images     VARCHAR(500),
    OtpCode    VARCHAR(10),
    OtpExpire  DATETIME
);

CREATE TABLE IF NOT EXISTS Category (
    CategoryId   INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Categoryname VARCHAR(100),
    Categorycode VARCHAR(100),
    Images       VARCHAR(500),
    Status       BIT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS Videos (
    VideoId     VARCHAR(50) NOT NULL PRIMARY KEY,
    Title       VARCHAR(200),
    Poster      VARCHAR(50),
    Views       INT DEFAULT 0,
    Description VARCHAR(500),
    Active      BIT DEFAULT 1,
    CategoryId  INT,
    VideoFile   VARCHAR(200),
    CONSTRAINT fk_videos_category FOREIGN KEY (CategoryId) REFERENCES Category(CategoryId)
);

CREATE TABLE IF NOT EXISTS Shares (
    ShareId    INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Emails     VARCHAR(50),
    SharedDate DATE,
    Username   VARCHAR(50),
    VideoId    VARCHAR(50),
    CONSTRAINT fk_shares_user  FOREIGN KEY (Username) REFERENCES Users(Username),
    CONSTRAINT fk_shares_video FOREIGN KEY (VideoId)  REFERENCES Videos(VideoId)
);

CREATE TABLE IF NOT EXISTS Favorites (
    FavoriteId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    LikedDate  DATE,
    VideoId    VARCHAR(50),
    Username   VARCHAR(50),
    CONSTRAINT fk_fav_user  FOREIGN KEY (Username) REFERENCES Users(Username),
    CONSTRAINT fk_fav_video FOREIGN KEY (VideoId)  REFERENCES Videos(VideoId)
);

-- Nếu DB cũ của bạn đã có bảng nhưng thiếu cột, chạy thêm (bỏ qua lỗi "Duplicate column"):
-- ALTER TABLE Users  ADD COLUMN OtpCode VARCHAR(10);
-- ALTER TABLE Users  ADD COLUMN OtpExpire DATETIME;
-- ALTER TABLE Videos ADD COLUMN VideoFile VARCHAR(200);

-- ---------------------- Dữ liệu mẫu ----------------------
INSERT IGNORE INTO Users (Username, Password, Phone, Fullname, Email, Admin, Active) VALUES
 ('admin','admin123','0900000000','Quản trị viên','admin@example.com',1,1),
 ('user1','123456','0900000001','Nguyễn Văn A','user1@example.com',0,1),
 ('user2','123456','0900000002','Trần Thị B','user2@example.com',0,1),
 ('user3','123456','0900000003','Lê Văn C','user3@example.com',0,1),
 ('user4','123456','0900000004','Phạm Thị D','user4@example.com',0,1),
 ('user5','123456','0900000005','Hoàng Văn E','user5@example.com',0,1),
 ('user6','123456','0900000006','Võ Thị F','user6@example.com',0,0),
 ('user7','123456','0900000007','Đặng Văn G','user7@example.com',0,1);

INSERT INTO Category (Categoryname, Categorycode, Status)
SELECT * FROM (
  SELECT 'Áo thời trang' AS n, 'AO' AS c, 1 AS s UNION ALL
  SELECT 'Quần thời trang', 'QUAN', 1 UNION ALL
  SELECT 'Giày dép', 'GIAY', 1 UNION ALL
  SELECT 'Phụ kiện', 'PK', 1
) t WHERE NOT EXISTS (SELECT 1 FROM Category);

-- Category 1 có 8 video (để thấy phân trang 3 video/trang), các category còn lại ít hơn
INSERT IGNORE INTO Videos (VideoId, Title, Views, Description, Active, CategoryId) VALUES
 ('VD001','Áo sơ mi mùa hè',120,'Cách phối áo sơ mi cho mùa hè',1,1),
 ('VD002','Áo thun basic',85,'Áo thun basic dễ phối đồ',1,1),
 ('VD003','Áo khoác bomber',60,'Bomber jacket cá tính',1,1),
 ('VD004','Áo hoodie oversize',200,'Hoodie oversize phong cách Hàn',1,1),
 ('VD005','Áo len cổ lọ',33,'Áo len cho ngày se lạnh',1,1),
 ('VD006','Áo polo công sở',48,'Polo lịch sự đi làm',1,1),
 ('VD007','Áo blazer nữ',77,'Blazer thanh lịch',1,1),
 ('VD008','Áo croptop',91,'Croptop năng động',1,1),
 ('VD009','Quần jean ống rộng',150,'Jean ống rộng hot trend',1,2),
 ('VD010','Quần short kaki',40,'Short kaki mát mẻ',1,2),
 ('VD011','Quần tây công sở',55,'Quần tây form đẹp',1,2),
 ('VD012','Quần jogger',72,'Jogger thể thao',1,2),
 ('VD013','Giày sneaker trắng',300,'Sneaker trắng dễ phối',1,3),
 ('VD014','Giày da nam',66,'Giày da lịch lãm',1,3),
 ('VD015','Kính mát thời trang',25,'Chọn kính theo khuôn mặt',1,4);

INSERT INTO Shares (Emails, SharedDate, Username, VideoId)
SELECT * FROM (
  SELECT 'a@gmail.com' AS e, CURDATE() AS d, 'user1' AS u, 'VD001' AS v UNION ALL
  SELECT 'b@gmail.com', CURDATE(), 'user2', 'VD001' UNION ALL
  SELECT 'c@gmail.com', CURDATE(), 'user3', 'VD004' UNION ALL
  SELECT 'd@gmail.com', CURDATE(), 'user1', 'VD009' UNION ALL
  SELECT 'e@gmail.com', CURDATE(), 'user4', 'VD013'
) t WHERE NOT EXISTS (SELECT 1 FROM Shares);

INSERT INTO Favorites (LikedDate, VideoId, Username)
SELECT * FROM (
  SELECT CURDATE() AS d, 'VD001' AS v, 'user1' AS u UNION ALL
  SELECT CURDATE(), 'VD001', 'user2' UNION ALL
  SELECT CURDATE(), 'VD001', 'user3' UNION ALL
  SELECT CURDATE(), 'VD004', 'user1' UNION ALL
  SELECT CURDATE(), 'VD009', 'user5' UNION ALL
  SELECT CURDATE(), 'VD013', 'user2' UNION ALL
  SELECT CURDATE(), 'VD013', 'user3'
) t WHERE NOT EXISTS (SELECT 1 FROM Favorites);
