package entity;

public class Videos_24162007 {
    private String videoId;
    private String title;
    private String poster;
    private int views;
    private String description;
    private boolean active;
    private int categoryId;
    private String categoryname;
    private int shareCount;
    private int favoriteCount;
    private String videoFile;
    private long price;
    private int stock;

    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Videos_24162007() {}

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryname() { return categoryname; }
    public void setCategoryname(String categoryname) { this.categoryname = categoryname; }
    public int getShareCount() { return shareCount; }
    public void setShareCount(int shareCount) { this.shareCount = shareCount; }
    public int getFavoriteCount() { return favoriteCount; }
    public void setFavoriteCount(int favoriteCount) { this.favoriteCount = favoriteCount; }
    public String getVideoFile() { return videoFile; }
    public void setVideoFile(String videoFile) { this.videoFile = videoFile; }
}