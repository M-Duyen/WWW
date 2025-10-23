package com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.servlet;

import com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.bean.Book;
import com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.dao.BookDAO;
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

@WebServlet(name = "/books", value = "/book")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource(name = "jdbc/bookdb")
    private DataSource dataSource;
    private BookDAO bookDAO;

    public BookServlet() {
    }

    @Override
    public void init() throws ServletException {
        if(dataSource == null){
            throw  new RuntimeException("DataSource is null");
        }
        bookDAO = new BookDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookId = req.getParameter("id");
        if(bookId != null){
            int id = Integer.parseInt(bookId);
            Book book = bookDAO.getBookById(id);
            if(book != null){
                req.setAttribute("book", book);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/book-detail.jsp");
                dispatcher.forward(req, resp);
            }else{
                resp.sendError(HttpServletResponse.SC_ACCEPTED, "Book not found");
                return;
            }
        }
        List<Book> books = bookDAO.getAllBooks();
        req.setAttribute("books", books);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/book-list.jsp");
        dispatcher.forward(req, resp);
    }
}
