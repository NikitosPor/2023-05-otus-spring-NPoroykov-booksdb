package ru.otus.booksdb.service;

import ru.otus.booksdb.domain.Book;

import java.util.List;

public interface BookOperationsService {

    long create(String bookTitle, String bookAuthor, String bookGenre);

    void deleteById(long id);

    Book getById(long id);

    int printNumberOfAll();

    List<Book> printAll();

    void updateTitleById(long id, String newTitle);

}
