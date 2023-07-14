package ru.otus.booksdb.service;

import ru.otus.booksdb.domain.Genre;

import java.util.List;

public interface GenreOperationsService {

    long create(String title);

    Genre getById(long id);

    int printNumberOfAll();

    List<Genre> printAll();

}
