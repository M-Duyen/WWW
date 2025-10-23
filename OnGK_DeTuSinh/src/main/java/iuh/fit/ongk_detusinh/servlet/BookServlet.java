package iuh.fit.ongk_detusinh.servlet;

import iuh.fit.ongk_detusinh.dao.BookDAO;
import iuh.fit.ongk_detusinh.dao.BookImpl;
import iuh.fit.ongk_detusinh.dao.CategoryDAO;
import iuh.fit.ongk_detusinh.model.Book;
import iuh.fit.ongk_detusinh.model.Category;
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
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@WebServlet(name = "BookServlet", value = "/book")
public class BookServlet extends HttpServlet {
    private BookImpl bookDAO;
    private CategoryDAO categoryDAO;

    @Resource(name = "jdbc/bookstore")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if(dataSource == null){
            throw new ServletException("DataSource is null");
        }
        bookDAO = new BookDAO(dataSource);
        categoryDAO = new CategoryDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String criteria = req.getParameter("criteria");
        List<String> authors = bookDAO.getAuthors();
        req.setAttribute("authors",authors);

        List<Book> books;
        if("price".equals(criteria)){
            String minStr = req.getParameter("min");
            String maxStr = req.getParameter("max");

            Double min = (minStr != null && !minStr.isEmpty()) ? Double.parseDouble(minStr) : null;
            Double max = (maxStr != null && !maxStr.isEmpty()) ? Double.parseDouble(maxStr) : null;

            books = bookDAO.getBookByPrice(min,max);
        } else if ("author".equals(criteria)) {


            String author = req.getParameter("author");
            books = bookDAO.getBooksByAuthor(author);
        } else if ("name".equals(criteria)) {
            String name = req.getParameter("name");
            books = bookDAO.getBooksByName(name);
        }else {
            books = bookDAO.getAllBooks();
        }
        String action = req.getParameter("action");
        if("detail".equals(action)){
            List<Category> categories = categoryDAO.getAllCategory();
            req.setAttribute("categories",categories);

            String id = req.getParameter("id");
            if(id != null){
                Book book = bookDAO.getBookById(id);
                req.setAttribute("book", book);
                req.getRequestDispatcher("/book-form.jsp").forward(req,resp);
            }

        }
        req.setAttribute("books", books);
        req.getRequestDispatcher("/book-list.jsp").forward(req,resp);




    }
}
