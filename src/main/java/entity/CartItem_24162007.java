package entity;

public class CartItem_24162007 {
    private String videoId;
    private String title;
    private String poster;
    private long price;
    private int quantity;
    private int stock;

    public CartItem_24162007() {}

    public CartItem_24162007(String videoId, String title, String poster, long price, int quantity, int stock) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.price = price;
        this.quantity = quantity;
        this.stock = stock;
    }

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public long getTotal() { return price * quantity; }
}