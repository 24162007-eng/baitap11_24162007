package controller;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.OrderStatus_24162007;
import entity.Orders_24162007;
import entity.Users_24162007;
import service.OrderService_24162007;

@WebServlet("/orders")
public class OrderServlet_24162007 extends HttpServlet {

    private OrderService_24162007 orderService = new OrderService_24162007();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162007 user = (Users_24162007) req.getSession().getAttribute("currentUser");
        String idParam = req.getParameter("id");

        if (idParam == null) {
            OrderStatus_24162007 filter = OrderStatus_24162007.fromCode(req.getParameter("status"));
            Map<String, Integer> counts = orderService.countOrdersByStatus(user.getUsername());
            int totalOrders = 0;
            for (int n : counts.values()) totalOrders += n;

            req.setAttribute("orders", orderService.getOrdersByUsername(user.getUsername(), filter == null ? null : filter.name()));
            req.setAttribute("statuses", OrderStatus_24162007.values());
            req.setAttribute("statusCounts", counts);
            req.setAttribute("totalOrders", totalOrders);
            req.setAttribute("currentStatus", filter == null ? "" : filter.name());
            req.getRequestDispatcher("/orders.jsp").forward(req, resp);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }

        Orders_24162007 order = orderService.getOrderById(id);
        if (order == null || !order.getUsername().equals(user.getUsername())) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }
        req.setAttribute("order", order);
        req.setAttribute("success", "1".equals(req.getParameter("success")));
        req.getRequestDispatcher("/orderDetail.jsp").forward(req, resp);
    }
}