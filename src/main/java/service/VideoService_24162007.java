package service;

import java.util.List;
import java.util.Map;
import dao.VideoDAO_24162007;
import entity.Videos_24162007;

public class VideoService_24162007 {

    private VideoDAO_24162007 videoDAO = new VideoDAO_24162007();

    // Xem chi tiết: có tăng lượt xem
    public Videos_24162007 getVideoDetail(String videoId) {
        videoDAO.increaseView(videoId);
        return videoDAO.getVideoById(videoId);
    }

    // Lấy video để sửa/xóa: KHÔNG tăng lượt xem
    public Videos_24162007 getVideoById(String videoId) {
        return videoDAO.getVideoById(videoId);
    }

    public List<Videos_24162007> getVideosByCategory(int categoryId, int page, int pageSize) {
        return videoDAO.getVideosByCategory(categoryId, page, pageSize);
    }

    public int countVideosByCategory(int categoryId) {
        return videoDAO.countVideosByCategory(categoryId);
    }

    public Map<Integer, Integer> countVideosGroupByCategory() {
        return videoDAO.countVideosGroupByCategory();
    }

    public int getTotalPages(int categoryId, int pageSize) {
        int total = videoDAO.countVideosByCategory(categoryId);
        return (int) Math.ceil((double) total / pageSize);
    }

    public List<Videos_24162007> getAllVideos() {
        return videoDAO.getAllVideos();
    }

    public boolean createVideo(Videos_24162007 v) {
        return videoDAO.insertVideo(v);
    }

    public boolean updateVideo(Videos_24162007 v) {
        return videoDAO.updateVideo(v);
    }

    public boolean deleteVideo(String videoId) {
        return videoDAO.deleteVideo(videoId);
    }
}