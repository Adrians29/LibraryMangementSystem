package org.adrianegl.library.domain;

import lombok.Getter;
import org.adrianegl.library.util.Validation;

public class Book extends Item {
    private String isbn;
    @Getter private String author;
    private String genre;


    public Book(String title, ItemStatus status, String isbn, String author, String genre) {
        super(title, status);

        if (!Validation.isValidISBM(isbn)) {
            throw new IllegalArgumentException("Invalid ISBN");
        }

        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
    }
}
