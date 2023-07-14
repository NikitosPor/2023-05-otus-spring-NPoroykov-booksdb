package ru.otus.booksdb.dao;

import org.springframework.stereotype.Repository;
import ru.otus.booksdb.domain.Book;

import java.util.List;

@Repository
public interface BookDao {

    int count();

    long insert(Book book);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);

    Book getByTitle(String title);

    void updateTitleById(long id, String newTitle);
}
