package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Users_24162007;
import service.UserService_24162007;

@WebServlet("/login")
public class LoginServlet_24162007 extends HttpServlet {

    private UserService_24162007 userService = new UserService_24162007();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Users_24162007 user = userService.login(username, password);

        if (user != null) {
            user.setPassword(null);   // không giữ mật khẩu trong session
            user.setOtpCode(null);
            req.getSession().setAttribute("currentUser", user);
            if (user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/users");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu, hoặc tài khoản chưa kích hoạt");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}