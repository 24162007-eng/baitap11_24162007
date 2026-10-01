package controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Category_24162007;
import service.CategoryService_24162007;
import service.VideoService_24162007;

@WebServlet("/home")
public class HomeServlet_24162007 extends HttpServlet {

    private CategoryService_24162007 categoryService = new CategoryService_24162007();
    private VideoService_24162007 videoService = new VideoService_24162007();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category_24162007> categories = categoryService.getAllCategories();
        req.setAttribute("categories", categories);
        req.setAttribute("videoCounts", videoService.countVideosGroupByCategory());
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
