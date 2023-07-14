package ru.otus.booksdb.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.booksdb.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Dao для работы с книгой должно")
@JdbcTest
@Import(BookDaoJDBC.class)
class BookDaoJDBCTest {

    private static final int EXPECTED_BOOKS_COUNT = 2;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private BookDaoJDBC dao;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void countBookTest() {
        long actualBooksCount = dao.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять кнгу в БД")
    @Test
    void insertBookTest() {
        var expectedBook = new Book(3, "Anna Karenina", "Leo Tolstoy", "Drama");
        long id = dao.insert(expectedBook);
        var actualBook = dao.getById(id);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("получать книгу по ID из БД")
    @Test
    void getByBookIdTest() {
        var expectedBook = new Book(1, "War and peace", "Leo Tolstoy", "Drama");
        var actualBook = dao.getById(1);
        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @DisplayName("получать все книги")
    @Test
    void getAllBooksTest() {
        var expectedBook1 = new Book(1, "War and peace", "Leo Tolstoy", "Drama");
        var expectedBook2 = new Book(2, "Eugene Onegin", "Alex Pushkin", "Drama");
        var actualBookList = dao.getAll();
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook1, expectedBook2);
    }

    @DisplayName("удалять книгу по ID из БД")
    @Test
    void deleteBookByIdTest() {
        assertThatCode(() -> dao.getById(1)).doesNotThrowAnyException();
        dao.deleteById(1);
        assertThatCode(() -> dao.getById(1)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}