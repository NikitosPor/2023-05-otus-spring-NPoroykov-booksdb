package ru.otus.booksdb.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.booksdb.dao.BookDaoJDBC;
import ru.otus.booksdb.domain.Book;
import ru.otus.booksdb.helpers.IOService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса BookOperationsService")
@SpringBootTest
class BookOperationsServiceImplTest {

    private static final Book EXPECTED_BOOK = new Book(3, "Anna Karenina", "Leo Tolstoy", "Drama");

    @MockBean
    private IOService ioService;

    @MockBean
    private BookDaoJDBC bookDaoJdbc;

    @Autowired
    private BookOperationsService bookOperationsService;

    @Test
    void createBookTest() {
        long testedBookId = bookOperationsService.create(EXPECTED_BOOK.getTitle(), EXPECTED_BOOK.getAuthor(), EXPECTED_BOOK.getGenre());
        when(bookDaoJdbc.getById(testedBookId)).thenReturn(EXPECTED_BOOK);
        assertThat(bookOperationsService.getById(testedBookId)).isEqualTo(EXPECTED_BOOK);
    }

    @Test
    void deleteBookByIdTest() {
        Book expectedBook = new Book(0, "Anna Karenina", "Leo Tolstoy", "Drama");
        bookDaoJdbc.insert(expectedBook);
        assertThatCode(() -> bookOperationsService.getById(0)).doesNotThrowAnyException();
        bookOperationsService.deleteById(0);
        assertThatCode(() -> bookOperationsService.getById(0)).isNull();
    }

    @Test
    void getBookByIdTest() {
        Book expectedBook = new Book(10, "Anna Karenina", "Leo Tolstoy", "Drama");
        when(bookDaoJdbc.getById(10)).thenReturn(expectedBook);

        assertThat(bookOperationsService.getById(10))
                .isEqualTo(expectedBook);
    }

}