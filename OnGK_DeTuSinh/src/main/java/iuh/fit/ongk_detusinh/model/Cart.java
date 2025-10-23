package iuh.fit.ongk_detusinh.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Cart implements CartImpl {
    private List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    @Override
    public void addCartItem(Book book, int quantity){
        for(CartItem item : cartItems){
            if(item.getBook().getId().equals(book.getId())){
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(book, quantity));
    }

    @Override
    public void removeCartItem(String id){
        cartItems.removeIf(item -> item.getBook().getId().equals(id));
    }

    @Override
    public void updateQuantity(String id, int quantity){
        for(CartItem item : cartItems){
            if(item.getBook().getId().equals(id)){
                if(quantity > 0){
                    item.setQuantity(quantity);
                }else {
                    removeCartItem(id);
                }
            }
        }
    }

    @Override
    public double getTotal(){
        double total = 0;
        for(CartItem item : cartItems){
            total += item.getSubTotal();
        }
        return total;
    }

    @Override
    public void clear(){
        cartItems.clear();
    }
}
