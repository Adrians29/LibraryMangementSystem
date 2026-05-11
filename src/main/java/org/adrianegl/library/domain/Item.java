package org.adrianegl.library.domain;

import org.adrianegl.library.util.Constants;

public class Item {
    protected String id;
    protected String title;
    protected ItemStatus status;

    public enum ItemStatus {
        BORROWED, IN_STORE, LOST
    }

    public Item(String title, ItemStatus status) {
        this.id = String.format("%04d", Constants.nextId++);
        this.title = title;
            this.status = ItemStatus.IN_STORE;
    }
}
