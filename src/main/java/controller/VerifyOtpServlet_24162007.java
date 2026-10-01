package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService_24162007;

@WebServlet("/verifyOtp")
public class VerifyOtpServlet_24162007 extends HttpServlet {

    private UserService_24162007 userService = new UserService_24162007();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String otp = req.getParameter("otp");
        boolean ok = userService.verifyOtp(username, otp);
        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            req.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/verifyOtp.jsp").forward(req, resp);
        }
    }
}