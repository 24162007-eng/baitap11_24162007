package service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import dao.VideoDAO_24162007;
import entity.CartItem_24162007;
import entity.Videos_24162007;
import jakarta.servlet.http.HttpSession;

public class CartService_24162007 {

    public static final int MAX_PER_ITEM = 10;

    private VideoDAO_24162007 videoDAO = new VideoDAO_24162007();

    @SuppressWarnings("unchecked")
    public Map<String, CartItem_24162007> getCart(HttpSession session) {
        Object o = session.getAttribute("cart");
        if (o == null) {
            Map<String, CartItem_24162007> cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
            return cart;
        }
        return (Map<String, CartItem_24162007>) o;
    }

    private int limitOf(Videos_24162007 v) {
        return Math.min(v.getStock(), MAX_PER_ITEM);
    }

    public String add(HttpSession session, String videoId, int qty) {
        if (videoId == null || videoId.isBlank()) return "Sản phẩm không hợp lệ";
        if (qty < 1) qty = 1;
        Videos_24162007 v = videoDAO.getVideoById(videoId);
        if (v == null || !v.isActive()) return "Sản phẩm không tồn tại hoặc đã ngừng bán";
        int limit = limitOf(v);
        if (limit <= 0) return "Sản phẩm đã hết hàng";

        Map<String, CartItem_24162007> cart = getCart(session);
        CartItem_24162007 item = cart.get(videoId);
        int newQty = (item == null ? 0 : item.getQuantity()) + qty;
        String warning = null;
        if (newQty > limit) {
            newQty = limit;
            warning = "Mỗi sản phẩm chỉ mua tối đa " + limit + ", giỏ hàng đã được điều chỉnh";
        }
        if (item == null) {
            item = new CartItem_24162007(v.getVideoId(), v.getTitle(), v.getPoster(), v.getPrice(), newQty, v.getStock());
            cart.put(videoId, item);
        }
        item.setQuantity(newQty);
        item.setPrice(v.getPrice());
        item.setStock(v.getStock());
        return warning;
    }

    public String update(HttpSession session, String videoId, int qty) {
        Map<String, CartItem_24162007> cart = getCart(session);
        CartItem_24162007 item = cart.get(videoId);
        if (item == null) return "Sản phẩm không có trong giỏ hàng";
        if (qty <= 0) {
            cart.remove(videoId);
            return null;
        }
        Videos_24162007 v = videoDAO.getVideoById(videoId);
        if (v == null || !v.isActive() || v.getStock() <= 0) {
            cart.remove(videoId);
            return "Sản phẩm không còn khả dụng, đã được xóa khỏi giỏ hàng";
        }
        int limit = limitOf(v);
        String warning = null;
        if (qty > limit) {
            qty = limit;
            warning = "Mỗi sản phẩm chỉ mua tối đa " + limit + ", số lượng đã được điều chỉnh";
        }
        item.setQuantity(qty);
        item.setPrice(v.getPrice());
        item.setStock(v.getStock());
        return warning;
    }

    public void remove(HttpSession session, String videoId) {
        getCart(session).remove(videoId);
    }

    public void clear(HttpSession session) {
        getCart(session).clear();
    }

    public void refresh(HttpSession session) {
        Iterator<Map.Entry<String, CartItem_24162007>> it = getCart(session).entrySet().iterator();
        while (it.hasNext()) {
            CartItem_24162007 item = it.next().getValue();
            Videos_24162007 v = videoDAO.getVideoById(item.getVideoId());
            if (v == null || !v.isActive() || v.getStock() <= 0) {
                it.remove();
                continue;
            }
            item.setPrice(v.getPrice());
            item.setStock(v.getStock());
            int limit = limitOf(v);
            if (item.getQuantity() > limit) item.setQuantity(limit);
        }
    }

    public long getTotal(Map<String, CartItem_24162007> cart) {
        long total = 0;
        for (CartItem_24162007 item : cart.values()) total += item.getTotal();
        return total;
    }
}