package iuh.fit.ongk_cart.dao;

import iuh.fit.ongk_cart.model.Book;
import iuh.fit.ongk_cart.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final DBUtil dbUtil;

    public BookDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "Select * from book";
        try (Connection connection = dbUtil.getCOnnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("image")

                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public Book getBookById(int id) {
        String sql = "select * from book where id = ?";
        try (Connection connection = dbUtil.getCOnnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getString("image")

                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void addBook(Book book){
        String sql = "INSERT INTO book (name, price, quantity, image) VALUES (?,?,?,?)";
        try(Connection connection = dbUtil.getCOnnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, book.getName());
            ps.setDouble(2, book.getPrice());
            ps.setInt(3, book.getQuantity());
            ps.setString(4, book.getImage());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
