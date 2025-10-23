package iuh.fit.ongk_detusinh.servlet;

import iuh.fit.ongk_detusinh.dao.BookDAO;
import iuh.fit.ongk_detusinh.dao.CategoryDAO;
import iuh.fit.ongk_detusinh.dao.CategoryImpl;
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
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@WebServlet(name = "CategoryServlet", value = "/category")
public class CategoryServlet extends HttpServlet {
    private CategoryImpl categoryDAO;
    private BookDAO bookDAO;

    @Resource(name = "jdbc/bookstore")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if(dataSource == null){
            throw new ServletException("DataSource is null");
        }
        categoryDAO = new CategoryDAO(dataSource);
        bookDAO = new BookDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("detail".equals(action)){
            String id = req.getParameter("id");
            Category category = categoryDAO.getCategoryById(id);
            req.setAttribute("category", category);
            req.getRequestDispatcher("/category-form.jsp").forward(req, resp);

        } else if ("list".equals(action)) {
            String id = req.getParameter("id");
            List<Book> books = bookDAO.getBooksByCategoryId(id);
            req.setAttribute("books", books);
            req.getRequestDispatcher("/book-list.jsp").forward(req,resp);

        } else if ("search".equals(action)) {
            String name = req.getParameter("name");
            List<Category> categories = categoryDAO.getCategoryByName(name);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/category-list.jsp").forward(req, resp);

        } else{
            List<Category> categories = categoryDAO.getAllCategory();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/category-list.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("update".equals(action)){
            String id = req.getParameter("id");
            String name = req.getParameter("name");

            categoryDAO.updateCategory(new Category(id,name));
        } else if ("add".equals(action)) {
            String id = req.getParameter("id");
            String name = req.getParameter("name");

            categoryDAO.addCategory(new Category(id,name));
        } else if ("delete".equals(action)) {
            String id = req.getParameter("id");
            try{
                boolean check = categoryDAO.deleteCategory(id);
                if(!check){
                    req.getSession().setAttribute("message", "Xoa that bai");
                }
            } catch (RuntimeException e) {
                req.getSession().setAttribute("message", e.getMessage());
            }
        }
        resp.sendRedirect(req.getContextPath() + "/category");
    }
}
