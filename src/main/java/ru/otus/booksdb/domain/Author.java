package ru.otus.booksdb.domain;

public class Author {

    private final long id;

    private final String author;

    public Author(long id, String author) {
        this.id = id;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }
}
