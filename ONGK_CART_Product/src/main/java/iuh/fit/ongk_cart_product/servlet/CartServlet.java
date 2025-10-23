package iuh.fit.ongk_cart_product.servlet;

import iuh.fit.ongk_cart_product.dao.BookDAO;
import iuh.fit.ongk_cart_product.model.Book;
import iuh.fit.ongk_cart_product.model.Cart;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
@WebServlet(name = "CartServlet", value = "/cart")
public class CartServlet extends HttpServlet {
    private BookDAO bookDAO;

    @Resource(name = "jdbc/bookdb")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if (dataSource == null) {
            throw new ServletException("DataSource is null");
        }
        bookDAO = new BookDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            session.setAttribute("cart",cart);

        }

        String action = req.getParameter("action");
        try{
            if("add".equals(action)){
                int id = Integer.parseInt(req.getParameter("id"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                Book book = bookDAO.getBookById(id);
                if(book == null){
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getParameter("id"));
                }else{
                    cart.addCartItem(book,quantity);
                }
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                cart.updateQuantity(id,quantity);
            }else if ("delete".equals(action)){
                int id = Integer.parseInt(req.getParameter("id"));
                cart.removeCartItem(id);
            } else if ("clear".equals(action)) {
                cart.clearCart();
            } else if ("checkout".equals(action)) {
                double total = cart.getTotalPrice();
                req.setAttribute("total",total);

                req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("cart");
    }
}
