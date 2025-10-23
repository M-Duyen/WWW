package iuh.fit.ongk_cart_product.dao;

import iuh.fit.ongk_cart_product.model.Book;
import iuh.fit.ongk_cart_product.util.DBUtil;

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
        String sql = "SELECT * FROM book";
        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {
            while (rs.next()) {
                books.add(new Book(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("image")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public Book getBookById(int id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Book(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getString("image"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Book> searchBookByName(String value) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE name  LIKE ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, "%" + value + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getString("image")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO book (name, price, quantity, image) VALUES (?,?,?,?)";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, book.getName());
            ps.setDouble(2, book.getPrice());
            ps.setInt(3, book.getQuantity());
            ps.setString(4, book.getImage());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book) {
        String sql = "UPDATE book SET name = ?, price = ?, quantity = ?, image = ? WHERE is = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, book.getName());
            ps.setDouble(2, book.getPrice());
            ps.setInt(3, book.getQuantity());
            ps.setString(4, book.getImage());
            ps.setInt(5, book.getId());
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBookById(int id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
