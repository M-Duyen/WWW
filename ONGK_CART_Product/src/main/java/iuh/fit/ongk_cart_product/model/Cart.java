package iuh.fit.ongk_cart_product.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public void addCartItem(Book book, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getBook().getId() == book.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(book, quantity));
    }



    public void removeCartItem(int id) {
        cartItems.removeIf(item -> item.getBook().getId() == id);
    }

    public void updateQuantity(int id, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getBook().getId() == id) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    removeCartItem(id);
                }
            }
        }

    }


    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems){
            total += item.getSubTotal();
        }
        return total;
    }
    public void clearCart(){
        cartItems.clear();
    }

}
