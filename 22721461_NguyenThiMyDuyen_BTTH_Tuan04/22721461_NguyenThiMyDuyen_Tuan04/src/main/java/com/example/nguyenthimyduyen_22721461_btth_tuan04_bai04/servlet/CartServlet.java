package com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.servlet;

import com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.bean.Book;
import com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.bean.CartBean;
import com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.dao.BookDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private BookDAO bookDAO;

    @Resource(name = "jdbc/bookdb")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            bookDAO = new BookDAO(dataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartBean cartBean = (CartBean) session.getAttribute("cart");
        if (cartBean == null) {
            cartBean = new CartBean();
            session.setAttribute("cart", cartBean);
        }
        req.setAttribute("cart", cartBean);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartBean cartBean = (CartBean) session.getAttribute("cart");
        if (cartBean == null) {
            cartBean = new CartBean();
            session.setAttribute("cart", cartBean);
        }

        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                Book product = bookDAO.getBookById(id);
                cartBean.addBook(product, quantity);
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                cartBean.updateQuantity(id, quantity);
            } else if ("remove".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                cartBean.removeBook(id);
            } else if ("clear".equals(action)) {
                cartBean.clearCart();
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect("cart");
    }

}
