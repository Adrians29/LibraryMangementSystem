package org.adrianegl.library.util;

public class Validation {
    public static boolean isValidISBN(String isbn) {
        return isbn.matches("\\d{13}");
    }
}
