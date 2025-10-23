package iuh.fit.ongk_detusinh.dao;

import iuh.fit.ongk_detusinh.model.Book;

import java.util.List;

public interface BookImpl {
    List<Book> getAllBooks();

    Book getBookById(String id);

    void addBook(Book book);

    void updateBook(Book book);

    List<Book> getBooksByCategoryId(String id);

    List<Book> getBooksByName(String name);

    List<Book> getBookByPrice(Double min, Double max);

    List<String> getAuthors();

    List<Book> getBooksByAuthor(String author);
}
