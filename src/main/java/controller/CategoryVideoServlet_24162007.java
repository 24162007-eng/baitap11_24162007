package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Category_24162007;
import entity.Videos_24162007;
import service.CategoryService_24162007;
import service.VideoService_24162007;

@WebServlet("/category")
public class CategoryVideoServlet_24162007 extends HttpServlet {

    private CategoryService_24162007 categoryService = new CategoryService_24162007();
    private VideoService_24162007 videoService = new VideoService_24162007();
    private static final int PAGE_SIZE = 3;

    private int parseInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category_24162007> allCategories = categoryService.getAllCategories();
        if (allCategories.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // Không truyền categoryId (menu "Sản phẩm") -> mặc định category đầu tiên
        int categoryId = parseInt(req.getParameter("categoryId"), allCategories.get(0).getCategoryId());
        Category_24162007 category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        int totalVideos = videoService.countVideosByCategory(categoryId);
        int totalPages = videoService.getTotalPages(categoryId, PAGE_SIZE);
        int page = parseInt(req.getParameter("page"), 1);
        page = Math.max(1, Math.min(page, Math.max(totalPages, 1)));

        List<Videos_24162007> videos = videoService.getVideosByCategory(categoryId, page, PAGE_SIZE);
        Map<Integer, Integer> videoCounts = videoService.countVideosGroupByCategory();

        req.setAttribute("category", category);
        req.setAttribute("videos", videos);
        req.setAttribute("totalVideos", totalVideos);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("allCategories", allCategories);
        req.setAttribute("videoCounts", videoCounts);
        req.getRequestDispatcher("/categoryVideos.jsp").forward(req, resp);
    }
}
