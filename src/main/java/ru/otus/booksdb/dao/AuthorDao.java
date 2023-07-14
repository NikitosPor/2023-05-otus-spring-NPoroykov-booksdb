package ru.otus.booksdb.dao;

import ru.otus.booksdb.domain.Author;

import java.util.List;

public interface AuthorDao {

    int count();

    long insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    Author getByName(String name);
}
