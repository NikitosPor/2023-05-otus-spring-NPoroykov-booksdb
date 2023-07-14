package ru.otus.booksdb.dao;

import ru.otus.booksdb.domain.Genre;

import java.util.List;

public interface GenreDao {

    int count();

    long insert(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    Genre getByTitle(String title);

}
