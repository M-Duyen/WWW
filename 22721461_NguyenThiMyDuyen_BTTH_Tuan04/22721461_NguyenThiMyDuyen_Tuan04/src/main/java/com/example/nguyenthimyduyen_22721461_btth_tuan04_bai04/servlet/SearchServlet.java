package com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.servlet;

import com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.dao.BookDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
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
        String query = req.getParameter("query");
        if (query != null && !query.trim().isEmpty()) {
            try {
                req.setAttribute("searchResults", bookDAO.searchBooks(query));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        req.getRequestDispatcher("/search-results.jsp").forward(req, resp);
    }
}