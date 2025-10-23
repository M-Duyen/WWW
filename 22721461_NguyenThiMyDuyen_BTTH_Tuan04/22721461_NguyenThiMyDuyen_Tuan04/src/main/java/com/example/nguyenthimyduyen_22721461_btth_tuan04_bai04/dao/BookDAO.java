package com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.dao;

import com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.bean.Book;
import com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.util.DBUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private DBUtil dbUtil;

    public BookDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection connection = dbUtil.connection();
             var preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setName(resultSet.getString("name"));
                book.setPrice(resultSet.getDouble("price"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setImage(resultSet.getString("image"));
                list.add(book);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Book getBookById(int Id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = dbUtil.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, Id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");
                    String image = resultSet.getString("image");

                    return new Book(id, name, price, quantity, image);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<Book> searchBooks(String query) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE name LIKE ?";
        try (Connection conn = dbUtil.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
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
        }
        return books;
    }
}
