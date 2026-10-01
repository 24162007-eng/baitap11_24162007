package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBConnection_24162007;

public class FavoriteDAO_24162007 {

    public int countByVideoId(String videoId) {
        String sql = "SELECT COUNT(*) AS total FROM Favorites WHERE VideoId=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}