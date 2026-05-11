package org.adrianegl.library.domain;

import org.adrianegl.library.util.Constants;

public class Student extends User {
    public Student(String name) {
        super(name);
    }

    @Override
    public int getBorrowLimit() {
        return Constants.MAX_BOOKS_STUDENT;
    }

    @Override
    public boolean canBorrow(Item item) {
        if (!(item instanceof Book)) {
            return false;
        }

        return borrowedItems.size() < getBorrowLimit();
    }
}
