package entity;

public enum OrderStatus_24162007 {
    NEW("Đơn hàng mới", "st-new"),
    CONFIRMED("Đã xác nhận", "st-confirmed"),
    PREPARING("Chuẩn bị hàng", "st-preparing"),
    SHIPPING("Vận chuyển", "st-shipping"),
    DELIVERING("Giao hàng", "st-delivering"),
    DELIVERED("Đã giao", "st-delivered"),
    CANCELLED("Đơn hàng hủy", "st-cancelled"),
    RETURNED("Đơn hàng hoàn", "st-returned");

    private final String label;
    private final String cssClass;

    OrderStatus_24162007(String label, String cssClass) {
        this.label = label;
        this.cssClass = cssClass;
    }

    public String getCode() { return name(); }
    public String getLabel() { return label; }
    public String getCssClass() { return cssClass; }

    public static OrderStatus_24162007 fromCode(String code) {
        if (code == null) return null;
        for (OrderStatus_24162007 s : values()) {
            if (s.name().equalsIgnoreCase(code.trim())) return s;
        }
        return null;
    }
}