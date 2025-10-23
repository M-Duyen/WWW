package iuh.fit.ongk_bank.dao;

import iuh.fit.ongk_bank.model.Account;
import iuh.fit.ongk_bank.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private final DBUtil dbUtil;

    public AccountDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM bank_accounts";
        try (Connection connection = dbUtil.getCOnnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {
            while (rs.next()) {
                accounts.add(new Account(
                        rs.getLong("account_number"),
                        rs.getString("owner_name"),
                        rs.getString("card_number"),
                        rs.getString("owner_address"),
                        rs.getDouble("amount")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;

    }

    public void addAccount(Account account) {
        String sql = "INSERT INTO bank_accounts(owner_name, card_number, owner_address, amount) VALUES (?,?,?,?)";
        try (Connection connection = dbUtil.getCOnnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, account.getOwnerName());
            ps.setString(2, account.getCardNumber());
            ps.setString(3, account.getOwnerAddress());
            ps.setDouble(4, account.getAmount());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    (1.5 đ) Lọc theo số dư: nhập khoảng [min, max] để lấy các tài khoản có amount trong
//    khoảng. Nếu trống một đầu mút thì không giới hạn đầu mút đó
    public List<Account> filterAccountByAmount(Double min, Double max) {
        List<Account> accounts = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM bank_accounts WHERE 1=1");

        if (min != null) {
            sql.append(" AND amount >= ?");
        }
        if (max != null) {
            sql.append(" AND amount <= ?");
        }

        try (Connection connection = dbUtil.getCOnnection();
             PreparedStatement ps = connection.prepareStatement(sql.toString());
        ) {
            int idx = 1;
            if (min != null) {
                ps.setDouble(idx++, min);
            }
            if (max != null) {
                ps.setDouble(idx++, max);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accounts.add(new Account(
                            rs.getLong("account_number"),
                            rs.getString("owner_name"),
                            rs.getString("card_number"),
                            rs.getString("owner_address"),
                            rs.getDouble("amount")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    //    Lọc theo tỉnh/thành phố: tìm các tài khoản thuộc một tỉnh/thành phố xác định (lấy từ owner_address).
    public List<Account> getAccountByCity(String city) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM bank_accounts WHERE owner_address LIKE ?";
        try (Connection connection = dbUtil.getCOnnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, "%" + city + "%");
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    accounts.add(new Account(
                            rs.getLong("account_number"),
                            rs.getString("owner_name"),
                            rs.getString("card_number"),
                            rs.getString("owner_address"),
                            rs.getDouble("amount")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }
}
