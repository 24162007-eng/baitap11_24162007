package entity;

public class OrderDetails_24162007 {
    private int detailId;
    private int orderId;
    private String videoId;
    private String title;
    private long price;
    private int quantity;

    public OrderDetails_24162007() {}

    public int getDetailId() { return detailId; }
    public void setDetailId(int detailId) { this.detailId = detailId; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public long getTotal() { return price * quantity; }
}