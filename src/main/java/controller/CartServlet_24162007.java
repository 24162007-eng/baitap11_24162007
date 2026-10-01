package controller;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import entity.CartItem_24162007;
import service.CartService_24162007;

@WebServlet("/cart")
public class CartServlet_24162007 extends HttpServlet {

    private CartService_24162007 cartService = new CartService_24162007();

    private Integer parseQty(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        cartService.refresh(session);
        Map<String, CartItem_24162007> cart = cartService.getCart(session);

        for (String key : new String[] {"cartMsg", "cartError"}) {
            Object o = session.getAttribute(key);
            if (o != null) {
                req.setAttribute(key, o);
                session.removeAttribute(key);
            }
        }

        req.setAttribute("cartTotal", cartService.getTotal(cart));
        req.setAttribute("maxPerItem", CartService_24162007.MAX_PER_ITEM);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String action = req.getParameter("action");
        String videoId = req.getParameter("videoId");
        Integer qty = parseQty(req.getParameter("quantity"));
        String ok = null;
        String error = null;

        if ("add".equals(action)) {
            if (qty == null) {
                error = "Số lượng không hợp lệ";
            } else {
                error = cartService.add(session, videoId, qty);
                if (error == null) ok = "Đã thêm sản phẩm vào giỏ hàng";
            }
        } else if ("update".equals(action)) {
            if (qty == null) {
                error = "Số lượng không hợp lệ";
            } else {
                error = cartService.update(session, videoId, qty);
                if (error == null) ok = "Đã cập nhật giỏ hàng";
            }
        } else if ("remove".equals(action)) {
            cartService.remove(session, videoId);
            ok = "Đã xóa sản phẩm khỏi giỏ hàng";
        } else if ("clear".equals(action)) {
            cartService.clear(session);
            ok = "Đã xóa toàn bộ giỏ hàng";
        } else {
            error = "Hành động không hợp lệ";
        }

        session.setAttribute("cartMsg", ok);
        session.setAttribute("cartError", error);
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}