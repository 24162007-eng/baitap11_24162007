package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection_24162007 {

    /** Trả về Connection; ném SQLException nếu lỗi (DAO sẽ bắt và xử lý, không bị NullPointerException). */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy MySQL JDBC Driver", e);
        }
        return DriverManager.getConnection(
                AppConfig_24162007.get("db.url", "jdbc:mysql://localhost:3306/QuanLyVideoThoiTrang_24162007?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8"),
                AppConfig_24162007.get("db.user", "root"),
                AppConfig_24162007.get("db.password", ""));
    }
}
