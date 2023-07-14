package ru.otus.booksdb.service;

import org.springframework.stereotype.Service;
import ru.otus.booksdb.dao.AuthorDao;
import ru.otus.booksdb.domain.Author;

import java.util.List;

@Service
public class AuthorOperationsServiceImpl implements AuthorOperationsService {

    private final AuthorDao authorDao;

    public AuthorOperationsServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public long create(String name) {
        Author author = new Author(1, name);
        return authorDao.insert(author);
    }

    public Author getById(long id) {
        return authorDao.getById(id);
    }

    public int printNumberOfAll() {
        return authorDao.count();
    }

    public List<Author> printAll() {
        return authorDao.getAll();
    }

}
