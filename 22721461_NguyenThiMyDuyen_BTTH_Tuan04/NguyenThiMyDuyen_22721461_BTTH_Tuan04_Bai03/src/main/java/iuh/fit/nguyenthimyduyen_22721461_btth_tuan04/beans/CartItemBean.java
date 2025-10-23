package iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartItemBean {
    private Product product;
    private int quantity;

    public double getSubtotal(){
        return product.getPrice() * quantity;
    }
}
