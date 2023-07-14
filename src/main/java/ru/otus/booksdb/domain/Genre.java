package ru.otus.booksdb.domain;

public class Genre {

    private final long id;

    private final String genre;

    public Genre(long id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }
}
