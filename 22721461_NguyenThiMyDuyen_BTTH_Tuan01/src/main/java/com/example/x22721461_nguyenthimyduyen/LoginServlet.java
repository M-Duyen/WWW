package com.example.x22721461_nguyenthimyduyen;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // For simplicity, we are using hardcoded credentials.
        // In a real application, you would validate against a database.
        if ("admin".equals(username) && "123".equals(password)) {

            req.getSession().setAttribute("user", username);
            resp.sendRedirect(req.getContextPath() + "/home.jsp");
        } else {
            req.setAttribute("errorMessage", "Invalid credentials");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
