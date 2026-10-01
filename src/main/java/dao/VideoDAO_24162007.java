package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import entity.Videos_24162007;
import util.DBConnection_24162007;

public class VideoDAO_24162007 {

    private Videos_24162007 mapRow(ResultSet rs) throws SQLException {
        Videos_24162007 v = new Videos_24162007();
        v.setVideoId(rs.getString("VideoId"));
        v.setTitle(rs.getString("Title"));
        v.setPoster(rs.getString("Poster"));
        v.setViews(rs.getInt("Views"));
        v.setDescription(rs.getString("Description"));
        v.setActive(rs.getBoolean("Active"));
        v.setCategoryId(rs.getInt("CategoryId"));
        v.setVideoFile(rs.getString("VideoFile"));
        v.setPrice(rs.getLong("Price"));
        v.setStock(rs.getInt("Stock"));
        return v;
    }

    public Videos_24162007 getVideoById(String videoId) {
        String sql = "SELECT v.*, c.Categoryname, " +
                "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId=v.VideoId) AS shareCount, " +
                "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId=v.VideoId) AS favoriteCount " +
                "FROM Videos v LEFT JOIN Category c ON v.CategoryId=c.CategoryId WHERE v.VideoId=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Videos_24162007 v = mapRow(rs);
                v.setCategoryname(rs.getString("Categoryname"));
                v.setShareCount(rs.getInt("shareCount"));
                v.setFavoriteCount(rs.getInt("favoriteCount"));
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Videos_24162007> getVideosByCategory(int categoryId, int page, int pageSize) {
        List<Videos_24162007> list = new ArrayList<>();
        String sql = "SELECT v.*, " +
                "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId=v.VideoId) AS shareCount, " +
                "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId=v.VideoId) AS favoriteCount " +
                "FROM Videos v WHERE v.CategoryId=? AND v.Active=1 ORDER BY v.VideoId LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, pageSize);
            ps.setInt(3, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Videos_24162007 v = mapRow(rs);
                v.setShareCount(rs.getInt("shareCount"));
                v.setFavoriteCount(rs.getInt("favoriteCount"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countVideosByCategory(int categoryId) {
        String sql = "SELECT COUNT(*) AS total FROM Videos WHERE CategoryId=? AND Active=1";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<Integer, Integer> countVideosGroupByCategory() {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT CategoryId, COUNT(*) AS total FROM Videos WHERE Active=1 GROUP BY CategoryId";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) map.put(rs.getInt("CategoryId"), rs.getInt("total"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void increaseView(String videoId) {
        String sql = "UPDATE Videos SET Views = Views + 1 WHERE VideoId=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Videos_24162007> getAllVideos() {
        List<Videos_24162007> list = new ArrayList<>();
        String sql = "SELECT v.*, c.Categoryname FROM Videos v " +
                "LEFT JOIN Category c ON v.CategoryId=c.CategoryId ORDER BY v.VideoId";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Videos_24162007 v = mapRow(rs);
                v.setCategoryname(rs.getString("Categoryname"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertVideo(Videos_24162007 v) {
        String sql = "INSERT INTO Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId, VideoFile, Price, Stock) " +
                "VALUES (?,?,?,0,?,1,?,?,?,?)";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getVideoId());
            ps.setString(2, v.getTitle());
            ps.setString(3, v.getPoster());
            ps.setString(4, v.getDescription());
            ps.setInt(5, v.getCategoryId());
            ps.setString(6, v.getVideoFile());
            ps.setLong(7, v.getPrice());
            ps.setInt(8, v.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateVideo(Videos_24162007 v) {
        String sql = "UPDATE Videos SET Title=?, Description=?, CategoryId=?, Active=?, Price=?, Stock=?, " +
                "Poster=COALESCE(?, Poster), VideoFile=COALESCE(?, VideoFile) WHERE VideoId=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getTitle());
            ps.setString(2, v.getDescription());
            ps.setInt(3, v.getCategoryId());
            ps.setBoolean(4, v.isActive());
            ps.setLong(5, v.getPrice());
            ps.setInt(6, v.getStock());
            ps.setString(7, v.getPoster());
            ps.setString(8, v.getVideoFile());
            ps.setString(9, v.getVideoId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteVideo(String videoId) {
        try (Connection conn = DBConnection_24162007.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement p1 = conn.prepareStatement("DELETE FROM Favorites WHERE VideoId=?");
                 PreparedStatement p2 = conn.prepareStatement("DELETE FROM Shares WHERE VideoId=?");
                 PreparedStatement p3 = conn.prepareStatement("DELETE FROM Videos WHERE VideoId=?")) {
                p1.setString(1, videoId);
                p1.executeUpdate();
                p2.setString(1, videoId);
                p2.executeUpdate();
                p3.setString(1, videoId);
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