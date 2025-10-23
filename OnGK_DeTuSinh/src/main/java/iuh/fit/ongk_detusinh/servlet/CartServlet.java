package iuh.fit.ongk_detusinh.servlet;

import iuh.fit.ongk_detusinh.dao.BookDAO;
import iuh.fit.ongk_detusinh.dao.BookImpl;
import iuh.fit.ongk_detusinh.model.Book;
import iuh.fit.ongk_detusinh.model.Cart;
import iuh.fit.ongk_detusinh.model.CartImpl;
import iuh.fit.ongk_detusinh.model.CartItem;
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
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@WebServlet(name = "CartServlet", value = "/cart")
public class CartServlet extends HttpServlet {
    private BookImpl bookDAO;

    @Resource(name = "jdbc/bookstore")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if(dataSource == null){
            throw new ServletException("DataSource is null");
        }
        bookDAO = new BookDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();;
        CartImpl cart = (CartImpl) session.getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/cart.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session= req.getSession();;
        CartImpl cart = (Cart) session.getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        String action = req.getParameter("action");
        if("add".equals(action)){
            String id = req.getParameter("id");
            Book book = bookDAO.getBookById(id);
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            cart.addCartItem(book, quantity);
        } else if ("update".equals(action)) {
            String id = req.getParameter("id");
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            cart.updateQuantity(id, quantity);
        }else if ("remove".equals(action)){
            String id = req.getParameter("id");
            cart.removeCartItem(id);
        }else if("clear".equals(action)){
            cart.clear();
        } else if ("checkout".equals(action)) {
            List<String> list = new ArrayList<>();
            cart.getCartItems().forEach(item -> list.add(item.getBook().getId()));
            req.setAttribute("listID",list);

            Double total = cart.getTotal();
            req.setAttribute("total", total);

            req.getRequestDispatcher("/checkout.jsp").forward(req,resp);
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

}
