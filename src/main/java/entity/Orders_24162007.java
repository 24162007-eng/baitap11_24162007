package entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Orders_24162007 {
    private int orderId;
    private String username;
    private String receiverName;
    private String phone;
    private String address;
    private String note;
    private long totalAmount;
    private String paymentMethod;
    private String status;
    private Timestamp createdDate;
    private List<OrderDetails_24162007> details = new ArrayList<>();

    public Orders_24162007() {}

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public long getTotalAmount() { return totalAmount; }
    public void setTotalAmount(long totalAmount) { this.totalAmount = totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getCreatedDate() { return createdDate; }
    public void setCreatedDate(Timestamp createdDate) { this.createdDate = createdDate; }
    public List<OrderDetails_24162007> getDetails() { return details; }
    public void setDetails(List<OrderDetails_24162007> details) { this.details = details; }
}