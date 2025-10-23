package com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemBean {
    private Book book;
    private int quantity;

    public double getSubTotal(){
        return book.getPrice() * quantity;
    }


}
