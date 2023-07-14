package ru.otus.booksdb.service;

import org.springframework.stereotype.Service;
import ru.otus.booksdb.dao.GenreDao;
import ru.otus.booksdb.domain.Genre;

import java.util.List;

@Service
public class GenreOperationsServiceImpl implements GenreOperationsService {

    private final GenreDao genreDao;

    public GenreOperationsServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public long create(String title) {
        Genre genre = new Genre(1, title);
        return genreDao.insert(genre);
    }


    public Genre getById(long id) {
        return genreDao.getById(id);
    }

    public int printNumberOfAll() {
        return genreDao.count();
    }

    public List<Genre> printAll() {
        return genreDao.getAll();
    }

}
