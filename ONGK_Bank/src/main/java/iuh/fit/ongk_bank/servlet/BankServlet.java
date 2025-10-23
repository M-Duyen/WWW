package iuh.fit.ongk_bank.servlet;

import iuh.fit.ongk_bank.dao.AccountDAO;
import iuh.fit.ongk_bank.model.Account;
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
@WebServlet(name = "BankServlet", value = "/bank")
public class BankServlet extends HttpServlet {
    private AccountDAO accountDAO;

    @Resource(name = "jdbc/bankdb")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if (dataSource == null) {
            throw new ServletException("DataSource is null");
        }
        accountDAO = new AccountDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String criteria = req.getParameter("criteria");
        List<Account> accounts;
        if("amount".equals(criteria)){
            String minStr = req.getParameter("min");
            String maxStr = req.getParameter("max");

            Double min = (minStr != null && !minStr.trim().isEmpty()) ? Double.parseDouble(minStr) : null;
            Double max = (maxStr != null && !maxStr.trim().isEmpty()) ? Double.parseDouble(maxStr) : null;

            accounts = accountDAO.filterAccountByAmount(min, max);
        } else if ("city".equals(criteria)) {
            String city = req.getParameter("city");

            accounts =  accountDAO.getAccountByCity(city);
        }else{
            accounts = accountDAO.getAllAccounts();
        }

        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher("/account-list.jsp").forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            String ownerName = req.getParameter("ownerName");
            String cardNumber = req.getParameter("cardNumber");
            String ownerAddress = req.getParameter("ownerAddress");
            double amount = Double.parseDouble(req.getParameter("amount"));

            Account account = new Account();
            account.setOwnerName(ownerName);
            account.setCardNumber(cardNumber);
            account.setOwnerAddress(ownerAddress);
            account.setAmount(amount);

            accountDAO.addAccount(account);
        }

        resp.sendRedirect(req.getContextPath() + "/bank");
    }
}
