package iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.beans;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private int id;
    private String model;
    private double price;
    private int quantity;
    private String description;
    private String image;
}
