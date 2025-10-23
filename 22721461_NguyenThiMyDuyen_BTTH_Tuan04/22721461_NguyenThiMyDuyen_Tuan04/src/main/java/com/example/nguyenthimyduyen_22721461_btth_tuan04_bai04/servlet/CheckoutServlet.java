package com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.servlet;

import com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.bean.CartBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        CartBean cart = null;

        if (session != null) {
            cart = (CartBean) session.getAttribute("cart");
        }

        if (cart == null || cart.getCartItemBeans().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        request.setAttribute("cart", cart);
        System.out.println("Cart total in servlet: " + cart.getTotal());
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullname = request.getParameter("fullname");
        String address = request.getParameter("address");
        String payment = request.getParameter("payment");
        String totalStr = request.getParameter("total");


        if (fullname == null || fullname.trim().isEmpty() ||
                address == null || address.trim().isEmpty() ||
                payment == null || payment.trim().isEmpty()) {
            request.setAttribute("error", "Please fill all required fields");
            doGet(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.removeAttribute("cart");

        response.sendRedirect(request.getContextPath() + "/checkout-success.jsp");
    }
}