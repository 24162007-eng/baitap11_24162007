package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Users_24162007;
import service.UserService_24162007;

@WebServlet("/register")
public class RegisterServlet_24162007 extends HttpServlet {

    private UserService_24162007 userService = new UserService_24162007();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users_24162007 u = new Users_24162007();
        u.setUsername(req.getParameter("username"));
        u.setPassword(req.getParameter("password"));
        u.setPhone(req.getParameter("phone"));
        u.setFullname(req.getParameter("fullname"));
        u.setEmail(req.getParameter("email"));

        String result = userService.register(u);
        if (result.equals("OK")) {
            req.setAttribute("username", u.getUsername());
            req.getRequestDispatcher("/verifyOtp.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", result);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}