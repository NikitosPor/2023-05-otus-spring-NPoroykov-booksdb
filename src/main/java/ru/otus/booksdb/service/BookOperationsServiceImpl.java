package ru.otus.booksdb.service;

import org.springframework.stereotype.Service;
import ru.otus.booksdb.dao.AuthorDao;
import ru.otus.booksdb.dao.BookDao;
import ru.otus.booksdb.dao.GenreDao;
import ru.otus.booksdb.domain.Book;

import java.util.List;

@Service
public class BookOperationsServiceImpl implements BookOperationsService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public BookOperationsServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    public long create(String bookTitle, String bookAuthor, String bookGenre) {
        Book book = new Book(1, bookTitle, bookAuthor, bookGenre);

        return bookDao.insert(book);
    }

    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    public Book getById(long id) {
        return bookDao.getById(id);
    }

    public int printNumberOfAll() {
        return bookDao.count();
    }

    public List<Book> printAll() {
        return bookDao.getAll();
    }

    @Override
    public void updateTitleById(long id, String newTitle) {
        bookDao.updateTitleById(id, newTitle);
    }


}
