package iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.servlet;

import iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.beans.Product;
import iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.dao.ProductDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "/products", value = "/product")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Resource(name = "jdbc/shopdb")
    private DataSource dataSource;

    public ProductServlet() {
    }

    @Override
    public void init() {
        if (dataSource == null) {
            throw new RuntimeException("DataSource is not initialized!");
        }
        productDAO = new ProductDAO(dataSource);
        System.out.println("ProductDAO initialized successfully.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String proID = req.getParameter("id");
        if (proID != null) {
            int id = Integer.parseInt(proID);
            Product product = productDAO.getProductByID(id);
            if (product != null) {
                req.setAttribute("product", product);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product-detail.jsp");
                dispatcher.forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_ACCEPTED, "Product not found");
                return;
            }
        }
        List<Product> products = productDAO.getAllProducts();
        req.setAttribute("products", products);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product-list.jsp");
        dispatcher.forward(req, resp);
    }
}