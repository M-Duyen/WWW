package iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.beans;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CartBean {
    private final List<CartItemBean> items;

    public CartBean() {
        items = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity) {
        for (CartItemBean item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItemBean(product, quantity));
    }

    public void removeProduct(int productID) {
        items.removeIf(cartItemBean -> cartItemBean.getProduct().getId() == productID);
    }

    public void updateQuantity(int productID, int quantity) {
        for (CartItemBean item : items) {
            if (item.getProduct().getId() == productID) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    removeProduct(productID);
                }
            }
        }
    }

    public double getTotal() {
        double total = 0;
        for (CartItemBean item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }
}
