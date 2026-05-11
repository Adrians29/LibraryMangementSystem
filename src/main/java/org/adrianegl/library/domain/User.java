package org.adrianegl.library.domain;

import org.adrianegl.library.util.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String id;
    protected String name;
    protected List<Item> borrowedItems;


    public User(String name, List<Item> borrowedItems) {
        this.id = String.format("%04d", Constants.nextId++);
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    public abstract int getBorrowLimit();

    public abstract boolean canBorrow(Item item);

}
