package org.adrianegl;

import org.adrianegl.library.domain.Admin;
import org.adrianegl.library.domain.Library;
import org.adrianegl.library.domain.Student;
import org.adrianegl.library.domain.Teacher;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.loadUsers();
        library.loadItems();

        Student student = new Student("Juan");
        Teacher teacher = new Teacher("Jose");
        Admin admin = new Admin("George");

    }
}
