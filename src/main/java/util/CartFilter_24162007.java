package util;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import entity.Users_24162007;

@WebFilter({"/cart", "/checkout", "/orders"})
public class CartFilter_24162007 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        Users_24162007 u = (session == null) ? null : (Users_24162007) session.getAttribute("currentUser");

        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        if (u.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        chain.doFilter(request, response);
    }
}