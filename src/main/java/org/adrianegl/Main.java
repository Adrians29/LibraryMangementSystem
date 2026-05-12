package org.adrianegl;

import org.adrianegl.library.domain.*;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.loadUsers();
        library.loadItems();

        Student student = new Student("Juan");
        Teacher teacher = new Teacher("Jose");
        Admin admin = new Admin("George");

        library.registerUser(student);
        library.registerUser(teacher);
        library.registerUser(admin);

        Book book1 = new Book("Python for Beginners", Item.ItemStatus.IN_STORE, "1234567890123", "Kevin Bruce", "Education");
        Book book2 = new Book("Project Hail Mary", Item.ItemStatus.IN_STORE, "2345678909876", "Andy Weir", "Fiction");
        DVD dvd = new DVD("Batman Begins", Item.ItemStatus.IN_STORE, "Christopher Nolan", 140);
        Magazine magazine = new Magazine("National Geographic", Item.ItemStatus.IN_STORE, "Polo", 9);

        library.addItem(book1);
        library.addItem(book2);
        library.addItem(dvd);
        library.addItem(magazine);

        try {
            library.borrowItem(student.getId(), book1.getId());
            System.out.println("Student borrowed a book successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            library.borrowItem(teacher.getId(), dvd.getId());
            System.out.println("Teacher borrowed DVD successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
