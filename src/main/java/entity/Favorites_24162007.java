package entity;

import java.sql.Date;

public class Favorites_24162007 {
    private int favoriteId;
    private Date likedDate;
    private String videoId;
    private String username;

    public Favorites_24162007() {}

    public int getFavoriteId() { return favoriteId; }
    public void setFavoriteId(int favoriteId) { this.favoriteId = favoriteId; }
    public Date getLikedDate() { return likedDate; }
    public void setLikedDate(Date likedDate) { this.likedDate = likedDate; }
    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}