package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import entity.Users_24162007;
import util.DBConnection_24162007;

public class UserDAO_24162007 {

    private Users_24162007 mapRow(ResultSet rs) throws SQLException {
        Users_24162007 u = new Users_24162007();
        u.setUsername(rs.getString("Username"));
        u.setPassword(rs.getString("Password"));
        u.setPhone(rs.getString("Phone"));
        u.setFullname(rs.getString("Fullname"));
        u.setEmail(rs.getString("Email"));
        u.setAdmin(rs.getBoolean("Admin"));
        u.setActive(rs.getBoolean("Active"));
        u.setImages(rs.getString("Images"));
        u.setOtpCode(rs.getString("OtpCode"));
        u.setOtpExpire(rs.getTimestamp("OtpExpire"));
        return u;
    }

    public Users_24162007 checkLogin(String username, String password) {
        String sql = "SELECT * FROM Users WHERE Username=? AND Password=? AND Active=1";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkUsernameExists(String username) {
        String sql = "SELECT Username FROM Users WHERE Username=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertUser(Users_24162007 u) {
        String sql = "INSERT INTO Users (Username, Password, Phone, Fullname, Email, Admin, Active, Images, OtpCode, OtpExpire) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getPhone());
            ps.setString(4, u.getFullname());
            ps.setString(5, u.getEmail());
            ps.setBoolean(6, u.isAdmin());
            ps.setBoolean(7, u.isActive());
            ps.setString(8, u.getImages());
            ps.setString(9, u.getOtpCode());
            ps.setTimestamp(10, u.getOtpExpire());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyOtp(String username, String otp) {
        String sql = "SELECT OtpCode, OtpExpire FROM Users WHERE Username=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String dbOtp = rs.getString("OtpCode");
                Timestamp expire = rs.getTimestamp("OtpExpire");
                if (dbOtp != null && dbOtp.equals(otp) && expire != null && expire.after(new Timestamp(System.currentTimeMillis()))) {
                    return activateUser(username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean activateUser(String username) {
        String sql = "UPDATE Users SET Active=1, OtpCode=NULL, OtpExpire=NULL WHERE Username=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Users_24162007> getUsersPaginated(int page, int pageSize) {
        List<Users_24162007> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY Username LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countUsers() {
        String sql = "SELECT COUNT(*) AS total FROM Users";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Users_24162007 getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE Username=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(Users_24162007 u) {
        String sql = "UPDATE Users SET Password=?, Phone=?, Fullname=?, Email=?, Admin=?, Active=?, Images=COALESCE(?, Images) WHERE Username=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getPassword());
            ps.setString(2, u.getPhone());
            ps.setString(3, u.getFullname());
            ps.setString(4, u.getEmail());
            ps.setBoolean(5, u.isAdmin());
            ps.setBoolean(6, u.isActive());
            ps.setString(7, u.getImages());
            ps.setString(8, u.getUsername());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa Favorites và Shares của user trước (khóa ngoại), rồi mới xóa Users
    public boolean deleteUser(String username) {
        try (Connection conn = DBConnection_24162007.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement p1 = conn.prepareStatement("DELETE FROM Favorites WHERE Username=?");
                 PreparedStatement p2 = conn.prepareStatement("DELETE FROM Shares WHERE Username=?");
                 PreparedStatement p3 = conn.prepareStatement("DELETE FROM Users WHERE Username=?")) {
                p1.setString(1, username);
                p1.executeUpdate();
                p2.setString(1, username);
                p2.executeUpdate();
                p3.setString(1, username);
                int rows = p3.executeUpdate();
                conn.commit();
                return rows > 0;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
