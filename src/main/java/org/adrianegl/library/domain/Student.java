package org.adrianegl.library.domain;

public class Student extends User {
    public Student(String id, String name) {
        super(id, name);
    }

    @Override
    public int getBorrowLimit() {
        return 0;
    }

    @Override
    public boolean canBorrow(Item item) {
        return false;
    }
}
