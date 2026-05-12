package org.adrianegl;

import org.adrianegl.library.domain.Library;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.loadUsers();
        library.loadItems();
    }
}
