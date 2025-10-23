package iuh.fit.ongk_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
    private Book book;
    private int quantity;
    public double getSubTotal(){
        return book.getPrice() * quantity;
    }
}
