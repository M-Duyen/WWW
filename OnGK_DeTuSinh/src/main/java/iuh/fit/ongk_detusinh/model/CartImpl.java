package iuh.fit.ongk_detusinh.model;

public interface CartImpl {
    void addCartItem(Book book, int quantity);

    void removeCartItem(String id);

    void updateQuantity(String id, int quantity);

    double getTotal();

    void clear();

    java.util.List<CartItem> getCartItems();

    void setCartItems(java.util.List<CartItem> cartItems);
}
