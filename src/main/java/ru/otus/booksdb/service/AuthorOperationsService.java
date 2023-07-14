package ru.otus.booksdb.service;

import ru.otus.booksdb.domain.Author;

import java.util.List;

public interface AuthorOperationsService {

    long create(String name);


    Author getById(long id);

    int printNumberOfAll();

    List<Author> printAll();

}
