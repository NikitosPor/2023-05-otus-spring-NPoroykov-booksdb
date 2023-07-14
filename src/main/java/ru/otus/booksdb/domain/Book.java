package ru.otus.booksdb.domain;

public final class Book {
    private final long id;

    private final String title;

    private final String author;

    private final String genre;

    public Book(long id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }
}