package controller;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import entity.CartItem_24162007;
import entity.Users_24162007;
import service.CartService_24162007;
import service.OrderService_24162007;

@WebServlet("/checkout")
public class CheckoutServlet_24162007 extends HttpServlet {

    private static final Pattern PHONE = Pattern.compile("^0\\d{9}$");

    private CartService_24162007 cartService = new CartService_24162007();
    private OrderService_24162007 orderService = new OrderService_24162007();

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private void forwardForm(HttpServletRequest req, HttpServletResponse resp, Map<String, CartItem_24162007> cart)
            throws ServletException, IOException {
        req.setAttribute("cartTotal", cartService.getTotal(cart));
        req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        cartService.refresh(session);
        Map<String, CartItem_24162007> cart = cartService.getCart(session);
        if (cart.isEmpty()) {
            session.setAttribute("cartError", "Giỏ hàng đang trống");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        Users_24162007 user = (Users_24162007) session.getAttribute("currentUser");
        req.setAttribute("receiverName", user.getFullname());
        req.setAttribute("phone", user.getPhone());
        forwardForm(req, resp, cart);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        cartService.refresh(session);
        Map<String, CartItem_24162007> cart = cartService.getCart(session);
        if (cart.isEmpty()) {
            session.setAttribute("cartError", "Giỏ hàng đang trống");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        Users_24162007 user = (Users_24162007) session.getAttribute("currentUser");
        String receiverName = trim(req.getParameter("receiverName"));
        String phone = trim(req.getParameter("phone"));
        String address = trim(req.getParameter("address"));
        String note = trim(req.getParameter("note"));

        req.setAttribute("receiverName", receiverName);
        req.setAttribute("phone", phone);
        req.setAttribute("address", address);
        req.setAttribute("note", note);

        String error = null;
        if (receiverName.isEmpty() || receiverName.length() > 50) {
            error = "Họ tên người nhận không được để trống và tối đa 50 ký tự";
        } else if (!PHONE.matcher(phone).matches()) {
            error = "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0";
        } else if (address.isEmpty() || address.length() > 255) {
            error = "Địa chỉ giao hàng không được để trống và tối đa 255 ký tự";
        } else if (note.length() > 255) {
            error = "Ghi chú tối đa 255 ký tự";
        }

        if (error == null) {
            try {
                int orderId = orderService.placeOrder(user, cart, receiverName, phone, address, note);
                if (orderId > 0) {
                    cartService.clear(session);
                    resp.sendRedirect(req.getContextPath() + "/orders?id=" + orderId + "&success=1");
                    return;
                }
                error = "Đặt hàng thất bại, vui lòng thử lại";
            } catch (IllegalStateException e) {
                error = e.getMessage();
                cartService.refresh(session);
                if (cartService.getCart(session).isEmpty()) {
                    session.setAttribute("cartError", error);
                    resp.sendRedirect(req.getContextPath() + "/cart");
                    return;
                }
            }
        }

        req.setAttribute("error", error);
        forwardForm(req, resp, cart);
    }
}