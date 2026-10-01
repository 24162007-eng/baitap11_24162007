package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entity.CartItem_24162007;
import entity.OrderDetails_24162007;
import entity.Orders_24162007;
import util.DBConnection_24162007;

public class OrderDAO_24162007 {

    public int createOrder(Orders_24162007 o, Map<String, CartItem_24162007> cart) {
        try (Connection conn = DBConnection_24162007.getConnection()) {
            conn.setAutoCommit(false);
            try {
                List<OrderDetails_24162007> details = new ArrayList<>();
                long total = 0;
                String lockSql = "SELECT Title, Price, Stock, Active FROM Videos WHERE VideoId=? FOR UPDATE";
                String stockSql = "UPDATE Videos SET Stock = Stock - ? WHERE VideoId=?";
                try (PreparedStatement lock = conn.prepareStatement(lockSql);
                     PreparedStatement upd = conn.prepareStatement(stockSql)) {
                    for (CartItem_24162007 item : cart.values()) {
                        lock.setString(1, item.getVideoId());
                        try (ResultSet rs = lock.executeQuery()) {
                            if (!rs.next() || !rs.getBoolean("Active")) {
                                throw new IllegalStateException("Sản phẩm \"" + item.getTitle() + "\" không còn được bán");
                            }
                            int stock = rs.getInt("Stock");
                            if (item.getQuantity() > stock) {
                                throw new IllegalStateException("Sản phẩm \"" + item.getTitle() + "\" chỉ còn " + stock + " trong kho");
                            }
                            OrderDetails_24162007 d = new OrderDetails_24162007();
                            d.setVideoId(item.getVideoId());
                            d.setTitle(rs.getString("Title"));
                            d.setPrice(rs.getLong("Price"));
                            d.setQuantity(item.getQuantity());
                            details.add(d);
                            total += d.getTotal();
                        }
                        upd.setInt(1, item.getQuantity());
                        upd.setString(2, item.getVideoId());
                        upd.executeUpdate();
                    }
                }

                int orderId;
                String orderSql = "INSERT INTO Orders (Username, ReceiverName, Phone, Address, Note, TotalAmount, PaymentMethod, Status, CreatedDate) " +
                        "VALUES (?,?,?,?,?,?,?,?,NOW())";
                try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, o.getUsername());
                    ps.setString(2, o.getReceiverName());
                    ps.setString(3, o.getPhone());
                    ps.setString(4, o.getAddress());
                    ps.setString(5, o.getNote());
                    ps.setLong(6, total);
                    ps.setString(7, o.getPaymentMethod());
                    ps.setString(8, o.getStatus());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) throw new SQLException("Không lấy được mã đơn hàng");
                        orderId = keys.getInt(1);
                    }
                }

                String detailSql = "INSERT INTO OrderDetails (OrderId, VideoId, Title, Price, Quantity) VALUES (?,?,?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(detailSql)) {
                    for (OrderDetails_24162007 d : details) {
                        ps.setInt(1, orderId);
                        ps.setString(2, d.getVideoId());
                        ps.setString(3, d.getTitle());
                        ps.setLong(4, d.getPrice());
                        ps.setInt(5, d.getQuantity());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                conn.commit();
                return orderId;
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Orders_24162007 mapOrder(ResultSet rs) throws SQLException {
        Orders_24162007 o = new Orders_24162007();
        o.setOrderId(rs.getInt("OrderId"));
        o.setUsername(rs.getString("Username"));
        o.setReceiverName(rs.getString("ReceiverName"));
        o.setPhone(rs.getString("Phone"));
        o.setAddress(rs.getString("Address"));
        o.setNote(rs.getString("Note"));
        o.setTotalAmount(rs.getLong("TotalAmount"));
        o.setPaymentMethod(rs.getString("PaymentMethod"));
        o.setStatus(rs.getString("Status"));
        o.setCreatedDate(rs.getTimestamp("CreatedDate"));
        return o;
    }

    public List<Orders_24162007> getOrdersByUsername(String username, String status) {
        List<Orders_24162007> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE Username=?" +
                (status == null ? "" : " AND Status=?") +
                " ORDER BY OrderId DESC";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            if (status != null) ps.setString(2, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapOrder(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, Integer> countOrdersByStatus(String username) {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT Status, COUNT(*) AS total FROM Orders WHERE Username=? GROUP BY Status";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String st = rs.getString("Status");
                if (st != null) map.merge(st.trim().toUpperCase(), rs.getInt("total"), Integer::sum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Orders_24162007 getOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE OrderId=?";
        String detailSql = "SELECT * FROM OrderDetails WHERE OrderId=? ORDER BY DetailId";
        try (Connection conn = DBConnection_24162007.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             PreparedStatement pd = conn.prepareStatement(detailSql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            Orders_24162007 o = mapOrder(rs);
            pd.setInt(1, orderId);
            ResultSet rd = pd.executeQuery();
            while (rd.next()) {
                OrderDetails_24162007 d = new OrderDetails_24162007();
                d.setDetailId(rd.getInt("DetailId"));
                d.setOrderId(rd.getInt("OrderId"));
                d.setVideoId(rd.getString("VideoId"));
                d.setTitle(rd.getString("Title"));
                d.setPrice(rd.getLong("Price"));
                d.setQuantity(rd.getInt("Quantity"));
                o.getDetails().add(d);
            }
            return o;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}