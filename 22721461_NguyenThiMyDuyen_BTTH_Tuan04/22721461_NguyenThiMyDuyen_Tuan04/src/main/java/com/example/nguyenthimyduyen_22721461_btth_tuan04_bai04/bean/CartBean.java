package com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.bean;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CartBean {
    private List<CartItemBean> cartItemBeans;

    public CartBean() {
        this.cartItemBeans = new ArrayList<>();

    }

    public void addBook (Book book, int quantity){
        for (CartItemBean item : cartItemBeans){
            if(item.getBook().getId() == book.getId()){
                item.setQuantity((item.getQuantity() + quantity));
                return;
            }
        }
        cartItemBeans.add(new CartItemBean(book, quantity));
    }
    public void removeBook(int bookId){
        cartItemBeans.removeIf(cartItemBean -> cartItemBean.getBook().getId() == bookId);
    }
    public void updateQuantity(int bookId, int quantity){
        for(CartItemBean item : cartItemBeans){
            if(item.getBook().getId() == bookId){
                if(quantity > 0){
                    item.setQuantity(quantity);
                } else {
                    removeBook(bookId);
                }
            }
        }
    }
    public double getTotal(){
        double total = 0;
        for (CartItemBean item : cartItemBeans){
            total += item.getSubTotal();
        }
        return Math.round(total * 100) / 100.0;
    }
    public void clearCart(){
        cartItemBeans.clear();
    }

}
