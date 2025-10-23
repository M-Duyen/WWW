package iuh.fit.ongk_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String image;

}
