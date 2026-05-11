package org.adrianegl.library.domain;

public class Item {
    protected String id;
    protected String title;
    protected ItemStatus status;

    public enum ItemStatus {
        BORROWED, IN_STORE, LOST
    }

    public Item(String title, ItemStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }
}
