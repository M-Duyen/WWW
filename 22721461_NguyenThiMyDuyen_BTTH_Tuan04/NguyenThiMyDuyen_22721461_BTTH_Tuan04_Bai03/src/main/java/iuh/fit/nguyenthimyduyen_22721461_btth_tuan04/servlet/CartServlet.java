package iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.servlet;

import iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.beans.CartBean;
import iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.beans.Product;
import iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.dao.ProductDAO;
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
    private ProductDAO productDAO;

    @Resource(name = "jdbc/shopdb")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            productDAO = new ProductDAO(dataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
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
                Product product = productDAO.getProductByID(id);
                cartBean.addProduct(product, quantity);
            } else if ("update".equals(action)) {
                String idStr = req.getParameter("id");
                String quantityStr = req.getParameter("quantity");

                if (idStr == null || quantityStr == null) {
                    throw new RuntimeException("Missing parameters: id or quantity is null");
                }

                int id = Integer.parseInt(idStr);
                int quantity = Integer.parseInt(quantityStr);

                cartBean.updateQuantity(id, quantity);
            } else if ("remove".equals(action)) {
                int id = Integer.parseInt(req.getParameter("productId"));
                cartBean.removeProduct(id);
            } else if ("clear".equals(action)) {
                cartBean.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getHeader("referer"));
    }
}
