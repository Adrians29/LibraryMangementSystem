package org.adrianegl.library.domain;

import org.adrianegl.library.util.Constants;

public class Teacher extends User {
    public Teacher (String name) {
        super(name);
    }

    @Override
    public int getBorrowLimit() {
        return Constants.MAX_ITEMS_TEACHER;
    }

    @Override
    public boolean canBorrow(Item item) {
        return borrowedItems.size() < getBorrowLimit();
    }
}
