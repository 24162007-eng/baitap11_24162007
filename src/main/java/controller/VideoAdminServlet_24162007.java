package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Videos_24162007;
import service.CategoryService_24162007;
import service.VideoService_24162007;
import util.UploadUtil_24162007;

@WebServlet("/admin/videos")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
                 maxFileSize = 200L * 1024 * 1024,
                 maxRequestSize = 210L * 1024 * 1024)
public class VideoAdminServlet_24162007 extends HttpServlet {

    private VideoService_24162007 videoService = new VideoService_24162007();
    private CategoryService_24162007 categoryService = new CategoryService_24162007();

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("videos", videoService.getAllVideos());
        req.setAttribute("categories", categoryService.getAllCategories());
        req.getRequestDispatcher("/admin/videoList.jsp").forward(req, resp);
    }

    private void deleteFile(String name) {
        if (name == null || name.isBlank() || name.contains("/") || name.contains("\\") || name.contains("..")) return;
        try {
            Files.deleteIfExists(UploadUtil_24162007.ROOT.resolve(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            Videos_24162007 v = videoService.getVideoById(req.getParameter("videoId"));
            if (v == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
                return;
            }
            req.setAttribute("video", v);
            req.setAttribute("categories", categoryService.getAllCategories());
            req.getRequestDispatcher("/admin/videoForm.jsp").forward(req, resp);
        } else {
            showList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        try {
            if ("delete".equals(action)) {
                String id = req.getParameter("videoId");
                Videos_24162007 old = videoService.getVideoById(id);
                if (old != null && videoService.deleteVideo(id)) {
                    deleteFile(old.getPoster());
                    deleteFile(old.getVideoFile());
                }
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
                return;
            }

            long price = Long.parseLong(req.getParameter("price").trim());
            int stock = Integer.parseInt(req.getParameter("stock").trim());
            if (price < 0 || stock < 0) {
                throw new IllegalStateException("Giá và tồn kho không được âm");
            }

            Videos_24162007 v = new Videos_24162007();
            v.setTitle(req.getParameter("title"));
            v.setDescription(req.getParameter("description"));
            v.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
            v.setPrice(price);
            v.setStock(stock);
            v.setPoster(UploadUtil_24162007.save(req.getPart("poster"), "image"));
            v.setVideoFile(UploadUtil_24162007.save(req.getPart("videoFile"), "video"));

            if ("update".equals(action)) {
                v.setVideoId(req.getParameter("videoId"));
                v.setActive(req.getParameter("active") != null);
                videoService.updateVideo(v);
            } else {
                v.setVideoId("VD" + UUID.randomUUID().toString().substring(0, 8));
                videoService.createVideo(v);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        } catch (IOException | IllegalStateException | NumberFormatException e) {
            req.setAttribute("error", "Thao tác thất bại: " + e.getMessage());
            showList(req, resp);
        }
    }
}