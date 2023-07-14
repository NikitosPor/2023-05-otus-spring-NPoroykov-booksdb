package ru.otus.booksdb.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.booksdb.domain.Genre;
import ru.otus.booksdb.helpers.IOService;
import ru.otus.booksdb.service.GenreOperationsService;

import java.util.List;

@ShellComponent
public class AppShellControllerGenre {

    private final IOService ioService;

    private final GenreOperationsService genreOperationsService;

    public AppShellControllerGenre(GenreOperationsService genreOperationsService, IOService ioService) {
        this.genreOperationsService = genreOperationsService;
        this.ioService = ioService;
    }


    @ShellMethod(value = "Cоздание нового жанра в таблице GENRES", key = {"gc", "genre creation"})
    public void askForGenreCreation() {
        ioService.outputString("Введите <Название жанра> и нажмите Enter");
        String genreTitle = ioService.readString();

        long id = genreOperationsService.create(genreTitle);
        Genre genre = genreOperationsService.getById(id);
        if (genre != null) {
            String genreString = String.format("Создан жанр ID: %d, Название: %s",
                    genre.getId(), genre.getGenre());
            ioService.outputString(genreString);
        }
    }

    @ShellMethod(value = "Просмотр жанра в таблице GENRES по ID", key = {"gs", "genre search"})
    public void askForGenreById(long id) {
        Genre genre = genreOperationsService.getById(id);

        if (genre.getId() < 1) {
            ioService.outputString("Жанр не найден ((");
        } else {
            String genreIdString = String.format("Жанр ID: %d, Название: %s", genre.getId(), genre.getGenre());
            ioService.outputString(genreIdString);
        }
    }

    @ShellMethod(value = "Узнать количество жанров в таблице GENRES", key = {"ga", "genre amount"})
    public void askForGenreAmount() {
        int numberOfGenres = genreOperationsService.printNumberOfAll();
        String numberOfGenresString = String.format("Количество жанров в таблице = %d", numberOfGenres);
        ioService.outputString(numberOfGenresString);
    }

    @ShellMethod(value = "Показать все жанры в таблице GENRES", key = {"gl", "genre list"})
    public void askForAllGenres() {
        List<Genre> listOfGenres = genreOperationsService.printAll();
        for (Genre genre : listOfGenres) {
            String genreString = String.format("Жанр ID: %d, Название: %s", genre.getId(), genre.getGenre());
            ioService.outputString(genreString);
        }
    }

}
