package iuh.fit.ongk_detusinh.dao;

import iuh.fit.ongk_detusinh.model.Book;
import iuh.fit.ongk_detusinh.model.Category;
import iuh.fit.ongk_detusinh.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements BookImpl {
    private final DBUtil dbUtil;

    public BookDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {
            while (rs.next()) {
                String categoryId = rs.getString("category_id");
                Category category = new Category();
                category.setId(categoryId);

                list.add(new Book(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getDate("publication"),
                        rs.getString("imageUrl"),
                        category
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Book getBookById(String id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String categoryId = rs.getString("category_id");
                    Category category = new Category();
                    category.setId(categoryId);
                    return new Book(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getDate("publication"),
                            rs.getString("imageUrl"),
                            category
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void addBook(Book book) {
        String sql = "INSERT INTO Book (id, name, author, price, publication, imageUrl, category_id) VALUES (?,?,?,?,?,?)";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, book.getId());
            ps.setString(2, book.getName());
            ps.setString(3, book.getAuthor());
            ps.setDouble(4, book.getPrice());
            ps.setDate(5, book.getPublication());
            ps.setString(6, book.getImageUrl());

            ps.setString(7, book.getCategory().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE book SET  name = ?, author = ?, price = ?, publication = ?, imageUrl = ?, category_id  = ? WHERE id = ? ";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {

            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setDate(4, book.getPublication());
            ps.setString(5, book.getImageUrl());

            ps.setString(6, book.getCategory().getId());

            ps.setString(7, book.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getBooksByCategoryId(String id) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE category_id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String categoryId = rs.getString("category_id");
                    Category category = new Category();
                    category.setId(categoryId);
                    list.add(new Book(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getDate("publication"),
                            rs.getString("imageUrl"),
                            category
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public List<Book> getBooksByName(String name) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE name LIKE ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String categoryId = rs.getString("category_id");
                    Category category = new Category();
                    category.setId(categoryId);
                    list.add(new Book(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getDate("publication"),
                            rs.getString("imageUrl"),
                            category
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Book> getBookByPrice(Double min, Double max) {
        List<Book> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM book");
        if (min != null && max != null) {
            sql.append(" WHERE price BETWEEN ? AND ?");
        } else if (min != null) {
            sql.append(" WHERE price >= ?");
        } else if (max != null) {
            sql.append(" WHERE price <= ?");
        }

        sql.append(" ORDER BY price ASC");

        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.toString())
        ) {
            if (min != null && max != null) {
                ps.setDouble(1, min);
                ps.setDouble(2, max);
            } else if (min != null) {
                ps.setDouble(1, min);
            } else if (max != null) {
                ps.setDouble(1, max);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String categoryId = rs.getString("category_id");
                    Category category = new Category();
                    category.setId(categoryId);
                    list.add(new Book(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getDate("publication"),
                            rs.getString("imageUrl"),
                            category
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    @Override
    public List<String> getAuthors(){
        List<String> list = new ArrayList<>();
        List<Book> books = getAllBooks();
        books.forEach(item -> list.add(item.getAuthor()));
        return list;

    }
    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE author LIKE ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, "%" + author + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String categoryId = rs.getString("category_id");
                    Category category = new Category();
                    category.setId(categoryId);
                    list.add(new Book(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getDate("publication"),
                            rs.getString("imageUrl"),
                            category
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
