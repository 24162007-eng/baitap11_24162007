package controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Users_24162007;
import service.UserService_24162007;
import util.UploadUtil_24162007;

@WebServlet("/admin/users")
@MultipartConfig(maxFileSize = 5L * 1024 * 1024)
public class UserManagementServlet_24162007 extends HttpServlet {

    private UserService_24162007 userService = new UserService_24162007();
    private static final int PAGE_SIZE = 6;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        if (action.equals("edit")) {
            Users_24162007 u = userService.getUserByUsername(req.getParameter("username"));
            if (u == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                return;
            }
            req.setAttribute("user", u);
            req.getRequestDispatcher("/admin/userForm.jsp").forward(req, resp);
        } else if (action.equals("add")) {
            req.getRequestDispatcher("/admin/userForm.jsp").forward(req, resp);
        } else {
            int page = 1;
            try {
                page = Math.max(1, Integer.parseInt(req.getParameter("page")));
            } catch (NumberFormatException e) {
                page = 1;
            }
            int totalPages = userService.getTotalPages(PAGE_SIZE);
            page = Math.min(page, Math.max(totalPages, 1));
            List<Users_24162007> list = userService.getUsersPaginated(page, PAGE_SIZE);
            req.setAttribute("users", list);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.getRequestDispatcher("/admin/userList.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String ctx = req.getContextPath();

        // Xóa: dùng POST (không xóa bằng link GET)
        if ("delete".equals(action)) {
            String username = req.getParameter("username");
            Users_24162007 me = (Users_24162007) req.getSession().getAttribute("currentUser");
            if (me != null && me.getUsername().equals(username)) {
                resp.sendRedirect(ctx + "/admin/users?msg=selfdelete"); // không cho admin tự xóa mình
                return;
            }
            userService.deleteUser(username);
            resp.sendRedirect(ctx + "/admin/users");
            return;
        }

        Users_24162007 u = new Users_24162007();
        u.setUsername(req.getParameter("username"));
        u.setPassword(req.getParameter("password"));
        u.setPhone(req.getParameter("phone"));
        u.setFullname(req.getParameter("fullname"));
        u.setEmail(req.getParameter("email"));
        u.setAdmin(req.getParameter("admin") != null);
        u.setActive(req.getParameter("active") != null);

        // Ảnh đại diện (không bắt buộc). Form phải là multipart/form-data.
        try {
            u.setImages(UploadUtil_24162007.save(req.getPart("avatar"), "image"));
        } catch (IOException | ServletException | IllegalStateException e) {
            e.printStackTrace();
        }

        if ("create".equals(action)) {
            if (!userService.createUserByAdmin(u)) {
                resp.sendRedirect(ctx + "/admin/users?action=add&error=exists");
                return;
            }
        } else if ("update".equals(action)) {
            userService.updateUser(u);
            // Đang sửa chính mình -> cập nhật lại session
            Users_24162007 me = (Users_24162007) req.getSession().getAttribute("currentUser");
            if (me != null && me.getUsername().equals(u.getUsername())) {
                req.getSession().setAttribute("currentUser", userService.getUserByUsername(u.getUsername()));
            }
        }
        resp.sendRedirect(ctx + "/admin/users");
    }
}
