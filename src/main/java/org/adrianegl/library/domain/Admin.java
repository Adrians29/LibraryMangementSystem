package org.adrianegl.library.domain;

import org.adrianegl.library.util.Constants;

public class Admin extends User {
    public Admin (String name) {
        super(name);
        this.id = String.format("A%04d", Constants.nextAdminId++);
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
