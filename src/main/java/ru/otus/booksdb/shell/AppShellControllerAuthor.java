package ru.otus.booksdb.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.booksdb.domain.Author;
import ru.otus.booksdb.helpers.IOService;
import ru.otus.booksdb.service.AuthorOperationsService;

import java.util.List;

@ShellComponent
public class AppShellControllerAuthor {

    private final IOService ioService;

    private final AuthorOperationsService authorOperationsService;

    public AppShellControllerAuthor(AuthorOperationsService authorOperationsService, IOService ioService) {
        this.authorOperationsService = authorOperationsService;
        this.ioService = ioService;
    }


    @ShellMethod(value = "Cоздание нового автора в таблице AUTHORS", key = {"ac", "author creation"})
    public void askForAuthorCreation() {
        ioService.outputString("Введите <Имя автора книги> и нажмите Enter");
        String authorName = ioService.readString();

        long id = authorOperationsService.create(authorName);
        Author author = authorOperationsService.getById(id);
        if (author != null) {
            String authorString = String.format("Создан автор ID: %d, Имя: %s",
                    author.getId(), author.getAuthor());
            ioService.outputString(authorString);
        }
    }

    @ShellMethod(value = "Просмотр автора в таблице AUTHORS по ID", key = {"as", "author search"})
    public void askForAuthorById(long id) {
        Author author = authorOperationsService.getById(id);
        if (author.getId() < 1) {
            ioService.outputString("Автор не найден ((");
        } else {
            String authorString = String.format("Автор ID: %d, Имя: %s", author.getId(), author.getAuthor());
            ioService.outputString(authorString);
        }
    }

    @ShellMethod(value = "Узнать количество авторов в таблице AUTHORS", key = {"aa", "author amount"})
    public void askForAuthorAmount() {
        int numberOfAuthors = authorOperationsService.printNumberOfAll();
        String numberOfAuthorsString = String.format("Количество авторов в таблице = %d", numberOfAuthors);
        ioService.outputString(numberOfAuthorsString);
    }

    @ShellMethod(value = "Показать всех авторов в таблице AUTHORS", key = {"al", "author list"})
    public void askForAllAuthors() {
        List<Author> listOfAuthors = authorOperationsService.printAll();
        for (Author author : listOfAuthors) {
            String authorString = String.format("Автор ID: %d, Имя: %s", author.getId(), author.getAuthor());
            ioService.outputString(authorString);
        }
    }

}
