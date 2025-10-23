package iuh.fit.ongk_cart_product.servlet;

import iuh.fit.ongk_cart_product.dao.BookDAO;
import iuh.fit.ongk_cart_product.model.Book;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@WebServlet(name= "BookServlet", value = "/book")
public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;

    @Resource(name = "jdbc/bookdb")
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
        String action = req.getParameter("action");
        if("details".equals(action)){
            int id = Integer.parseInt(req.getParameter("id"));
            Book book = bookDAO.getBookById(id);
            req.setAttribute("book", book);
            req.getRequestDispatcher("book-detail.jsp").forward(req,resp);
        }
        List<Book> books = bookDAO.getAllBooks();
        req.setAttribute("books", books);
        req.getRequestDispatcher("/book-list.jsp").forward(req, resp);

    }
}
