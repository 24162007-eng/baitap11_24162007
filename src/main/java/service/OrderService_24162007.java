package service;

import java.util.List;
import java.util.Map;
import dao.OrderDAO_24162007;
import entity.CartItem_24162007;
import entity.Orders_24162007;
import entity.Users_24162007;

public class OrderService_24162007 {

    private OrderDAO_24162007 orderDAO = new OrderDAO_24162007();

    public int placeOrder(Users_24162007 user, Map<String, CartItem_24162007> cart,
                          String receiverName, String phone, String address, String note) {
        Orders_24162007 o = new Orders_24162007();
        o.setUsername(user.getUsername());
        o.setReceiverName(receiverName);
        o.setPhone(phone);
        o.setAddress(address);
        o.setNote(note);
        o.setPaymentMethod("COD");
        o.setStatus("Chờ xác nhận");
        return orderDAO.createOrder(o, cart);
    }

    public List<Orders_24162007> getOrdersByUsername(String username) {
        return orderDAO.getOrdersByUsername(username);
    }

    public Orders_24162007 getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }
}