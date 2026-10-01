package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Videos_24162007;
import service.VideoService_24162007;

@WebServlet("/videoDetail")
public class VideoDetailServlet_24162007 extends HttpServlet {

    private VideoService_24162007 videoService = new VideoService_24162007();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoId = req.getParameter("videoId");
        if (videoId == null || videoId.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        Videos_24162007 video = videoService.getVideoDetail(videoId);
        if (video == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy video");
            return;
        }
        req.setAttribute("video", video);
        req.getRequestDispatcher("/videoDetail.jsp").forward(req, resp);
    }
}
