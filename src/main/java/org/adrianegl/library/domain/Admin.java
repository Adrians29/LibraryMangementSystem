package org.adrianegl.library.domain;

public class Admin extends User {
    public Admin (String name) {
        super(name);
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
