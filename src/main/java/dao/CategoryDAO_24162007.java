package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Category_24162007;
import util.DBConnection_24162007;

public class CategoryDAO_24162007 {

    private Category_24162007 mapRow(ResultSet rs) throws SQLException {
        Category_24162007 c = new Category_24162007();
        c.setCategoryId(rs.getInt("CategoryId"));
        c.setCategoryname(rs.getString("Categoryname"));
        c.setCategorycode(rs.getString("Categorycode"));
        c.setImages(rs.getString("Images"));
        c.setStatus(rs.getBoolean("Status"));
        return c;
    }

    public List<Category_24162007> getAllCategories() {
        List<Category_24162007> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE Status=1 ORDER BY CategoryId";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Category_24162007 getCategoryById(int id) {
        String sql = "SELECT * FROM Category WHERE CategoryId=?";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}